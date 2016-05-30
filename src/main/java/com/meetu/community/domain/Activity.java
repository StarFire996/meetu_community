package com.meetu.community.domain;

import java.sql.Timestamp;

public class Activity {
	
	private Integer id;
	private Integer type;
	private String cover;
	private String url;
	private String title;
	private Integer index;
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
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public Timestamp getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Timestamp createAt) {
		this.createAt = createAt;
	}
	@Override
	public String toString() {
		return "Activity [id=" + id + ", type=" + type + ", cover=" + cover
				+ ", url=" + url + ", title=" + title + ", index=" + index
				+ ", createAt=" + createAt + "]";
	}
	
}
