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
import com.meetu.community.domain.Activity;
import com.meetu.community.domain.Comment;
import com.meetu.community.domain.Post;
import com.meetu.community.domain.User;
import com.meetu.community.service.ActivityService;
import com.meetu.community.service.CommentService;
import com.meetu.community.service.PostService;
import com.meetu.community.service.UserService;

@Controller
@RequestMapping("app/community/post")
public class PostController {

	public static Logger LOGGER = LoggerFactory.getLogger(PostController.class);

	@Autowired
	private PostService postService;

	@Autowired
	private UserService userService;

	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private CommentService commentService;

	// 发布帖子
	@RequestMapping(value = "createPost", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> createPost(HttpServletRequest request,
			@RequestParam("data") String jsonData) {
		JSONObject json = new JSONObject();
		JSONObject json2 = new JSONObject();
		Map<String, Object> debugMap = new HashMap<String, Object>();
		try {
			// 请求参数获取
			JSONObject data = JSONObject.parseObject(jsonData);
			String newToken = (String) request.getAttribute("token");
			String userId = (String) request.getAttribute("userid");
			Integer type = data.getInteger("type");
			String tags = data.getString("tags");
			String imgs = data.getString("imgs");
			String content = data.getString("content");
			String music = data.getString("music");

			json2.put("token", newToken);
			debugMap.put("userId", userId);
			debugMap.put("type", type);
			debugMap.put("tags", tags);
			debugMap.put("imgs", imgs);
			debugMap.put("content", content);
			debugMap.put("music", music);

			User user = this.userService.selectUserById(userId);

			Post post = new Post();
			post.setCommentNum(0);
			post.setContent(content);
			post.setCreateAt(new Timestamp(System.currentTimeMillis()));
			post.setCreateBy(user.getCode());

			//post.setCreateBy(10119);

			post.setLevel(0);
			post.setMusic(music);
			post.setPraiseNum(0);
			post.setType(type);

			this.postService.savePost(post, tags,imgs);
			
			json2.put("postId", post.getId());
			json.put("data", json2);
			json.put("state", "200");

			debugMap.put("json", json);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("createPost:{}", debugMap);
			}

			return ResponseEntity.ok(json);
		} catch (Exception e) {
			json.put("state", "300");
			json.put("error", e.getMessage());
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("createPost_err:{}", e.getMessage());
			}
		}
		return ResponseEntity.ok(json);
	}

	// 根据话题列别获取帖子
	@RequestMapping(value = "getPostListByTag", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> getPostListByTag(
			HttpServletRequest request, @RequestParam("data") String jsonData) {
		JSONObject json = new JSONObject();
		JSONObject json2 = new JSONObject();
		Map<String, Object> debugMap = new HashMap<String, Object>();
		try {
			// 请求参数获取
			JSONObject data = JSONObject.parseObject(jsonData);
			String newToken = (String) request.getAttribute("token");
			String userId = (String) request.getAttribute("userid");
			Integer tagId = data.getInteger("tagId");
			Long time = data.getLong("time");

			json2.put("token", newToken);
			debugMap.put("userId", userId);
			debugMap.put("tagId", tagId);
			debugMap.put("time", time);

			Integer userFrom = this.userService.selectCodeById(userId);
			
			//userFrom =12880;
			
			List<Post> list = null;

			if (time == 0) {
				// 第一次查询
				list = this.postService.selectPostListByTagId(new Timestamp(
						System.currentTimeMillis()), tagId);
			} else {
				// 加载更多
				list = this.postService.selectPostListByTagId(new Timestamp(
						time * 1000), tagId);
			}
			JSONArray postArray = new JSONArray();
			if (list != null && list.size() > 0) {
				this.postService.parsePostListToJson(userFrom, list, postArray);
				time = list.get(list.size() - 1).getCreateAt().getTime() / 1000;
				if (list.size() > 10) {
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

	// 首页根据话题类别获取帖子
	@RequestMapping(value = "getPostListByType", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> getPostListByType(
			HttpServletRequest request, @RequestParam("data") String jsonData) {
		JSONObject json = new JSONObject();
		JSONObject json2 = new JSONObject();
		Map<String, Object> debugMap = new HashMap<String, Object>();
		try {
			// 请求参数获取
			JSONObject data = JSONObject.parseObject(jsonData);
			String newToken = (String) request.getAttribute("token");
			String userId = (String) request.getAttribute("userid");
			Integer type = data.getInteger("type");
			Long time = data.getLong("time");

			json2.put("token", newToken);
			debugMap.put("userId", userId);
			debugMap.put("type", type);
			debugMap.put("time", time);

			Integer userFrom = this.userService.selectCodeById(userId);
//			userFrom =12880;
//			userId="1511751ce0eb46dd9fd1f87b49949ee0";
			
			
			List<Post> postList = null;
			List<Activity> actList = null;
			Timestamp timestamp = null;
			if (time == 0) {
				// 第一次查询
				timestamp = new Timestamp(System.currentTimeMillis());
			} else {
				// 加载更多
				timestamp = new Timestamp(time * 1000);
			}
			if (type == 0) {
				// 新鲜的帖子
				postList = this.postService.selectNewPostList(timestamp);
			} else if (type == 1) {
				// 推荐的帖子
				postList = this.postService.selectRecommendPostList(timestamp);
			} else if (type == 2) {
				// 好友的帖子
				postList = this.postService.selectFriendPostList(userId, timestamp);
			}
			//活动banner
			actList = this.activityService.selectActivityByType(type);
			
			
			JSONArray actArray = new JSONArray();
			if (actList != null && actList.size() > 0) {
				this.activityService.parseActivityListToJson(actList, actArray);
			}
			json2.put("banner", actArray);

			JSONArray postArray = new JSONArray();
			if (postList != null && postList.size() > 0) {
				this.postService.parsePostListToJson(userFrom, postList, postArray);
				time = postList.get(postList.size() - 1).getCreateAt().getTime() / 1000;
				if (postList.size() > 10) {
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
				LOGGER.info("getPostListByType:{}", debugMap);
			}

			return ResponseEntity.ok(json);
		} catch (Exception e) {
			json.put("state", "300");
			json.put("error", e.getMessage());
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("getPostListByType_err:{}", e.getMessage());
			}
		}
		return ResponseEntity.ok(json);
	}
	
	
	// 获取帖子详情
	@RequestMapping(value = "getPostDetail", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> getPostDetail(
			HttpServletRequest request, @RequestParam("data") String jsonData) {
		JSONObject json = new JSONObject();
		JSONObject json2 = new JSONObject();
		Map<String, Object> debugMap = new HashMap<String, Object>();
		try {
			// 请求参数获取
			JSONObject data = JSONObject.parseObject(jsonData);
			String newToken = (String) request.getAttribute("token");
			String userId = (String) request.getAttribute("userid");
			Integer postId = data.getInteger("postId");
			Long time = data.getLong("time");

			json2.put("token", newToken);
			debugMap.put("userId", userId);
			debugMap.put("postId", postId);
			debugMap.put("time", time);

			Integer userFrom = this.userService.selectCodeById(userId);
			userFrom =12880;
			userId="1511751ce0eb46dd9fd1f87b49949ee0";
			
			
			List<Comment> comList = null;
			Timestamp timestamp = null;
			if (time == 0) {
				// 第一次查询
				timestamp = new Timestamp(System.currentTimeMillis());
			} else {
				// 加载更多
				timestamp = new Timestamp(time * 1000);
			}

			Post post = this.postService.selectPostById(postId);
			
			JSONObject postJson = new JSONObject();
			
			this.postService.parsePostToJson(userFrom, post, postJson);
			
			comList = this.commentService.selectCommontByPostId(postId,timestamp);
			
			
			JSONArray comArray = new JSONArray();
			if (comList != null && comList.size() > 0) {
				this.commentService.parseCommentListToJson(comList,comArray);
				time = comList.get(comList.size() - 1).getCreateAt().getTime() / 1000;
				if (comList.size() > 10) {
					json2.put("hasNext", 1);
				} else {
					json2.put("hasNext", 0);
				}
			} else {
				json2.put("hasNext", 0);

			}
			
			json2.put("post", postJson);
			
			json2.put("time", time);
			json2.put("commentList", comArray);

			json.put("data", json2);
			json.put("state", "200");

			debugMap.put("json", json);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("getPostDetail:{}", debugMap);
			}

			return ResponseEntity.ok(json);
		} catch (Exception e) {
			json.put("state", "300");
			json.put("error", e.getMessage());
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("getPostDetail_err:{}", e.getMessage());
			}
		}
		return ResponseEntity.ok(json);
	}
	
	
	// 首页根据话题类别获取帖子
	@RequestMapping(value = "getMyPostList", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> getMyPostList(
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
//			userFrom =12880;
			
			List<Post> postList = null;
			Timestamp timestamp = null;
			if (time == 0) {
				// 第一次查询
				timestamp = new Timestamp(System.currentTimeMillis());
			} else {
				// 加载更多
				timestamp = new Timestamp(time * 1000);
			}
			
			postList = this.postService.selectPostListByUserCode(userFrom,timestamp);

			JSONArray postArray = new JSONArray();
			if (postList != null && postList.size() > 0) {
				this.postService.parsePostListToJson(userFrom, postList, postArray);
				time = postList.get(postList.size() - 1).getCreateAt().getTime() / 1000;
				if (postList.size() > 10) {
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
				LOGGER.info("getMyPostList:{}", debugMap);
			}

			return ResponseEntity.ok(json);
		} catch (Exception e) {
			json.put("state", "300");
			json.put("error", e.getMessage());
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("getMyPostList_err:{}", e.getMessage());
			}
		}
		return ResponseEntity.ok(json);
	}
	// 删除帖子
	@RequestMapping(value = "deletePost", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> deletePost(
			HttpServletRequest request, @RequestParam("data") String jsonData) {
		JSONObject json = new JSONObject();
		JSONObject json2 = new JSONObject();
		Map<String, Object> debugMap = new HashMap<String, Object>();
		try {
			// 请求参数获取
			JSONObject data = JSONObject.parseObject(jsonData);
			String newToken = (String) request.getAttribute("token");
			String userId = (String) request.getAttribute("userid");
			Integer postId = data.getInteger("postId");

			json2.put("token", newToken);
			debugMap.put("userId", userId);
			debugMap.put("postId", postId);

			Integer userFrom = this.userService.selectCodeById(userId);
//			userFrom =12880;
			
			Post post = this.postService.selectPostById(postId);
			if (post != null && userFrom.equals(post.getCreateBy())) {
				this.postService.deletePost(postId);
			}

			json.put("data", json2);
			json.put("state", "200");

			debugMap.put("json", json);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("deletePost:{}", debugMap);
			}

			return ResponseEntity.ok(json);
		} catch (Exception e) {
			json.put("state", "300");
			json.put("error", e.getMessage());
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("deletePost_err:{}", e.getMessage());
			}
		}
		return ResponseEntity.ok(json);
	}
}
