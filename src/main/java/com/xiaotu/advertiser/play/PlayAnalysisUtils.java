package com.xiaotu.advertiser.play;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import scenarioAnalysis.dto.EpisodeInfoDto;
import scenarioAnalysis.dto.MatchInfoDto;
import scenarioAnalysis.service.ScenarioAnalysisMainService;

public class PlayAnalysisUtils {

	private static Logger logger = LoggerFactory.getLogger(PlayAnalysisUtils.class);

	/**
	 * 解析剧本<br>
	 * 默认人物最小重复次数为0，不保留群众演员<br>
	 * 如果文件是压缩包，则会在文件所在目录新建一个“文件名_unzip”的文件夹，并把压缩包中文件解压到此处<br>
	 * 对于word文件，会在word文件所在目录下新建convert文件夹，存储由word转换的txt文件
	 * 
	 * @param fileName
	 *            文件名称
	 * @param filepath
	 *            剧本文件存储路径
	 * @param openOfficeInstallPath
	 *            openoffice安装地址
	 * @param winrarInstallPath
	 *            winrar安装地址
	 * @param viewLocationKeywordFilePath
	 *            存储场景关键字的文件所在目录
	 * @return key为viewInfoList时，value表示场次列表<br>
	 *         key为titleMsgList时，value表示标题信息<br>
	 *         具体返回结构见接口文档
	 * @throws Exception
	 */
	public static Map<String, Object> analysePlay(String filepath) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		List<ViewInfoDto> viewList = new ArrayList<ViewInfoDto>();
		List<PlayTitleMsgDto> titleMsgList = new ArrayList<PlayTitleMsgDto>();
		
		ScenarioAnalysisMainService analysisService = new ScenarioAnalysisMainService();
		Map<String, Object> resultPlay = (Map<String, Object>) analysisService.analysisScenarioFile(filepath);

		logger.info("总体错误信息：" + resultPlay.get("titleErrorMsgStr"));
		logger.info("总体警告信息：" + resultPlay.get("titleWarnMsgStr"));
		logger.info("总体提示信息：" + resultPlay.get("titleInfoMsgStr"));
		PlayTitleMsgDto playTitleMsgDto = new PlayTitleMsgDto();
		playTitleMsgDto.setTitleInfoMsgs((String) resultPlay.get("titleInfoMsgStr"));
		playTitleMsgDto.setTitleWarnMsgs((String) resultPlay.get("titleWarnMsgStr"));
		playTitleMsgDto.setTitleErrorMsgs((String) resultPlay.get("titleErrorMsgStr"));
		titleMsgList.add(playTitleMsgDto);

		List<Map<String, Object>> dataList = (ArrayList<Map<String, Object>>) resultPlay.get("dataList");

		for (Map<String, Object> dataMap : dataList) {
			for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
				Map<String, Object> viewDataMap = (Map<String, Object>) entry.getValue();
				List<String> titleErrorMsg = (List<String>) viewDataMap.get("titleErrorMsg");
				
				if (titleErrorMsg.isEmpty()) {
					List<EpisodeInfoDto> episodeList = (List<EpisodeInfoDto>) viewDataMap.get("episodeList");
					List<MatchInfoDto> matchList = (List<MatchInfoDto>) viewDataMap.get("matchList");
					List<String> roleList = (List<String>) viewDataMap.get("roleList");
					
					for (MatchInfoDto match : matchList) {
						ViewInfoDto viewInfoDto = new ViewInfoDto();
						viewInfoDto.setSeriesNo(match.getSeriesNo());
						viewInfoDto.setViewNo(match.getViewNo());
						viewInfoDto.setSeason(match.getSeason());
						viewInfoDto.setAtmosphere(match.getAtmosphere());
						viewInfoDto.setSite(match.getSite());
						viewInfoDto.setTitle(match.getTitle());
						viewInfoDto.setContent(match.getContent());
						viewInfoDto.setMajorRoleNameList(match.getMajorRoleNameList());
						viewInfoDto.setFirstLocation(match.getFirstLocation());
						viewInfoDto.setSecondLocation(match.getSecondLocation());
						viewInfoDto.setThirdLocation(match.getThirdLocation());
						
						if (!viewList.contains(viewInfoDto)) {
							viewList.add(viewInfoDto);
						}
					}
				}
			}
		}

		resultMap.put("viewInfoList", viewList);
		resultMap.put("titleMsgList", titleMsgList);
		return resultMap;
	}
}
