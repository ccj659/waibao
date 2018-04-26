package com.example.jinglinzichan.bean;

@SuppressWarnings("serial")
public class NoticeBean extends BaseBean {

	private String id;
	private String title;
	private String creatime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreatime() {
		return creatime;
	}

	public void setCreatime(String creatime) {
		this.creatime = creatime;
	}

}