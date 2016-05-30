package com.meetu.community.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.meetu.community.domain.Activity;

public interface ActivityService {

	public List<Activity> selectActivityByType(Integer type);

	public void insertActivity(Activity activity);
	
	public void parseActivityListToJson(List<Activity> actList,JSONArray actArray);
}
