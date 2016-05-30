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
import com.meetu.community.domain.Comment;
import com.meetu.community.service.CommentService;
import com.meetu.community.service.UserService;

@Controller
@RequestMapping("app/community/comment")
public class CommentController {
	
	public static Logger LOGGER = LoggerFactory.getLogger(TagsController.class);
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "createComment", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> createComment(HttpServletRequest request,
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
			Integer parentId = data.getInteger("parentId");
			Integer userTo = data.getInteger("toUserCode");
			String content = data.getString("content");
			
			json2.put("token", newToken);
			
			debugMap.put("content", content);
			debugMap.put("userId", userId);
			debugMap.put("postId", postId);
			debugMap.put("parentId", parentId);
			debugMap.put("userTo", userTo);
			
			Integer userFrom = this.userService.selectCodeById(userId);
			
//			userFrom = 10119;
			
			Comment comment = new Comment();
			comment.setContent(content);
			comment.setCreateAt(new Timestamp(System.currentTimeMillis()));
			comment.setParentId(parentId);
			comment.setPostId(postId);
			comment.setUserFrom(userFrom);
			comment.setUserTo(userTo);
			
			this.commentService.saveComment(comment);
			
			json.put("commentId", comment.getId());
			json.put("data", json2);
			json.put("state", "200");
			
			debugMap.put("json", json);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("createComment:{}",debugMap);
			}
			
			return ResponseEntity.ok(json);
		} catch (Exception e) {
			json.put("state", "300");
			json.put("error", e.getMessage());
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("createComment_err:{}",e.getMessage());
			}
		}
		return ResponseEntity.ok(json);
	}
	
	@RequestMapping(value = "deleteComment", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> deleteComment(HttpServletRequest request,
			@RequestParam("data") String jsonData) {
		JSONObject json = new JSONObject();
		JSONObject json2 = new JSONObject();
		Map<String, Object> debugMap = new HashMap<String, Object>();
		try {
			//请求参数获取
			JSONObject data = JSONObject.parseObject(jsonData);
			String newToken = (String) request.getAttribute("token");
			String userId = (String) request.getAttribute("userid");
			Integer commentId = data.getInteger("commentId");
			
			json2.put("token", newToken);
			
			debugMap.put("commentId", commentId);
			debugMap.put("userId", userId);
			
			
			this.commentService.deleteCommentById(commentId);
			
			json.put("data", json2);
			json.put("state", "200");
			
			debugMap.put("json", json);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("deleteComment:{}",debugMap);
			}
			
			return ResponseEntity.ok(json);
		} catch (Exception e) {
			json.put("state", "300");
			json.put("error", e.getMessage());
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("deleteComment_err:{}",e.getMessage());
			}
		}
		return ResponseEntity.ok(json);
	}
}	
