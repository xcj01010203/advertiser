package com.xiaotu.advertiser.project.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.xiaotu.advertiser.play.PlayAnalysisUtils;
import com.xiaotu.advertiser.play.PlayTitleMsgDto;
import com.xiaotu.advertiser.play.ViewInfoDto;
import com.xiaotu.advertiser.project.model.PlayContentModel;
import com.xiaotu.advertiser.project.model.PlayFormatModel;
import com.xiaotu.advertiser.project.model.PlayModel;
import com.xiaotu.advertiser.project.model.PlayRoleModel;
import com.xiaotu.advertiser.project.model.PlayRoundModel;
import com.xiaotu.advertiser.project.model.PlayRoundTmpModel;
import com.xiaotu.advertiser.project.model.ProjectModel;
import com.xiaotu.advertiser.project.model.map.PlayRoundRoleMapModel;
import com.xiaotu.advertiser.user.model.UserModel;
import com.xiaotu.common.db.Page;
import com.xiaotu.common.db.util.UUIDUtils;
import com.xiaotu.common.exception.BusinessException;
import com.xiaotu.common.mvc.BaseService;
import com.xiaotu.common.util.FileUtil;
import com.xiaotu.common.util.PropertiesUtil;
import com.xiaotu.common.util.SessionUtil;
import com.xiaotu.common.util.SortUtils;

/**
 * 剧本管理
 * @author xuchangjian 2017年6月21日上午11:10:26
 */
@Service
public class PlayService extends BaseService {
	
	@Autowired
	private PlayRoundService playRoundService;
	
	@Autowired
	private PlayContentService playContentService;
	
	@Autowired
	private PlayRoundTmpService playRoundTmpService;
	
	@Autowired
	private PlayRoleService playRoleService;
	
	@Autowired
	private PlayRoundRoleMapService playRoundRoleMapService;

	/**
	 * 上传剧本并保存剧本信息
	 * @author xuchangjian 2017年6月21日下午3:07:51
	 * @param file
	 * @throws Exception
	 */
	public Map<String, Object> savePlay(MultipartFile file, Integer wordCount, Integer lineCount, Boolean pageIncludeTitle) throws Exception
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if (wordCount == null) {
			throw new BusinessException("为了方便计算页数，请填写剧本每行的字数");
		}
		if (lineCount == null) {
			throw new BusinessException("为了方便计算页数，请填写剧本页的行数");
		}
		if (pageIncludeTitle == null) {
			pageIncludeTitle = true;
		}
		
		
		ProjectModel project = SessionUtil.getSessionProject();
		UserModel user = SessionUtil.getSessionUser();
		
		//上传剧本
		String basepath = PropertiesUtil.getProperty("fileupload.path");
		String storepath = basepath + File.separator + "play";
		Map<String, String> uploadFileInfo = FileUtil.uploadFile(file, storepath, null);
		
		String fileRealName = uploadFileInfo.get("fileRealName");
		String fileStoreName = uploadFileInfo.get("fileStoreName");
		String myStorepath = uploadFileInfo.get("storepath");
		
		//解析剧本
		Map<String, Object> playInfo = PlayAnalysisUtils.analysePlay(myStorepath + fileStoreName, wordCount, lineCount, pageIncludeTitle);
		
		List<ViewInfoDto> viewList = (List<ViewInfoDto>) playInfo.get("viewInfoList");
		List<PlayTitleMsgDto> titleMsgList = (List<PlayTitleMsgDto>) playInfo.get("titleMsgList");
		String uploadDesc = "";
		String scriptRules = "";
		for (PlayTitleMsgDto playTitleMsg : titleMsgList)
		{
			String playName = fileRealName;
			String scriptRule = playTitleMsg.getScriptRule();
			String titleInfoMsgs = playTitleMsg.getTitleInfoMsgs();
			String titleWarnMsgs = playTitleMsg.getTitleWarnMsgs();
			String titleErrorMsgs = playTitleMsg.getTitleErrorMsgs();
			
			scriptRules += scriptRule + "|";
			if (!StringUtils.isBlank(titleInfoMsgs) || !StringUtils.isBlank(titleWarnMsgs) || !StringUtils.isBlank(titleErrorMsgs))
			{
				uploadDesc += "----《" + playName + "》----\r\n";
			}
			
			if (!StringUtils.isBlank(titleInfoMsgs))
			{
				uploadDesc += "提示:\r\n" + titleInfoMsgs; 
			}
			if (!StringUtils.isBlank(titleWarnMsgs))
			{
				uploadDesc += "警告:\r\n" + titleWarnMsgs;
			}
			if (!StringUtils.isBlank(titleErrorMsgs))
			{
				uploadDesc += "错误:\r\n" + titleErrorMsgs;
			}
		}

		//保存上传的剧本信息
		PlayModel play = new PlayModel();
		play.setProject(project);
		play.setName(fileRealName);
		play.setStorepath(myStorepath + fileStoreName);
		play.setUploadTime(new Date());
		play.setUser(user);
		play.setSucceed(viewList != null && viewList.size() > 0);
		play.setUploadDesc(uploadDesc);
		play.setScriptRule(scriptRules);
		
		this.save("insertPlay", play);
		
		//剧本格式信息
		PlayFormatModel playFormat = this.get("PlayFormatMapper.selectByProjectId", project.getId());
		if (playFormat == null) 
		{
			playFormat = new PlayFormatModel();
		}
		playFormat.setLineCount(lineCount);
		playFormat.setWordCount(wordCount);
		playFormat.setPageIncludeTitle(pageIncludeTitle);
		playFormat.setProject(project);
		
		if (StringUtils.isBlank(playFormat.getId())) 
		{
			this.save("PlayFormatMapper.insertOne", playFormat);
		}
		else
		{
			this.save("PlayFormatMapper.updateOne", playFormat);
		}
		
		//把解析出的剧本信息和数据库中已有信息比较，并把结果入库
		this.compareWithExistRound(viewList, project);;
		
		resultMap.put("uploadDesc", uploadDesc);
		return resultMap;
	}
	
	/**
	 * 把解析出的剧本信息和数据库中已有信息比较，并把比对结果入库
	 * @author xuchangjian 2017年6月22日上午10:37:33
	 * @param viewList	场次信息
	 * @param project	项目信息
	 * @param wordCount	每行显示字数
	 * @param lineCount	每页显示行数
	 * @param pageIncludeTitle	计算页数是否包含标题
	 * @return
	 */
	public void compareWithExistRound(List<ViewInfoDto> viewList, ProjectModel project)
	{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projectId", project.getId());
		List<PlayRoundModel> existPlayRoundList = this.playRoundService.getList("selectPlayRound", params);
		
		List<PlayRoundTmpModel> roundTmpList = new ArrayList<PlayRoundTmpModel>();	//有冲突的场次
		List<PlayRoundTmpModel> existTmpList = this.playRoundTmpService.getList("selectWithoutContent", params);
		
		List<ViewInfoDto> autoAddViewList = new ArrayList<ViewInfoDto>();	//自动新增的场次
		List<String> toDeleteRoundIdList = new ArrayList<String>();	//待删除的场次ID
		for (ViewInfoDto viewInfoDto : viewList)
		{
			boolean hasConflict = false;	//标识跟数据库中记录是否有冲突（数据库中对应场次已手动保存）
			if (existPlayRoundList != null && existPlayRoundList.size() >= 0)
			{
				for (PlayRoundModel playRound : existPlayRoundList)
				{
					if (viewInfoDto.getSeriesNo() == playRound.getSeriesNo() && viewInfoDto.getViewNo().equals(playRound.getRoundNo()))
					{
						if (playRound.getIsManualSaved())
						{
							hasConflict = true;
							
							PlayRoundTmpModel roundTmp = new PlayRoundTmpModel();
							roundTmp.setProject(project);
							roundTmp.setPlayRound(playRound);
							roundTmp.setSeriesNo(viewInfoDto.getSeriesNo());
							roundTmp.setRoundNo(viewInfoDto.getViewNo());
							roundTmp.setAtmosphere(viewInfoDto.getAtmosphere());
							roundTmp.setSite(viewInfoDto.getSite());
							roundTmp.setTitle(viewInfoDto.getTitle());
							roundTmp.setContent(viewInfoDto.getContent());
							roundTmp.setFirstLocation(viewInfoDto.getFirstLocation());
							roundTmp.setPageCount(viewInfoDto.getPageCount());
							
							List<String> majorRoleNameList = viewInfoDto.getMajorRoleNameList();
							
							if (majorRoleNameList != null && majorRoleNameList.size() > 0)
							{
								Comparator<String> sort = SortUtils.cnSort();
								Collections.sort(majorRoleNameList, sort);
								
								String majorRoleNames = "";
								for (String majorRoleName : majorRoleNameList)
								{
									majorRoleNames += majorRoleName + ",";
								}
								
								majorRoleNames = majorRoleNames.substring(0, majorRoleNames.length() - 1);
								roundTmp.setMajorRoleNames(majorRoleNames);
							}
							
							if (existTmpList == null || !existTmpList.contains(roundTmp))
							{
								roundTmpList.add(roundTmp);
							}
							
						}
						if (!hasConflict)
						{
							toDeleteRoundIdList.add(playRound.getId());
						} 
						else 
						{
							break;
						}
					}
				}
			}
			
			if (!hasConflict)
			{
				autoAddViewList.add(viewInfoDto);
			}
		}
		
		//把需要提示跳过或替换的场次信息入库
		if (roundTmpList != null && roundTmpList.size() > 0)
		{
			this.playRoundTmpService.save("insertBatch", roundTmpList);
		}
		
		//先删除已有的场次
		if (toDeleteRoundIdList != null && toDeleteRoundIdList.size() > 0)
		{
			this.playRoundService.removeBatchById(toDeleteRoundIdList);	
		}
		
		//保存需要自动保存的剧本
		this.saveByViewInfoDtoList(autoAddViewList, project);
	}
	
	/**
	 * 保存自动新增的记录
	 * @author xuchangjian 2017年6月22日上午10:14:14
	 * @param viewInfoDtoList	场次列表
	 * @param project	项目信息
	 */
	public void saveByViewInfoDtoList(List<ViewInfoDto> viewInfoDtoList, ProjectModel project)
	{
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("projectId", project.getId());
		List<PlayRoleModel> existPlayRoleList = this.playRoleService.getList("selectPlayRoleList", parameterMap);	//库中已有的角色信息
		List<PlayRoundRoleMapModel> existRoleMapList = new ArrayList<PlayRoundRoleMapModel>();	//已添加的角色信息
		
		List<PlayRoundModel> toAddPlayRoundList = new ArrayList<PlayRoundModel>();	//待新增的场次
		List<PlayContentModel> toAddPlayContentList = new ArrayList<PlayContentModel>();	//待新增的场次内容
		
		List<PlayRoleModel> toAddRoleList = new ArrayList<PlayRoleModel>();	//待新增的角色信息
		List<PlayRoundRoleMapModel> toAddRoundRoleMapList = new ArrayList<PlayRoundRoleMapModel>();	//待新增的场次和角色关联关系
		
		for (ViewInfoDto viewInfoDto : viewInfoDtoList)
		{
			//场次信息
			PlayRoundModel playRound = new PlayRoundModel();
			playRound.setId(UUIDUtils.getStringUUID());
			playRound.setProject(project);
			playRound.setSeriesNo(viewInfoDto.getSeriesNo());
			playRound.setRoundNo(viewInfoDto.getViewNo());
			playRound.setAtmosphere(viewInfoDto.getAtmosphere());
			playRound.setSite(viewInfoDto.getSite());
			playRound.setFirstLocation(viewInfoDto.getFirstLocation());
			playRound.setCreateTime(new Date());
			playRound.setLastUpdateTime(new Date());
			playRound.setIsManualSaved(false);
			playRound.setPageCount(viewInfoDto.getPageCount());
			toAddPlayRoundList.add(playRound);
			
			//场次内容信息
			PlayContentModel playContent = new PlayContentModel();
			playContent.setProject(project);
			playContent.setPlayRound(playRound);
			playContent.setTitle(viewInfoDto.getTitle());
			playContent.setContent(viewInfoDto.getContent());
			if (!StringUtils.isBlank(viewInfoDto.getContent()))
			{
				playContent.setWordCount(viewInfoDto.getContent().length());
			}
			toAddPlayContentList.add(playContent);
			
			//角色信息
			List<String> majorRoleNameList = viewInfoDto.getMajorRoleNameList();
			if (majorRoleNameList != null && majorRoleNameList.size() > 0)
			{
				Map<String, Object> roleCompareResult = this.playRoundService.comparePlayRole(existPlayRoleList, existRoleMapList, majorRoleNameList, project, playRound, 1);
				toAddRoleList.addAll((List<PlayRoleModel>) roleCompareResult.get("toAddRoleList"));
				toAddRoundRoleMapList.addAll((List<PlayRoundRoleMapModel>) roleCompareResult.get("toAddRoundRoleMapList"));
				
				existPlayRoleList.addAll((List<PlayRoleModel>) roleCompareResult.get("toAddRoleList"));
				existRoleMapList.addAll((List<PlayRoundRoleMapModel>) roleCompareResult.get("toAddRoundRoleMapList"));
			}
		}
		
		//把场次和剧本内容信息入库
		if (toAddPlayRoundList != null && toAddPlayRoundList.size() > 0)
		{
			this.playRoundService.save("insertBatch", toAddPlayRoundList);
		}
		if (toAddPlayContentList != null && toAddPlayContentList.size() > 0)
		{
			this.playContentService.save("insertBatch", toAddPlayContentList);
		}
		
		
		//把角色信息入库
		if (toAddRoleList != null && toAddRoleList.size() > 0)
		{
			this.playRoleService.save("insertBatch", toAddRoleList);
		}
		if (toAddRoundRoleMapList != null && toAddRoundRoleMapList.size() > 0)
		{
			this.playRoundRoleMapService.save("insertBatch", toAddRoundRoleMapList);
		}
	}
	
	/**
	 * 查询剧本列表
	 * @author xuchangjian 2017年7月26日上午11:57:01
	 * @param page
	 * @return
	 */
	public Map<String, Object> queryPlayList(Page page)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ProjectModel project = SessionUtil.getSessionProject();
		
		List<Map<String, Object>> playList = this.queryPageList("selectPlay", project.getId(), page);
		
		resultMap.put("playList", playList);
		resultMap.put("totalPage", page.getTotalPage());
		resultMap.put("totalRows", page.getTotalRows());
		return resultMap;
	}
	
	/**
	 * 重新分析页数
	 * @author xuchangjian 2017年8月31日下午3:48:44
	 * @param wordCount	每行显示字数
	 * @param lineCount	每页显示行数
	 * @param pageIncludeTitle	计算页数是否包含标题
	 */
	public void savePage(Integer wordCount, Integer lineCount, Boolean pageIncludeTitle)
	{
		if (wordCount == null) {
			throw new BusinessException("为了方便计算页数，请填写剧本每行的字数");
		}
		if (lineCount == null) {
			throw new BusinessException("为了方便计算页数，请填写剧本页的行数");
		}
		if (pageIncludeTitle == null) {
			pageIncludeTitle = true;
		}
		
		ProjectModel project = SessionUtil.getSessionProject();
		//剧本格式信息
		PlayFormatModel playFormat = this.get("PlayFormatMapper.selectByProjectId", project.getId());
		if (playFormat == null) 
		{
			playFormat = new PlayFormatModel();
		}
		playFormat.setLineCount(lineCount);
		playFormat.setWordCount(wordCount);
		playFormat.setPageIncludeTitle(pageIncludeTitle);
		playFormat.setProject(project);
		
		if (StringUtils.isBlank(playFormat.getId())) 
		{
			this.save("PlayFormatMapper.insertOne", playFormat);
		}
		else
		{
			this.save("PlayFormatMapper.updateOne", playFormat);
		}
		
		Map<String, Object> contentParam = new HashMap<String, Object>();
		contentParam.put("projectId", project.getId());
		List<PlayContentModel> contentList = this.getList("PlayContentMapper.selectPlayContent", contentParam);
		
		for (PlayContentModel playContent : contentList)
		{
			String title = playContent.getTitle();
			String content = playContent.getContent();
			
			double pageCount = PlayAnalysisUtils.calculatePage(title, content, lineCount, wordCount, pageIncludeTitle);
			
			Map<String, Object> roundPageMap = new HashMap<String, Object>();
			roundPageMap.put("id", playContent.getPlayRound().getId());
			roundPageMap.put("pageCount", pageCount);
			this.update("PlayRoundMapper.updatePage", roundPageMap);
		}
	}
	

	@Override
	protected String getKey() {
		return "PlayMapper";
	}
}
