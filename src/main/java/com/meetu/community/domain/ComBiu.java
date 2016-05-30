package com.meetu.community.domain;

import java.sql.Timestamp;

public class ComBiu {
	
	private Integer id;
	private Integer userCodeMine;
	private Integer userCodeGrab;
	private Integer status;
	private Integer isRead;
	private Timestamp createAt;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserCodeMine() {
		return userCodeMine;
	}
	public void setUserCodeMine(Integer userCodeMine) {
		this.userCodeMine = userCodeMine;
	}
	public Integer getUserCodeGrab() {
		return userCodeGrab;
	}
	public void setUserCodeGrab(Integer userCodeGrab) {
		this.userCodeGrab = userCodeGrab;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getIsRead() {
		return isRead;
	}
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
	public Timestamp getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Timestamp createAt) {
		this.createAt = createAt;
	}
	@Override
	public String toString() {
		return "Biu [id=" + id + ", userCodeMine=" + userCodeMine
				+ ", userCodeGrab=" + userCodeGrab + ", status=" + status
				+ ", isRead=" + isRead + ", createAt=" + createAt + "]";
	}
	
}
