package com.example.jinglinzichan.bean;

@SuppressWarnings("serial")
public class FundDetailsBean extends BaseBean {

	private String money;
	private String account_money;
	private String creatime;
	private String name;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getAccount_money() {
		return account_money;
	}

	public void setAccount_money(String account_money) {
		this.account_money = account_money;
	}

	public String getCreatime() {
		return creatime;
	}

	public void setCreatime(String creatime) {
		this.creatime = creatime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}