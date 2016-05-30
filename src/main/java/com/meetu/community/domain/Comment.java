package com.meetu.community.domain;

import java.sql.Timestamp;

public class Comment {
	
	private Integer id;
	private Integer postId;
	private Integer parentId;
	private String content;
	private Integer userFrom;
	private Integer userTo;
	private Timestamp createAt;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPostId() {
		return postId;
	}
	public void setPostId(Integer postId) {
		this.postId = postId;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getUserFrom() {
		return userFrom;
	}
	public void setUserFrom(Integer userFrom) {
		this.userFrom = userFrom;
	}
	public Integer getUserTo() {
		return userTo;
	}
	public void setUserTo(Integer userTo) {
		this.userTo = userTo;
	}
	public Timestamp getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Timestamp createAt) {
		this.createAt = createAt;
	}
	@Override
	public String toString() {
		return "Comment [id=" + id + ", postId=" + postId + ", parentId="
				+ parentId + ", content=" + content + ", userFrom=" + userFrom
				+ ", userTo=" + userTo + ", createAt=" + createAt + "]";
	}
	
}
