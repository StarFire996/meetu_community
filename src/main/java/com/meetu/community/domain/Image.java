package com.meetu.community.domain;

public class Image {
	
	private Integer id;
	private Integer hight;
	private Integer weight;
	private String url;
	private String desc;
	private String exten;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getHight() {
		return hight;
	}
	public void setHight(Integer hight) {
		this.hight = hight;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getExten() {
		return exten;
	}
	public void setExten(String exten) {
		this.exten = exten;
	}
	@Override
	public String toString() {
		return "Image [id=" + id + ", hight=" + hight + ", weight=" + weight
				+ ", url=" + url + ", desc=" + desc + ", exten=" + exten + "]";
	}
	
}
