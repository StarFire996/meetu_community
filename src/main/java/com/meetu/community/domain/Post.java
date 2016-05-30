package com.meetu.community.domain;

import java.sql.Timestamp;

public class Post {
	
	private Integer id;
	private Integer type;
	private String content;
	private String imgs;
	private String music;
	private Integer level;
	private Integer commentNum;
	private Integer praiseNum;
	private Integer createBy;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImgs() {
		return imgs;
	}
	public void setImgs(String imgs) {
		this.imgs = imgs;
	}
	public String getMusic() {
		return music;
	}
	public void setMusic(String music) {
		this.music = music;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}
	public Integer getPraiseNum() {
		return praiseNum;
	}
	public void setPraiseNum(Integer praiseNum) {
		this.praiseNum = praiseNum;
	}
	public Integer getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}
	public Timestamp getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Timestamp createAt) {
		this.createAt = createAt;
	}
	@Override
	public String toString() {
		return "Post [id=" + id + ", type=" + type + ", content=" + content
				+ ", imgs=" + imgs + ", music=" + music + ", level=" + level
				+ ", commentNum=" + commentNum + ", praiseNum=" + praiseNum
				+ ", createBy=" + createBy + ", createAt=" + createAt + "]";
	}
	
	
}
