package com.example.jinglinzichan.bean;

@SuppressWarnings("serial")
public class TenderDetailsBean extends BaseBean {

	private String id;
	private String user_name_true;
	private String num_nh;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser_name_true() {
		return user_name_true;
	}

	public void setUser_name_true(String user_name_true) {
		this.user_name_true = user_name_true;
	}

	public String getNum_nh() {
		return num_nh;
	}

	public void setNum_nh(String num_nh) {
		this.num_nh = num_nh;
	}

}