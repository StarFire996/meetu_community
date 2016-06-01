package com.meetu.community.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meetu.community.domain.Notify;
import com.meetu.community.domain.User;
import com.meetu.community.mapper.NotifyMapper;
import com.meetu.community.service.NotifyService;
import com.meetu.community.service.UserService;
import com.meetu.util.StsService;

@Service
@Transactional
public class NotifyServiceImpl implements NotifyService{

	@Autowired
	private NotifyMapper notifyMapper;
	
	@Autowired
	private UserService userService;
	
	public List<Notify> getNotifyList(Integer userCode, Timestamp timestamp,JSONArray notifyArr) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("userTo", userCode);
		map.put("timestamp", timestamp);
		List<Notify> list = this.notifyMapper.selectNotifyByUserTo(map);
		if (list != null && list.size()>0) {
			this.parseNotifyToJson(list,notifyArr);
		}
		return this.notifyMapper.selectNotifyByUserTo(map);
	}

	public void insertNotify(Notify notify) {
		this.notifyMapper.insertNotify(notify);
	}

	public void setNotifyRead(Integer userCode) {
		this.notifyMapper.setNotifyRead(userCode);
	}


	public void deletePraiseNotify(Notify notify) {
		this.notifyMapper.deletePraiseNotify(notify);
	}

	public void deleteNotifyByPostId(Integer postId) {
		this.notifyMapper.deleteNotifyByPostId(postId);
	}

	public void parseNotifyToJson(List<Notify> notifyList, JSONArray notifyArr) throws Exception {
		for (Notify notify : notifyList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("type", notify.getType());
			jsonObject.put("isRead", notify.getIsRead());
			Integer userFrom = notify.getUserFrom();
			User user = this.userService.selectUserByCode(userFrom);
			String userName = user.getNickname();
			String userHead = StsService.generateCircleUrl(user.getIcon_url());
			String userSchool = user.getSchool();
			jsonObject.put("userCode", userFrom);
			jsonObject.put("userName", userName);
			jsonObject.put("userHead", userHead);
			jsonObject.put("userSchool", userSchool);
			jsonObject.put("createAt", notify.getCreateAt().getTime()/1000);
			jsonObject.put("desc", notify.getComment());
			jsonObject.put("postId", notify.getPostId());
			jsonObject.put("postImg", notify.getImgs());
			jsonObject.put("postContent", notify.getContent());
			notifyArr.add(jsonObject);
		}
	}

	public void deleteNotifyByUserTo(Integer userTo) {
		this.notifyMapper.deleteNotifyByUserTo(userTo);
	}

	public Integer selectNotifyUnRead(Integer userFrom) {
		return this.notifyMapper.selectNotifyUnRead(userFrom);
	}
	
}
