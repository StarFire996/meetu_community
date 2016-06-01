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
import com.meetu.community.domain.ComBiu;
import com.meetu.community.domain.Notify;
import com.meetu.community.domain.Post;
import com.meetu.community.service.ComBiuService;
import com.meetu.community.service.UserService;

@Controller
@RequestMapping("app/community/combiu")
public class ComBiuController {

	public static Logger LOGGER = LoggerFactory.getLogger(ComBiuController.class);

	@Autowired
	private ComBiuService biuService;
	
	@Autowired
	private UserService userService;

	// 抢一条biu
	@RequestMapping(value = "grabComBiu", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> grabComBiu(HttpServletRequest request,
			@RequestParam("data") String jsonData) {
		JSONObject json = new JSONObject();
		JSONObject json2 = new JSONObject();
		Map<String, Object> debugMap = new HashMap<String, Object>();
		try {
			// 请求参数获取
			JSONObject data = JSONObject.parseObject(jsonData);
			String newToken = (String) request.getAttribute("token");
			String userId = (String) request.getAttribute("userid");
			//被抢用户的code
			Integer userTo = data.getInteger("userCode");

			json2.put("token", newToken);
			debugMap.put("userId", userId);
			debugMap.put("userTo", userTo);

			Integer userFrom = this.userService.selectCodeById(userId);
			
			//userFrom = 12880;
			
			ComBiu biu = new ComBiu();
			biu.setCreateAt(new Timestamp(System.currentTimeMillis()));
			biu.setIsRead(0);
			biu.setStatus(0);
			biu.setUserCodeGrab(userFrom);
			biu.setUserCodeMine(userTo);
			
			this.biuService.insertBiu(biu);

			json.put("data", json2);
			json.put("state", "200");

			debugMap.put("json", json);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("sendComBiu:{}", debugMap);
			}

			return ResponseEntity.ok(json);
		} catch (Exception e) {
			json.put("state", "300");
			json.put("error", e.getMessage());
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("sendComBiu_err:{}", e.getMessage());
			}
		}
		return ResponseEntity.ok(json);
	}
	
    // 获取抢我的人的列表
	@RequestMapping(value = "getComBiuList", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> getComBiuList(
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
			
			//userFrom =12880;
			
			List<ComBiu> biuList = null;
			Timestamp timestamp = null;
			if (time == 0) {
				// 第一次查询
				timestamp = new Timestamp(System.currentTimeMillis());
				//将本次时间点之前的全部biu设为已读
				this.biuService.setBiuRead(userFrom);
			} else {
				// 加载更多
				timestamp = new Timestamp(time * 1000);
			}
			biuList = this.biuService.selectMyBiuList(userFrom,timestamp);
			
			JSONArray postArray = new JSONArray();
			if (biuList != null && biuList.size() > 0) {
				this.biuService.parseBiuToJson(biuList, postArray);
				time = biuList.get(biuList.size() - 1).getCreateAt().getTime() / 1000;
				if (biuList.size() > 30) {
					json2.put("hasNext", 1);
				} else {
					json2.put("hasNext", 0);
				}
			} else {
				json2.put("hasNext", 0);
			}
			json2.put("time", time);
			json2.put("postList", postArray);

			json.put("data", json2);
			json.put("state", "200");

			debugMap.put("json", json);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("getPostListByTag:{}", debugMap);
			}

			return ResponseEntity.ok(json);
		} catch (Exception e) {
			json.put("state", "300");
			json.put("error", e.getMessage());
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("getPostListByTag_err:{}", e.getMessage());
			}
		}
		return ResponseEntity.ok(json);
	}
	
	
	
	// 接受biu我的人
	@RequestMapping(value = "acceptComBiu", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> acceptComBiu(HttpServletRequest request,
			@RequestParam("data") String jsonData) {
		JSONObject json = new JSONObject();
		JSONObject json2 = new JSONObject();
		Map<String, Object> debugMap = new HashMap<String, Object>();
		try {
			// 请求参数获取
			JSONObject data = JSONObject.parseObject(jsonData);
			String newToken = (String) request.getAttribute("token");
			String userId = (String) request.getAttribute("userid");
			//抢我用户的code
			Integer userTo = data.getInteger("userCode");

			json2.put("token", newToken);
			debugMap.put("userId", userId);
			debugMap.put("userTo", userTo);

			Integer userFrom = this.userService.selectCodeById(userId);
			
			//userFrom = 12880;
			
			JSONObject acceptComBiu = this.biuService.acceptComBiu(userFrom,userTo);
			if (acceptComBiu.getBoolean("state")) {
				json.put("data", json2);
				json.put("state", "200");
			}else{
				json.put("state", "300");
				json.put("error", acceptComBiu.getString("error"));
			}

			debugMap.put("json", json);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("deleteComBiu:{}", debugMap);
			}

			return ResponseEntity.ok(json);
		} catch (Exception e) {
			json.put("state", "300");
			json.put("error", e.getMessage());
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("deleteComBiu_err:{}", e.getMessage());
			}
		}
		return ResponseEntity.ok(json);
	}
	
	
	// 清空biu我的人
	@RequestMapping(value = "deleteComBiu", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> deleteComBiu(HttpServletRequest request,
			@RequestParam("data") String jsonData) {
		JSONObject json = new JSONObject();
		JSONObject json2 = new JSONObject();
		Map<String, Object> debugMap = new HashMap<String, Object>();
		try {
			// 请求参数获取
			JSONObject data = JSONObject.parseObject(jsonData);
			String newToken = (String) request.getAttribute("token");
			String userId = (String) request.getAttribute("userid");
			//被抢用户的code
			Integer userTo = data.getInteger("userCode");

			json2.put("token", newToken);
			debugMap.put("userId", userId);
			debugMap.put("userTo", userTo);

			Integer userFrom = this.userService.selectCodeById(userId);
			
			//userFrom = 12880;
			
			this.biuService.deleteBiuByUserCode(userFrom);

			json.put("data", json2);
			json.put("state", "200");

			debugMap.put("json", json);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("deleteComBiu:{}", debugMap);
			}

			return ResponseEntity.ok(json);
		} catch (Exception e) {
			json.put("state", "300");
			json.put("error", e.getMessage());
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("deleteComBiu_err:{}", e.getMessage());
			}
		}
		return ResponseEntity.ok(json);
	}

}
