package com.meetu.community.domain;

import java.util.Date;

public class User {

	private String id;
	private Integer code;
	private String name;
	private String idcard;
	private String nickname;
	private String password;
	private String icon_url;
	private String original_icon_url;
	private String icon_is_validate;
	private String sex;
	private Date birth_date;
	private Integer height;
	private Integer weight;
	private Integer virtual_currency;
	private String city;
	private String cityname;
	private Integer today_num;
	private Integer total_num;
	private Date last_visit_date;
	
	public Date getLast_visit_date() {
		return last_visit_date;
	}

	public void setLast_visit_date(Date last_visit_date) {
		this.last_visit_date = last_visit_date;
	}

	public Integer getToday_num() {
		return today_num;
	}

	public void setToday_num(Integer today_num) {
		this.today_num = today_num;
	}

	public Integer getTotal_num() {
		return total_num;
	}

	public void setTotal_num(Integer total_num) {
		this.total_num = total_num;
	}

	public String getCityname() {
		return cityname;
	}
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	public String getSchoolname() {
		return schoolname;
	}
	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}
	private String cityf;
	private String hometown;
	private String isgraduated;
	private String career;
	private String school;
	private String schoolname;
	private String phone;
	private String user_type;
	private String personality_tags;
	private String interested_tags;
	private String description;
	private Date register_date;
	private Double longitude;
	private Double dimension;
	private String disabled_status;
	private String company;
	private String starsign;
	private String password_hx;
	private Date activity_time;
	private String app_status;
	private Integer superman;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIcon_url() {
		return icon_url;
	}
	public void setIcon_url(String icon_url) {
		this.icon_url = icon_url;
	}
	public String getOriginal_icon_url() {
		return original_icon_url;
	}
	public void setOriginal_icon_url(String original_icon_url) {
		this.original_icon_url = original_icon_url;
	}
	public String getIcon_is_validate() {
		return icon_is_validate;
	}
	public void setIcon_is_validate(String icon_is_validate) {
		this.icon_is_validate = icon_is_validate;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getBirth_date() {
		return birth_date;
	}
	public void setBirth_date(Date birth_date) {
		this.birth_date = birth_date;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public Integer getVirtual_currency() {
		return virtual_currency;
	}
	public void setVirtual_currency(Integer virtual_currency) {
		this.virtual_currency = virtual_currency;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getHometown() {
		return hometown;
	}
	public void setHometown(String hometown) {
		this.hometown = hometown;
	}
	public String getCareer() {
		return career;
	}
	public void setCareer(String career) {
		this.career = career;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	public String getPersonality_tags() {
		return personality_tags;
	}
	public void setPersonality_tags(String personality_tags) {
		this.personality_tags = personality_tags;
	}
	public String getInterested_tags() {
		return interested_tags;
	}
	public void setInterested_tags(String interested_tags) {
		this.interested_tags = interested_tags;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getRegister_date() {
		return register_date;
	}
	public void setRegister_date(Date register_date) {
		this.register_date = register_date;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getDimension() {
		return dimension;
	}
	public void setDimension(Double dimension) {
		this.dimension = dimension;
	}
	public String getDisabled_status() {
		return disabled_status;
	}
	public void setDisabled_status(String disabled_status) {
		this.disabled_status = disabled_status;
	}
	public String getIsgraduated() {
		return isgraduated;
	}
	public void setIsgraduated(String isgraduated) {
		this.isgraduated = isgraduated;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getStarsign() {
		return starsign;
	}
	public void setStarsign(String starsign) {
		this.starsign = starsign;
	}
	public String getPassword_hx() {
		return password_hx;
	}
	public void setPassword_hx(String password_hx) {
		this.password_hx = password_hx;
	}
	public String getCityf() {
		return cityf;
	}
	public void setCityf(String cityf) {
		this.cityf = cityf;
	}
	public Date getActivity_time() {
		return activity_time;
	}
	public void setActivity_time(Date activity_time) {
		this.activity_time = activity_time;
	}
	public String getApp_status() {
		return app_status;
	}
	public void setApp_status(String app_status) {
		this.app_status = app_status;
	}
	public Integer getSuperman() {
		return superman;
	}
	public void setSuperman(Integer superman) {
		this.superman = superman;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", code=" + code + ", name=" + name
				+ ", idcard=" + idcard + ", nickname=" + nickname
				+ ", password=" + password + ", icon_url=" + icon_url
				+ ", original_icon_url=" + original_icon_url
				+ ", icon_is_validate=" + icon_is_validate + ", sex=" + sex
				+ ", birth_date=" + birth_date + ", height=" + height
				+ ", weight=" + weight + ", virtual_currency="
				+ virtual_currency + ", city=" + city + ", cityname="
				+ cityname + ", today_num=" + today_num + ", total_num="
				+ total_num + ", last_visit_date=" + last_visit_date
				+ ", cityf=" + cityf + ", hometown=" + hometown
				+ ", isgraduated=" + isgraduated + ", career=" + career
				+ ", school=" + school + ", schoolname=" + schoolname
				+ ", phone=" + phone + ", user_type=" + user_type
				+ ", personality_tags=" + personality_tags
				+ ", interested_tags=" + interested_tags + ", description="
				+ description + ", register_date=" + register_date
				+ ", longitude=" + longitude + ", dimension=" + dimension
				+ ", disabled_status=" + disabled_status + ", company="
				+ company + ", starsign=" + starsign + ", password_hx="
				+ password_hx + ", activity_time=" + activity_time
				+ ", app_status=" + app_status + ", superman=" + superman + "]";
	}


	
	
}
