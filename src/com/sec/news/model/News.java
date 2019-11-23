package com.sec.news.model;

import java.util.ArrayList;
import java.util.Date;

public class News {
	public News()
	{}
	
	/**
	 * 给定新闻ID查询News
	 * @param newsId
	 */
	public News(int newsId)
	{
		this.newsId = newsId;
	}
	
	private ArrayList<News> lst = new ArrayList<News>();
	//新闻编号
	private int newsId;
	//类型编号
	private Type type;
	//用户编号
	private User user;
	//新闻标题
	private String title;
	//新闻内容
	private String content;
	//新闻推荐（0不推荐1推荐）
	private int recommended;
	//新闻访问量(默认为0)
	private int click;
	//新闻关键词
	private String keywords;
	//发布时间
	private String releaseTime = null;
	
	
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getRecommended() {
		return recommended;
	}
	public void setRecommended(int recommended) {
		this.recommended = recommended;
	}
	public int getClick() {
		return click;
	}
	public void setClick(int click) {
		this.click = click;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}

	public int getNewsId() {
		return newsId;
	}

	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}
		
}
