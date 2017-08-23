package com.xiaotu.advertiser.dictionary.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.xiaotu.advertiser.common.util.Constants;
import com.xiaotu.advertiser.dictionary.model.GoodsModel;
import com.xiaotu.advertiser.dictionary.model.GoodsWordMapModel;
import com.xiaotu.advertiser.dictionary.model.WordModel;
import com.xiaotu.advertiser.project.model.ProjectModel;
import com.xiaotu.common.exception.BusinessException;
import com.xiaotu.common.mvc.BaseService;
import com.xiaotu.common.util.FileUtil;
import com.xiaotu.common.util.SepratorUtil;
import com.xiaotu.common.util.SessionUtil;

/**
 * @类名 GoodsService
 * @日期 2017年6月16日
 * @作者 高海军
 * @功能 品类业务处理类
 */
@Service
public class GoodsService extends BaseService
{
    private static final Predicate<String> DATA_FILTER = line ->
    {
        String[] arr = line.split(SepratorUtil.SEP_COMMA_EN);
        return arr.length > 1 && StringUtils.isNotBlank(arr[0])
                && StringUtils.isNotBlank(arr[1]);// 品类文件的数据过滤器
    };

    /**
     * 保存品类、关键词和他们的对应关系
     *
     * @param goodFile   品类、关键词文件
     * @param weightFile 映射关系文件
     */
    public void saveGoodsAndWord(File goodFile, File weightFile)
    {
        List<String> lineList = FileUtil.readFileByLine(
                goodFile.getAbsolutePath(), Constants.getFileEncode());
        if (lineList == null || lineList.isEmpty())
            throw new BusinessException("数据为空");
        lineList = lineList.stream().skip(1).collect(Collectors.toList());

        Set<GoodsModel> gdSet = new HashSet<>();// 文件中解析出的所有品类
        Set<WordModel> wdSet = new HashSet<>();// 文件中解析出的所有关键词

        Set<GoodsModel> goodsSet = new HashSet<>();// 需要入库的品类
        this.setInsertData(goodsSet, lineList, DATA_FILTER,
                line -> line.split(SepratorUtil.SEP_COMMA_EN)[0], goods ->
                {
                    GoodsModel goodsModel = new GoodsModel(goods.trim());
                    goodsSet.add(goodsModel);
                    gdSet.add(goodsModel);
                }, "selectGoods", "品类");// 获取需要入库的品类
        if (!goodsSet.isEmpty())
            this.save("insertGoods", new ArrayList<>(goodsSet));

        Set<WordModel> wordSet = new HashSet<>();// 需要入库的关键词
        this.setInsertData(wordSet, lineList, DATA_FILTER,
                line -> getWordStr(line), words ->
                {
                    for (String word : words.split(SepratorUtil.SEP_COMMA_EN))
                    {
                        WordModel wordModel = new WordModel(word.trim());
                        wordSet.add(wordModel);
                        wdSet.add(wordModel);
                    }
                }, "WordMapper.selectWord", "关键词");// 获取需要入库的关键词
        if (!wordSet.isEmpty())
            this.save("WordMapper.insertWord", new ArrayList<>(wordSet));

        this.saveGoodsWordMap(gdSet, wdSet, lineList, weightFile);
    }

    /**
     * 保存品类与关键词的关联信息
     *
     * @param goodsList  品类列表
     * @param wordList   关键词列表
     * @param lineList   品类文件所有行
     * @param weightFile 相识度文件
     */
    private void saveGoodsWordMap(Set<GoodsModel> goodsList,
            Set<WordModel> wordList, List<String> lineList, File weightFile)
    {
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("list", goodsList);
        Map<String, GoodsModel> goodsMap = this.getMap("selectGoods", paraMap,
                "goods");// 查询出所有需要关联的品类
        paraMap.put("list", wordList);
        Map<String, WordModel> wordMap = this.getMap("WordMapper.selectWord",
                paraMap, "word");// 查询出所有需要关联的关键词

        List<String> weightList = FileUtil.readFileByLine(
                weightFile.getAbsolutePath(), Constants.getFileEncode());// 读取关联数据
        if (weightList == null || weightList.isEmpty())
            throw new BusinessException("数据为空");
        weightList = weightList.stream().skip(1).collect(Collectors.toList());
        if (lineList.size() != weightList.size())
            throw new BusinessException("品类数据与相似度数据无法一一对应");

        Map<String, String[]> weightMap = new HashMap<>();
        weightList.stream().filter(DATA_FILTER).forEach(line ->
        {
            weightMap.put(line.split(SepratorUtil.SEP_COMMA_EN)[0].trim(),
                    getWordStr(line).split(SepratorUtil.SEP_COMMA_EN));
        });// 解析关联数据

        Set<GoodsWordMapModel> mapSet = new HashSet<>();
        lineList.stream().filter(DATA_FILTER).forEach(line ->
        {
            String goods = line.split(SepratorUtil.SEP_COMMA_EN)[0].trim();
            String[] weightArr = weightMap.get(goods);
            String[] wordArr = getWordStr(line)
                    .split(SepratorUtil.SEP_COMMA_EN);
            if (weightArr == null || weightArr.length != wordArr.length)
                throw new BusinessException("关联词与相识度无法一一对相应[" + goods + "]");

            for (int i = 0; i < wordArr.length; i++)
                mapSet.add(new GoodsWordMapModel(goodsMap.get(goods),
                        wordMap.get(wordArr[i].trim()),
                        Double.parseDouble(weightArr[i].trim())));
        });// 封装需要入库的关联实体集合

        if (!mapSet.isEmpty())// 更新数据库中的关联信息
        {
            List<GoodsWordMapModel> mapList = new ArrayList<>(mapSet);
            this.delete("GoodsWordMapMapper.deleteGoodsWordMap", mapList);
            this.save("GoodsWordMapMapper.insertGoodsWordMap", mapList);
        }
    }

    /**
     * 提取关键词/相识度字符串
     *
     * @param line 一行数据
     * @return 关键词/相识度字符串
     */
    private String getWordStr(String line)
    {
        return line
                .substring(line.indexOf(SepratorUtil.SEP_COMMA_EN)
                        + SepratorUtil.SEP_COMMA_EN.length())
                .replaceAll(SepratorUtil.SEP_QUOTATION, StringUtils.EMPTY);
    }

    /**
     * 设置可以入库的品类/关键词
     *
     * @param dataSet    可以入库的品类/关键词集合
     * @param dataFilter 数据过滤
     * @param dataMap    数据提炼
     * @param dataAction 数据处理行为
     * @param selectKey  数据库查询key
     * @param dataName   数据名称
     */
    private <T> void setInsertData(Set<T> dataSet, List<String> lineList,
            Predicate<String> dataFilter, Function<String, String> dataMap,
            Consumer<String> dataAction, String selectKey, String dataName)
    {

		/*
         * 读取文件中的每一行，获取品类/关键词
		 */
        lineList.stream().filter(dataFilter).map(dataMap).forEach(dataAction);
        if (dataSet.isEmpty())
            throw new BusinessException(dataName + "为空");

		/*
         * 删除库中已经存在的品类/关键词
		 */
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("list", dataSet);
        List<T> dbGoodsList = this.getList(selectKey, paraMap);
        dbGoodsList.forEach(goods -> dataSet.remove(goods));
    }

    /**
     * 查询品类列表
     *
     * @return
     * @author xuchangjian 2017年7月6日下午3:01:40
     */
    public Map<String, Object> queryGoodsList()
    {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        ProjectModel project = SessionUtil.getSessionProject();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("projectId", project.getId());
        List<GoodsModel> goodsList = this.getList("selectGoods", params);

        resultMap.put("goodsList", goodsList);
        return resultMap;
    }

    @Override
    protected String getKey()
    {
        return "GoodsMapper";
    }
}
