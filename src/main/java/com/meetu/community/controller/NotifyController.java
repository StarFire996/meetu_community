package com.meetu.community.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meetu.community.domain.Notify;
import com.meetu.community.service.NotifyService;
import com.meetu.community.service.UserService;

@Controller
@RequestMapping("app/community/notify")
public class NotifyController {
	
	public static Logger LOGGER = LoggerFactory.getLogger(NotifyController.class);
	
	@Autowired
	private NotifyService notifyService;
	
	@Autowired
	private UserService userService;
	
	// 获取自己的全部通知
	@RequestMapping(value = "getNotifyList", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> getNotifyList(
			HttpServletRequest request, @RequestParam("data") String jsonData) {
		JSONObject json = new JSONObject();
		JSONObject json2 = new JSONObject();
		Map<String, Object> debugMap = new HashMap<String, Object>();
		try {
			// 请求参数获取
			JSONObject data = JSONObject.parseObject(jsonData);
			String newToken = (String) request.getAttribute("token");
			String userId = (String) request.getAttribute("userid");
			Long time = data.getLong("time");

			json2.put("token", newToken);
			debugMap.put("userId", userId);
			debugMap.put("time", time);

			Integer userFrom = this.userService.selectCodeById(userId);
//			userFrom = 12880;
			
			List<Notify> notifyList = null;
			Timestamp timestamp = null;
			if (time == 0) {
				// 第一次查询
				timestamp = new Timestamp(System.currentTimeMillis());
			} else {
				// 加载更多
				timestamp = new Timestamp(time * 1000);
			}
					
			JSONArray notifyArr = new JSONArray();
			notifyList = this.notifyService.getNotifyList(userFrom,timestamp,notifyArr);
			if (notifyList != null && notifyList.size()>0) {
				time = notifyList.get(notifyList.size() - 1).getCreateAt().getTime() / 1000;
				if (notifyList.size() > 10) {
					json2.put("hasNext", 1);
				} else {
					json2.put("hasNext", 0);
				}
			}else{
				json2.put("hasNext", 0);
			}
			
			json2.put("time", time);
			json2.put("notifies", notifyArr);
			
			json.put("data", json2);
			json.put("state", "200");

			debugMap.put("json", json);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("getNotifyList:{}", debugMap);
			}

			return ResponseEntity.ok(json);
		} catch (Exception e) {
			json.put("state", "300");
			json.put("error", e.getMessage());
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("getNotifyList_err:{}", e.getMessage());
			}
		}
		return ResponseEntity.ok(json);
	}
	
	// 获取自己的全部通知
	@RequestMapping(value = "deletNotifies", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> deletNotifies(
			HttpServletRequest request, @RequestParam("data") String jsonData) {
		JSONObject json = new JSONObject();
		JSONObject json2 = new JSONObject();
		Map<String, Object> debugMap = new HashMap<String, Object>();
		try {
			// 请求参数获取
			JSONObject data = JSONObject.parseObject(jsonData);
			String newToken = (String) request.getAttribute("token");
			String userId = (String) request.getAttribute("userid");

			json2.put("token", newToken);
			debugMap.put("userId", userId);

			Integer userFrom = this.userService.selectCodeById(userId);
			//userFrom = 12880;
			
			this.notifyService.deleteNotifyByUserTo(userFrom);
			json.put("data", json2);
			json.put("state", "200");

			debugMap.put("json", json);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("deletNotifies:{}", debugMap);
			}

			return ResponseEntity.ok(json);
		} catch (Exception e) {
			json.put("state", "300");
			json.put("error", e.getMessage());
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("deletNotifies_err:{}", e.getMessage());
			}
		}
		return ResponseEntity.ok(json);
	}

}
