package com.meetu.community.service;

import java.sql.Timestamp;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.meetu.community.domain.Notify;

public interface NotifyService {

	public List<Notify> getNotifyList(Integer userCode,Timestamp timestamp,JSONArray notifyArr) throws Exception;

	public void insertNotify(Notify notify);

	public void setNotifyRead(Integer userCode);
	
	public void deletePraiseNotify(Notify notify);

	public void deleteNotifyByPostId(Integer postId);

	public void parseNotifyToJson(List<Notify> notifyList, JSONArray notifyArr) throws Exception;
	
	public void deleteNotifyByUserTo(Integer userTo);

	public Integer selectNotifyUnRead(Integer userFrom);
}
