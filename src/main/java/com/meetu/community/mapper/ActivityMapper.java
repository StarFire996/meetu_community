package com.meetu.community.mapper;

import java.util.List;

import com.meetu.community.domain.Activity;

public interface ActivityMapper {
	
	public List<Activity> selectActivityByType(Integer type);
	
	public void insertActivity(Activity activity);
}
