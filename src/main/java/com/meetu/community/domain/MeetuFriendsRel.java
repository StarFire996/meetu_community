package com.meetu.community.domain;

import java.util.Date;

public class MeetuFriendsRel implements java.io.Serializable{
	
	private static final long serialVersionUID = -3450268113860397697L;
	private String id;
	private String user_id1;
	private String user_id2;
	private Date date;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser_id1() {
		return user_id1;
	}
	public void setUser_id1(String user_id1) {
		this.user_id1 = user_id1;
	}
	public String getUser_id2() {
		return user_id2;
	}
	public void setUser_id2(String user_id2) {
		this.user_id2 = user_id2;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

}
