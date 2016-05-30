package com.meetu.community.domain;

import java.sql.Timestamp;

public class Tags {
	
	private Integer id;
	private String content;
	private Integer level;
	private Integer postNum;
	private Integer length;
	private Timestamp createAt;
	public Integer getId() {
		return id;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getPostNum() {
		return postNum;
	}
	public void setPostNum(Integer postNum) {
		this.postNum = postNum;
	}
	public Timestamp getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Timestamp createAt) {
		this.createAt = createAt;
	}
	@Override
	public String toString() {
		return "CommunityTag [id=" + id + ", content=" + content + ", level="
				+ level + ", postNum=" + postNum + ", createAt=" + createAt
				+ "]";
	}
	
}
