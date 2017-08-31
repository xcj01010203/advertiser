package com.xiaotu.advertiser.implant.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

import com.xiaotu.advertiser.common.util.Constants;
import com.xiaotu.advertiser.dictionary.model.GoodsModel;
import com.xiaotu.advertiser.implant.model.AnalysisJobModel;
import com.xiaotu.advertiser.implant.model.AnalysisResultModel;
import com.xiaotu.advertiser.implant.model.AnalysisWordModel;
import com.xiaotu.advertiser.implant.model.map.AnalysisWordRoleMapModel;
import com.xiaotu.advertiser.project.model.PlayRoleModel;
import com.xiaotu.advertiser.project.model.PlayRoundModel;
import com.xiaotu.advertiser.project.model.ProjectModel;
import com.xiaotu.advertiser.project.service.PlayRoundService;
import com.xiaotu.advertiser.user.model.UserModel;
import com.xiaotu.common.db.Page;
import com.xiaotu.common.exception.BusinessException;
import com.xiaotu.common.mvc.BaseService;
import com.xiaotu.common.util.GsonUtils;
import com.xiaotu.common.util.KeyLocker;
import com.xiaotu.common.util.PropertiesUtil;
import com.xiaotu.common.util.SepratorUtil;
import com.xiaotu.common.util.SessionUtil;
import com.xiaotu.common.util.SocketClientUtil;
import com.xiaotu.common.util.ThreadPool;

/**
 * @类名 AutoAnalysisService
 * @日期 2017年7月7日
 * @作者 高海军
 * @功能 广告植入自动分析Service
 */
@Service
public class AutoAnalysisService extends BaseService
{
    private static final Logger LOGGER = LoggerFactory
            .getLogger(AutoAnalysisService.class);

    @Autowired
    private PlayRoundService playRoundService;


    /**
     * 执行自动分析任务，保存分析结果
     *
     * @param page
     */
    @SuppressWarnings("unchecked")
    public void saveAnalysisResult(Page page)
    {
        ProjectModel project = SessionUtil.getSessionProject();
        UserModel user = SessionUtil.getSessionUser();
        AnalysisJobModel job = KeyLocker.lock(project.getId(),
                () -> this.refreshJob(user, project));// 刷新任务基本信息（同一项目需要同步执行，避免任务基本信息更新错误）

        Map<String, Object> map = playRoundService.queryRoundListWithContent(page);// 查询剧本内容
        if (map == null || map.get("roundList") == null
                || !(map.get("roundList") instanceof List)
                || ((List<Map<String, Object>>) map.get("roundList")).isEmpty())
            throw new BusinessException("剧本内容为空");

        ThreadPool.executeBySmall(() -> this.startJob(job, project, map));// 启动任务
    }

    /**
     * 刷新任务的基本信息
     *
     * @param user    当前用户
     * @param project 项目信息
     */
    private AnalysisJobModel refreshJob(UserModel user, ProjectModel project)
    {
        AnalysisJobModel job = this.validateJob(project);
        if (job == null)
        {
            job = new AnalysisJobModel(user, project);
            this.save("ImplantAnalyseJobMapper.insertJob", job);// 保存任务基本信息
        }
        else
        {
            job.setUser(SessionUtil.getSessionUser());
            job.setStartTime(System.currentTimeMillis());
            job.setStatus(AnalysisJobModel.JOB_RUNNING);
            this.update("ImplantAnalyseJobMapper.updateJob4Start", job);// 保存任务基本信息
        }
        return job;
    }

    /**
     * 校验任务是否可以启动
     *
     * @param project 项目信息
     * @return 已存在的任务信息
     */
    private AnalysisJobModel validateJob(ProjectModel project)
    {
        AnalysisJobModel job = this.get("ImplantAnalyseJobMapper.selectJob",
                project);
        if (job != null && job.getStatus() == AnalysisJobModel.JOB_RUNNING)
            throw new BusinessException("任务正在运行中");
        return job;
    }

    /**
     * 执行自动分析任务
     *
     * @param job     任务基本信息
     * @param project 项目
     * @param playMap 剧本内容
     */
    private void startJob(AnalysisJobModel job, ProjectModel project,
            Map<String, Object> playMap)
    {
        TransactionStatus status = this.getTransactionStatus();
        try
        {
            AnalysisResults results = this.parseData(project, playMap);

            // 删除旧的分析结果
            this.delete("deleteMapByProject", project.getId());
            this.delete("deleteWordByProject", project.getId());
            this.delete("deleteResultByProject", project.getId());

            // 保存写的分析结果
            if (!results.getResultList().isEmpty())
            {
                this.save("insertResult", results.getResultList());
                this.save("insertWord", results.getWordList());
                if (results.getMapList() != null
                        && !results.getMapList().isEmpty())
                    this.save("insertWordRoleMap", results.getMapList());
            }

            this.jobDone(job, AnalysisJobModel.JOB_SUCCESS, null);
            this.commit(status);
        }
        catch (Exception e)
        {
            this.rollback(status);
            LOGGER.error(ExceptionUtils.getStackTrace(e));
            this.jobDone(job, AnalysisJobModel.JOB_FAIL, e);
        }

    }

    /**
     * 从远程分析服务获取分析结果
     *
     * @param map 剧本内容
     * @return 分析结果
     * @throws Exception
     */
    private String getAnalysisData(Map<String, Object> map) throws Exception
    {
        SocketClientUtil client = new SocketClientUtil(
                PropertiesUtil.getProperty(Constants.AUTO_MODEL_IP),
                PropertiesUtil.getPropertyByInt(Constants.AUTO_MODEL_PORT));
        String requestData = GsonUtils.toJson(map);
        LOGGER.debug("request data:" + requestData);
        byte[] dataArr = client.send((requestData + SocketClientUtil.EOF)
                .getBytes(Constants.CHARSET_UTF8));
        if (dataArr == null || dataArr.length < 1)
            throw new BusinessException("分析结果为空");
        String data = new String(dataArr, Constants.CHARSET_UTF8);
        LOGGER.debug("analysis result:" + data);
        return data;
    }

    /**
     * 调用远程算法获取分析结果，并解析成指定格式
     *
     * @param project 项目
     * @param map     剧本
     * @return 格式化后的分析结果
     */
    @SuppressWarnings("unchecked")
    private AnalysisResults parseData(ProjectModel project,
            Map<String, Object> map) throws Exception
    {
        Map<String, Object> resMap = GsonUtils
                .fromJson(this.getAnalysisData(map), Map.class);
        List<Map<String, Object>> adFullList = (List<Map<String, Object>>) resMap
                .get("adFullList");
        List<Map<String, Object>> adDetailList = (List<Map<String, Object>>) resMap
                .get("adDetailList");

        if (adFullList == null || adFullList.isEmpty() || adDetailList == null
                || adDetailList.isEmpty())
            throw new BusinessException("返回的分析结果字段为空");

        Map<String, PlayRoundModel> roundMap = new ConcurrentHashMap<>();// 缓存场次列表
        Map<String, AnalysisResultModel> resultMap = new ConcurrentHashMap<>();// 分析结果数据

        adFullList.parallelStream().forEach(oneMap -> this
                .genAnalysisResult(project, oneMap, roundMap, resultMap));// 分析结果基本信息解析

        Map<AnalysisWordModel, AnalysisWordModel> wordMap = new ConcurrentHashMap<>();// 分析结果-关联词
        Map<AnalysisWordRoleMapModel, AnalysisWordRoleMapModel> wordRoleMap = new ConcurrentHashMap<>();// 分析结果-关联词对应角色
        adDetailList.parallelStream()
                .forEach(oneMap -> this.genAnalysisDetail(project, roundMap,
                        resultMap, oneMap, wordMap, wordRoleMap));// 分析结果详情解析

        return new AnalysisResults(new ArrayList<>(resultMap.values()),
                new ArrayList<>(wordMap.values()),
                new ArrayList<>(wordRoleMap.values()));
    }

    /**
     * 获取分析结果详情
     *
     * @param project
     * @param roundMap
     * @param resultMap
     * @param oneMap
     * @param wordsMap
     * @param wordRoleMap
     */
    @SuppressWarnings("unchecked")
    private void genAnalysisDetail(ProjectModel project,
            Map<String, PlayRoundModel> roundMap,
            Map<String, AnalysisResultModel> resultMap,
            Map<String, Object> oneMap,
            Map<AnalysisWordModel, AnalysisWordModel> wordsMap,
            Map<AnalysisWordRoleMapModel, AnalysisWordRoleMapModel> wordRoleMap)
    {
        String roundId = oneMap.get("roundId").toString();
        PlayRoundModel round = roundMap.get(roundId);
        if (round == null)
            throw new BusinessException("分析结果中场次信息不一致");
        List<Map<String, Object>> wordList = (List<Map<String, Object>>) oneMap
                .get("detailInfo");
        wordList.forEach(wordMap ->
        {
            AnalysisResultModel result = resultMap
                    .get(roundId + SepratorUtil.SEP_UNDERLINE
                            + wordMap.get("goodId").toString());
            if (result == null)
                throw new BusinessException("分析结果广告植入信息不一致");

            AnalysisWordModel wordKey = new AnalysisWordModel(result,
                    wordMap.get("word").toString(),
                    this.getPosition4Word(wordMap.get("position").toString()),
                    Double.parseDouble(wordMap.get("weight").toString()));
            if (!wordsMap.containsKey(wordKey))
                wordsMap.put(wordKey, wordKey);

            List<Map<String, Object>> roleList = (List<Map<String, Object>>) wordMap
                    .get("roleList");
            if (roleList == null || roleList.isEmpty())
                return;
            roleList.forEach(roleMap ->
            {
                AnalysisWordRoleMapModel mapModel = new AnalysisWordRoleMapModel(
                        new PlayRoleModel(roleMap.get("roleId").toString(),
                                project, roleMap.get("roleName").toString()),
                        wordsMap.get(wordKey));
                wordRoleMap.put(mapModel, mapModel);
            });
        });
    }

    /**
     * 封装分析结果数据
     *
     * @param project   项目
     * @param oneMap    一条分析结果数据
     * @param roundMap  场次缓存
     * @param resultMap 分析结果
     */
    @SuppressWarnings("unchecked")
    private void genAnalysisResult(ProjectModel project,
            Map<String, Object> oneMap, Map<String, PlayRoundModel> roundMap,
            Map<String, AnalysisResultModel> resultMap)
    {
        PlayRoundModel round = new PlayRoundModel(
                oneMap.get("roundId").toString(), project,
                Float.valueOf(oneMap.get("seriesNo").toString()).intValue(),
                oneMap.get("roundId").toString());
        roundMap.put(oneMap.get("roundId").toString(), round);// 缓存场次列表

        List<Map<String, Object>> detailList = (List<Map<String, Object>>) oneMap
                .get("detailInfo");
        if (detailList == null || detailList.isEmpty())
            throw new BusinessException("分析结果数据错误(adFullList.detailInfo为空)");
        // 封装分析结果数据
        detailList.forEach(detailMap ->
        {
            GoodsModel goods = new GoodsModel(
                    detailMap.get("goodId").toString(),
                    detailMap.get("goodName").toString());
            AnalysisResultModel result = new AnalysisResultModel(project, round,
                    goods,
                    this.getPosition4Result(
                            detailMap.get("position").toString()),
                    Double.valueOf(detailMap.get("weight").toString()));
            resultMap.put(
                    round.getId() + SepratorUtil.SEP_UNDERLINE + goods.getId(),
                    result);
        });
    }

    /**
     * 获取品类的植入位置信息
     *
     * @param pos 位置名称
     * @return 位置标识
     */
    private int getPosition4Result(String pos)
    {
        if (StringUtils.isEmpty(pos))
            throw new BusinessException("植入位置信息为空");
        if (pos.contains("台词") && pos.contains("地点"))
            return AnalysisResultModel.POS_ALL;
        return this.getPosition4Word(pos);
    }

    /**
     * 获取关联词植入位置信息
     *
     * @param pos 位置名称
     * @return 位置标识
     */
    private int getPosition4Word(String pos)
    {
        if (StringUtils.isEmpty(pos))
            throw new BusinessException("植入位置信息为空");
        if (pos.contains("台词"))
            return AnalysisResultModel.POS_TALK;
        if (pos.contains("地点"))
            return AnalysisResultModel.POS_PLACE;
        throw new BusinessException("植入位置信息错误:" + pos);
    }

    /**
     * 任务启动/执行异常，记录异常状态
     *
     * @param job       任务
     * @param jobStatus 任务状态
     * @param e         异常
     */
    private void jobDone(AnalysisJobModel job, int jobStatus, Exception e)
    {
        job.setStatus(jobStatus);
        job.setEndTime(System.currentTimeMillis());
        if (e != null)
            job.setMessage(e.getMessage());
        else
            job.setMessage(null);
        this.update("ImplantAnalyseJobMapper.updateJob4Done", job);
    }

    /**
     * @类名 AnalysisResults
     * @日期 2017年7月13日
     * @作者 高海军
     * @功能 封装格式化后的分析结果数据
     */
    private class AnalysisResults
    {
        List<AnalysisResultModel> resultList;
        List<AnalysisWordModel> wordList;
        List<AnalysisWordRoleMapModel> mapList;

        public AnalysisResults(List<AnalysisResultModel> resultList,
                List<AnalysisWordModel> wordList,
                List<AnalysisWordRoleMapModel> mapList)
        {
            super();
            this.resultList = resultList;
            this.wordList = wordList;
            this.mapList = mapList;
        }

        public List<AnalysisResultModel> getResultList()
        {
            return resultList;
        }

        public List<AnalysisWordModel> getWordList()
        {
            return wordList;
        }

        public List<AnalysisWordRoleMapModel> getMapList()
        {
            return mapList;
        }

    }

    @Override
    protected String getKey()
    {
        return "ImplantAnalyseResultMapper";
    }
}
