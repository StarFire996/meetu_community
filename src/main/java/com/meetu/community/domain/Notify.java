package com.meetu.community.domain;

import java.sql.Timestamp;

public class Notify {
	
	private Integer id;
	private Integer type;
	private Integer userFrom;
	private Integer userTo;
	private Integer postId;
	private String imgs;
	private String content;
	private String comment;
	private Integer isRead;
	private Timestamp createAt;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	public Integer getPostId() {
		return postId;
	}
	public void setPostId(Integer postId) {
		this.postId = postId;
	}
	public String getImgs() {
		return imgs;
	}
	public void setImgs(String imgs) {
		this.imgs = imgs;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
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
		return "Notify [id=" + id + ", type=" + type + ", userFrom=" + userFrom
				+ ", userTo=" + userTo + ", postId=" + postId + ", imgs="
				+ imgs + ", content=" + content + ", comment=" + comment
				+ ", isRead=" + isRead + ", createAt=" + createAt + "]";
	}
	
}
