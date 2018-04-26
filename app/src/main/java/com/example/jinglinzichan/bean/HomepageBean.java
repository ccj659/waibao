package com.example.jinglinzichan.bean;

@SuppressWarnings("serial")
public class HomepageBean extends BaseBean {

	private String id;
	private String name;
	private String repay_time;
	private String rate;
	private String borrow_amount_format;
	private String month_repay_money_format;
	private String deal_status;
	private int progress_point;
	private String remain_time;
	private String repayment_id;
	private String u_load_money_format;
	private String month_repay_money;

	public String getMonth_repay_money() {
		return month_repay_money;
	}

	public void setMonth_repay_money(String month_repay_money) {
		this.month_repay_money = month_repay_money;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRepay_time() {
		return repay_time;
	}

	public void setRepay_time(String repay_time) {
		this.repay_time = repay_time;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getBorrow_amount_format() {
		return borrow_amount_format;
	}

	public void setBorrow_amount_format(String borrow_amount_format) {
		this.borrow_amount_format = borrow_amount_format;
	}

	public String getMonth_repay_money_format() {
		return month_repay_money_format;
	}

	public void setMonth_repay_money_format(String month_repay_money_format) {
		this.month_repay_money_format = month_repay_money_format;
	}

	public String getDeal_status() {
		return deal_status;
	}

	public void setDeal_status(String deal_status) {
		this.deal_status = deal_status;
	}

	public int getProgress_point() {
		return progress_point;
	}

	public void setProgress_point(int progress_point) {
		this.progress_point = progress_point;
	}

	public String getRemain_time() {
		return remain_time;
	}

	public void setRemain_time(String remain_time) {
		this.remain_time = remain_time;
	}

	public String getRepayment_id() {
		return repayment_id;
	}

	public void setRepayment_id(String repayment_id) {
		this.repayment_id = repayment_id;
	}

	public String getU_load_money_format() {
		return u_load_money_format;
	}

	public void setU_load_money_format(String u_load_money_format) {
		this.u_load_money_format = u_load_money_format;
	}

}