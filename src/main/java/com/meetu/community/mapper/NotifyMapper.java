package com.meetu.community.mapper;

import java.util.HashMap;
import java.util.List;

import com.meetu.community.domain.Notify;

public interface NotifyMapper {
	
	public List<Notify> selectNotifyByUserTo(HashMap<String, Object> map);
	
	public void insertNotify(Notify notify);
	
	public void setNotifyRead(Integer userCode);

	public void deletePraiseNotify(Notify notify);
	
	public void deleteNotifyByPostId(Integer postId);
	
	public void deleteNotifyByUserTo(Integer userTo);

	public Integer selectNotifyUnRead(Integer userFrom);
}
