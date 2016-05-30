package com.meetu.community.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meetu.community.domain.Activity;
import com.meetu.community.mapper.ActivityMapper;
import com.meetu.community.service.ActivityService;

@Service
@Controller
public class ActivityServiceImpl implements ActivityService{
	
	@Autowired
	private ActivityMapper activityMapper;
	
	public List<Activity> selectActivityByType(Integer type) {
		return this.activityMapper.selectActivityByType(type);
	}

	public void insertActivity(Activity activity) {
		this.activityMapper.insertActivity(activity);
	}
	
	public void parseActivityListToJson(List<Activity> actList,JSONArray actArray){
		for (Activity activity : actList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("cover", activity.getCover());
			jsonObject.put("url", activity.getUrl());
			jsonObject.put("title", activity.getTitle());
			actArray.add(jsonObject);
		}
	}
	
	
}
