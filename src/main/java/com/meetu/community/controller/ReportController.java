package com.meetu.community.controller;

import java.sql.Timestamp;
import java.util.HashMap;
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

import com.alibaba.fastjson.JSONObject;
import com.meetu.community.domain.Report;
import com.meetu.community.service.ReportService;
import com.meetu.community.service.UserService;

@Controller
@RequestMapping("app/community/report")
public class ReportController {
	
	public static Logger LOGGER = LoggerFactory.getLogger(ReportController.class);
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "createReport", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> createReport(HttpServletRequest request,
			@RequestParam("data") String jsonData) {
		JSONObject json = new JSONObject();
		JSONObject json2 = new JSONObject();
		Map<String, Object> debugMap = new HashMap<String, Object>();
		try {
			//请求参数获取
			JSONObject data = JSONObject.parseObject(jsonData);
			String newToken = (String) request.getAttribute("token");
			String userId = (String) request.getAttribute("userid");
			Integer postId = data.getInteger("postId");
			Integer commentId = data.getInteger("commentId");
			Integer userTo = data.getInteger("userCode");
			
			json2.put("token", newToken);
			
			debugMap.put("postId", postId);
			debugMap.put("commentId", commentId);
			debugMap.put("userCode", userTo);
			debugMap.put("userId", userId);
			
			Integer userFrom = this.userService.selectCodeById(userId);
			
//			userFrom = 12880;
			
			Report report = new Report();
			report.setCommentId(commentId);
			report.setCreateAt(new Timestamp(System.currentTimeMillis()));
			report.setPostId(postId);
			report.setUserFrom(userFrom);
			report.setUserTo(userTo);
			
			this.reportService.insertReport(report);
			
			json.put("data", json2);
			json.put("state", "200");
			
			debugMap.put("json", json);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("createReport:{}",debugMap);
			}
			
			return ResponseEntity.ok(json);
		} catch (Exception e) {
			json.put("state", "300");
			json.put("error", e.getMessage());
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("createReport_err:{}",e.getMessage());
			}
		}
		return ResponseEntity.ok(json);
	}
}
