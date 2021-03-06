package com.xiaotu.advertiser.project.service;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.xiaotu.common.util.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaotu.advertiser.common.util.Constants;
import com.xiaotu.advertiser.project.model.PlayRoundModel;
import com.xiaotu.advertiser.project.model.ProjectModel;
import com.xiaotu.advertiser.project.model.RoleAnalyseResultModel;
import com.xiaotu.common.db.Page;
import com.xiaotu.common.exception.BusinessException;
import com.xiaotu.common.mvc.BaseService;

/**
 * 剧本内容
 * @author xuchangjian 2017年6月22日上午11:09:16
 */
@Service
public class PlayContentService extends BaseService {

    Logger logger = LoggerFactory.getLogger(PlayContentService.class);

    @Autowired
    private PlayRoundService playRoundService;

    @Override
    protected String getKey() {
        return "PlayContentMapper";
    }

    /**
     * 下载剧本
     * @author xuchangjian 2017年6月29日上午10:24:27
     * @return
     * @throws IOException
     */
    public Object downloadPlay(HttpServletResponse response) throws IOException
    {
        ProjectModel project = SessionUtil.getSessionProject();

        int roundCount = this.get("PlayRoundMapper.countProjectRound", project.getId());	//总场次数
        if (roundCount == 0)
        {
            throw new BusinessException("请先上传剧本");
        }
        int pageSize = 500;
        int pageCount = roundCount / pageSize;

        // 新建一个文档
        XWPFDocument doc = new XWPFDocument();
        // 创建一个段落
        XWPFParagraph para = doc.createParagraph();
        for (int i = 0; i <= pageCount; i++)
        {
            Page page = new Page();
            page.setPageSize(pageSize);
            page.setCurrentPage(i + 1);
            Map<String, Object> roundResult = this.playRoundService.queryRoundListWithContent(page);
            List<Map<String, Object>> roundList = (List<Map<String, Object>>) roundResult.get("roundList");

            for (Map<String, Object> round : roundList)
            {
                int seriesNo = (int) round.get("seriesNo");
                String roundNo = (String) round.get("roundNo");
                String atmosphere = (String) round.get("atmosphere");
                String site = (String) round.get("site");
                String firstLocation = (String) round.get("firstLocation");
                String content = (String) round.get("content");
                List<Map<String, Object>> roleList = (List<Map<String, Object>>) round.get("roleList");
                String roleNames = "";
                for (Map<String, Object> roleInfo : roleList)
                {
                    roleNames += roleInfo.get("name") + "/";
                }
                if (!StringUtils.isBlank(roleNames)) {
                    roleNames = roleNames.substring(0, roleNames.length() - 1);
                }

                StringBuilder title = new StringBuilder(seriesNo + "-" + roundNo);
                if (!StringUtils.isBlank(firstLocation))
                {
                    title.append(" " + firstLocation);
                }
                if (!StringUtils.isBlank(atmosphere))
                {
                    title.append(" " + atmosphere);
                }
                if (!StringUtils.isBlank(atmosphere) && !StringUtils.isBlank(site)) {
                    title.append("/");
                }
                if (StringUtils.isBlank(atmosphere) && !StringUtils.isBlank(site)) {
                    title.append(" ");
                }
                if (!StringUtils.isBlank(site))
                {
                    title.append(site);
                }

                //设置输出标题的格式的格式
                String titleStr = title.toString();
                XWPFRun run = para.createRun();
                run.setBold(true); // 加粗
                run.setFontSize(12);
                run.setText(titleStr);
                run.addCarriageReturn();
                if (!StringUtils.isBlank(roleNames))
                {
                    run.setText(roleNames);
                    run.addCarriageReturn();
                }

                //设置输出正文的格式
                run = para.createRun();
                run.setFontSize(12);
                if (!StringUtils.isBlank(content)) {
                    String[] contentArr = content.split("\r\n");
                    for (String singleLineC : contentArr) {
                        run.setText(singleLineC);
                        run.addCarriageReturn();
                    }
                }
                run.addCarriageReturn();
            }
        }

        //生成下载的文件名
        String fileName = "《" + project.getName() + "》.doc";
        response.setHeader("Content-Disposition", "attachment;fileName = " + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/msword");
        response.setCharacterEncoding("UTF-8");
        doc.write(response.getOutputStream());

        return null;
    }

    /**
     * 保存提取的剧本中的角色信息
     * @return
     * @throws Exception
     */
    public Map<String, Object> saveExtractedRoleList() throws Exception
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        ProjectModel project = SessionUtil.getSessionProject();

        int contentCount = this.get("countProjectContent", project.getId());
        if (contentCount == 0) {
            throw new BusinessException("请先上传剧本");
        }

        //调用远程算法获取分析结果
        List<Map<String, Object>> roundListWithRole = this.getRemoteResult(project);

        //先删除库里原来的分析结果
        this.delete("RoleAnalyseResultMapper.deleteByProjectId", project.getId());
        //保存分析结果
        this.saveRoleAnalyseResult(project, roundListWithRole);

        //只取出不重复的角色列表给前端（带有角色拥有的场次信息）
        List<Map<String, Object>> roleList = this.formatAnalyseResult(roundListWithRole);

        resultMap.put("roleList", roleList);
        return resultMap;
    }

    /**
     * 格式化分析结果，用于前端展示
     * @param roundListWithRole
     * @return
     */
    public List<Map<String, Object>> formatAnalyseResult(List<Map<String, Object>> roundListWithRole)
    {
        Map<String, Integer> roleNameCountMap = new HashMap<String, Integer>();	//key为角色名称，value为角色戏量
        for (Map<String, Object> roundMap : roundListWithRole)
        {
            List<String> roleNameList = (List<String>) roundMap.get("roleNameList");
            for (String roleName : roleNameList) {
                if (roleNameCountMap.containsKey(roleName))
                {
                    roleNameCountMap.put(roleName, roleNameCountMap.get(roleName) + 1);
                }
                else
                {
                    roleNameCountMap.put(roleName, 1);
                }
            }
        }

        List<Map<String, Object>> roleInfoList = new ArrayList<Map<String, Object>>();	//角色信息列表（带有角色名称、角色戏量信息）
        for (String roleName : roleNameCountMap.keySet()) {
            Integer roundCount = roleNameCountMap.get(roleName);

            Map<String, Object> roleInfoMap = new HashMap<String, Object>();
            roleInfoMap.put("roleName", roleName);
            roleInfoMap.put("roundCount", roundCount);
            roleInfoList.add(roleInfoMap);
        }

        Collections.sort(roleInfoList, (o1, o2) -> (Integer)o2.get("roundCount") - (Integer)o1.get("roundCount"));

        return roleInfoList;
    }

    /**
     * 保存分析结果
     * @param project
     * @param roundListWithRole
     */
    public void saveRoleAnalyseResult(ProjectModel project, List<Map<String, Object>> roundListWithRole)
    {
        List<RoleAnalyseResultModel> roleAnalyseResultList = new ArrayList<RoleAnalyseResultModel>();
        for (Map<String, Object> ananlyseResult : roundListWithRole)
        {
            String roundId = (String) ananlyseResult.get("roundId");
            List<String> roleNameList = (List<String>) ananlyseResult.get("roleNameList");

            for (String roleName : roleNameList)
            {
                RoleAnalyseResultModel result = new RoleAnalyseResultModel();
                result.setProject(project);
                result.setRoleName(roleName);

                PlayRoundModel round = new PlayRoundModel();
                round.setId(roundId);
                result.setRound(round);

                roleAnalyseResultList.add(result);
            }
        }
        this.save("RoleAnalyseResultMapper.insertBatch", roleAnalyseResultList);
    }

    /**
     * 调用远程算法获取分析结果
     * @param job     任务基本信息
     * @param project 项目
     * @param roundMap 剧本内容
     * @return 每个场次对应的角色列表
     * @throws Exception
     */
    private List<Map<String, Object>> getRemoteResult(ProjectModel project) throws Exception
    {
        List<Map<String, Object>> contentList = new ArrayList<Map<String, Object>>();

        Map<String, Object> roundResult = this.playRoundService.queryRoundListWithContent(null);
        List<Map<String, Object>> roundList = (List<Map<String, Object>>) roundResult.get("roundList");
        for (Map<String, Object> roundMap : roundList)
        {
            //只取出角色名称字段
            List<Map<String, Object>> roleList = (List<Map<String, Object>>) roundMap.get("roleList");
            List<String> roleNameList = new ArrayList<String>();
            for (Map<String, Object> roleMap : roleList) {
                roleNameList.add((String) roleMap.get("name"));
            }

            Map<String, Object> contentMap = new HashMap<String, Object>();
            contentMap.put("roundId", roundMap.get("id"));
            contentMap.put("roleList", roleNameList);
            contentMap.put("content", roundMap.get("content"));
            contentList.add(contentMap);
        }

        String analyseResultStr = this.getAnalysisData(contentList);
        List<Map<String, Object>> analyseResultList = GsonUtils.fromJson(analyseResultStr, List.class);

        return analyseResultList;
    }

    /**
     * 从远程分析服务获取分析结果
     *
     * @param map 剧本内容
     * @return 分析结果
     * @throws Exception
     */
    private String getAnalysisData(List<Map<String, Object>> contentList) throws Exception
    {
        SocketClientUtil client = new SocketClientUtil(
                PropertiesUtil.getProperty(Constants.ROLE_ANALYSE_IP),
                PropertiesUtil.getPropertyByInt(Constants.ROLE_ANALYSE_PROT));
        String requestData = GsonUtils.toJson(contentList);

        byte[] dataArr = client.send((requestData + SocketClientUtil.EOF).getBytes(Constants.CHARSET_UTF8));
        if (dataArr == null || dataArr.length < 1)
            throw new BusinessException("分析结果为空");
        String data = new String(dataArr, Constants.CHARSET_UTF8);

        return data;
    }
}
