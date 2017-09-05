package com.xiaotu.advertiser.implant.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xiaotu.advertiser.implant.service.ImplantAnalyseResultService;

/**
 * 广告植入自动分析结果
 * @author xuchangjian 2017年7月5日上午10:03:33
 */
@RestController
@RequestMapping("/implantAnalyse")
public class ImplantAnalyseResultController {

	@Autowired
	private ImplantAnalyseResultService implantAnalyseResultService;

	/**
	 * 获取按角色分类的广告统计信息
	 * @author xuchangjian 2017年7月5日上午10:45:11
	 * @param goodsIdList
	 * @return
	 */
	@RequestMapping("/queryRoleImplant")
	public Object queryRoleImplant(@RequestParam(required=false, value="goodsIdList[]") List<String> goodsIdList, Integer pageSize, Integer currentPage)
	{
		return this.implantAnalyseResultService.queryRoleImplant(goodsIdList, pageSize, currentPage);
	}
	
	/**
	 * 获取按产品分类的广告统计信息
	 * @author xuchangjian 2017年7月5日上午10:45:11
	 * @param goodsIdList
	 * @return
	 */
	@RequestMapping("/queryGoodsImplant")
	public Object queryGoodsImplant(@RequestParam(required=false, value="goodsIdList[]") List<String> goodsIdList)
	{
		return this.implantAnalyseResultService.queryGoodsImplant(goodsIdList);
	}
	
	/**
	 * 查询按场次产品分类的广告统计
	 * @author xuchangjian 2017年7月31日下午6:31:03
	 * @param seriesNoList	集次列表
	 * @param goodsIdList	品类ID列表
	 * @param roleId	场次中角色ID
	 * @param pageSize	每页显示条数
	 * @param currentPage	当前页数
	 * @return
	 */
	@RequestMapping("/queryRoundGoodsImplant")
	public Object queryRoundGoodsImplant(Integer seriesNo, 
			@RequestParam(required=false, value="goodsIdList[]") List<String> goodsIdList, String roleId,
			Integer pageSize, Integer currentPage)
	{
		return this.implantAnalyseResultService.queryRoundGoodsImplant(seriesNo, goodsIdList, roleId, pageSize, currentPage);
	}
	
	/**
	 * 获取分析结果
	 * @author xuchangjian 2017年7月28日上午10:19:39
	 * @param roundId
	 * @return
	 */
	@RequestMapping("/queryRoundResult")
	public Object queryRoundResult(String roundId) 
	{
		return this.implantAnalyseResultService.queryRoundResult(roundId);
	}
	
	
	/**
	 * 导出角色、产品、剧本内容分类列表
	 * @param goodsIdList
	 * @param roleId
	 * @return
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@RequestMapping("/exportRoundGoodsImplant")
	public Object queryExportRoundGoods(Integer seriesNo, @RequestParam(required=false, value="goodsIdList[]") List<String> goodsIdList,String roleId,String roleNames,HttpServletResponse response) throws IllegalArgumentException, IllegalAccessException, IOException
	{
		 implantAnalyseResultService.queryExportRoundGoods(seriesNo, goodsIdList,roleId,roleNames,response);
		 return null;
	}
	
}
