package com.xiaotu.advertiser.project.model;

/**
 * 剧本内容
 * @author xuchangjian 2017年6月22日上午10:52:28
 */
public class PlayContentModel {

	private String id;
	private ProjectModel project;	//所属项目
	private PlayRoundModel playRound;	//所属场次
	private String title;	//标题
	private String content;	//内容
	private int wordCount;
	public int getWordCount() {
		return wordCount;
	}
	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}
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
	public PlayRoundModel getPlayRound() {
		return playRound;
	}
	public void setPlayRound(PlayRoundModel playRound) {
		this.playRound = playRound;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
