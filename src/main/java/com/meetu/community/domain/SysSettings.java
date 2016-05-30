package com.meetu.community.domain;

public class SysSettings implements java.io.Serializable{

	private static final long serialVersionUID = -4863668983187924729L;
	
	private String id;
	private String parameter;
	private Integer value1;
	private String value2;
	private String description;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public Integer getValue1() {
		return value1;
	}
	public void setValue1(Integer value1) {
		this.value1 = value1;
	}
	public String getValue2() {
		return value2;
	}
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
