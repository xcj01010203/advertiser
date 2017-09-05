package com.xiaotu.advertiser.project.model;

/**
 * 剧本格式
 * @author xuchangjian 2017年8月31日上午10:45:05
 */
public class PlayFormatModel {

	private String id;
	private ProjectModel project;	//项目信息	
	private Integer wordCount = 35;	//每行显示字数
	private Integer lineCount = 40;	//每页显示行数
	private Boolean pageIncludeTitle = true;	//计算页数时是否包含标题
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ProjectModel getProject() {
		return project;
	}
	public void setProject(ProjectModel project) {
		this.project = project;
	}
	public Integer getWordCount() {
		return wordCount;
	}
	public void setWordCount(Integer wordCount) {
		this.wordCount = wordCount;
	}
	public Integer getLineCount() {
		return lineCount;
	}
	public void setLineCount(Integer lineCount) {
		this.lineCount = lineCount;
	}
	public Boolean getPageIncludeTitle() {
		return pageIncludeTitle;
	}
	public void setPageIncludeTitle(Boolean pageIncludeTitle) {
		this.pageIncludeTitle = pageIncludeTitle;
	}
}
