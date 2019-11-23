package com.sec.news.model;

import java.util.ArrayList;
import java.util.Date;

public class News {
	public News()
	{}
	
	/**
	 * ��������ID��ѯNews
	 * @param newsId
	 */
	public News(int newsId)
	{
		this.newsId = newsId;
	}
	
	private ArrayList<News> lst = new ArrayList<News>();
	//���ű��
	private int newsId;
	//���ͱ��
	private Type type;
	//�û����
	private User user;
	//���ű���
	private String title;
	//��������
	private String content;
	//�����Ƽ���0���Ƽ�1�Ƽ���
	private int recommended;
	//���ŷ�����(Ĭ��Ϊ0)
	private int click;
	//���Źؼ���
	private String keywords;
	//����ʱ��
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
