package com.meetu.community.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.expr.NewArray;

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
import com.meetu.community.domain.Tags;
import com.meetu.community.service.TagsService;

@Controller
@RequestMapping("app/community/tag")
public class TagsController {
	
	public static Logger LOGGER = LoggerFactory.getLogger(TagsController.class);

	@Autowired
	private TagsService tagsService;

	@RequestMapping(value = "createTag", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> createTag(HttpServletRequest request,
			@RequestParam("data") String jsonData) {
		JSONObject json = new JSONObject();
		JSONObject json2 = new JSONObject();
		Map<String, Object> debugMap = new HashMap<String, Object>();
		try {
			//请求参数获取
			JSONObject data = JSONObject.parseObject(jsonData);
			String newToken = (String) request.getAttribute("token");
			String userId = (String) request.getAttribute("userid");
			String content = data.getString("content");
			
			json2.put("token", newToken);
			
			debugMap.put("content", content);
			debugMap.put("userId", userId);
			Tags tags = new Tags();
			tags.setContent(content);
			tags.setCreateAt(new Timestamp(System.currentTimeMillis()));
			tags.setLevel(0);
			tags.setPostNum(0);
			this.tagsService.insertTag(tags);
			
			
			json2.put("tagId", tags.getId());
			json.put("data", json2);
			json.put("state", "200");
			
			debugMap.put("json", json);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("createTag:{}",debugMap);
			}
			
			return ResponseEntity.ok(json);
		} catch (Exception e) {
			json.put("state", "300");
			json.put("error", e.getMessage());
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("createTag_err:{}",e.getMessage());
			}
		}
		return ResponseEntity.ok(json);
	}
	
	@RequestMapping(value = "getTagByName", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> getTagByName(HttpServletRequest request,
			@RequestParam("data") String jsonData) {
		JSONObject json = new JSONObject();
		JSONObject json2 = new JSONObject();
		Map<String, Object> debugMap = new HashMap<String, Object>();
		try {
			//请求参数获取
			JSONObject data = JSONObject.parseObject(jsonData);
			String newToken = (String) request.getAttribute("token");
			String userId = (String) request.getAttribute("userid");
			String searchStr = data.getString("searchStr");
			
			json2.put("token", newToken);
			
			debugMap.put("searchStr", searchStr);
			debugMap.put("userId", userId);
			
			List<Tags> list = this.tagsService.selectTagsListByName(searchStr);
			JSONArray jsonArray = new JSONArray();
			
			if (list != null && list.size()>0) {
				this.tagsService.parseTagsListToJson(list, jsonArray);
			}
			
			json2.put("tags", jsonArray);
			json.put("data", json2);
			json.put("state", "200");
			debugMap.put("json", json);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("getTagByName:{}",debugMap);
			}
			return ResponseEntity.ok(json);
		} catch (Exception e) {
			json.put("state", "300");
			json.put("error", e.getMessage());
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("getTagByName_err:{}",e.getMessage());
			}
		}
		return ResponseEntity.ok(json);
	}
	
	@RequestMapping(value = "getTagList", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> getTagList(HttpServletRequest request,
			@RequestParam("data") String jsonData) {
		JSONObject json = new JSONObject();
		JSONObject json2 = new JSONObject();
		Map<String, Object> debugMap = new HashMap<String, Object>();
		try {
			//请求参数获取
			JSONObject data = JSONObject.parseObject(jsonData);
			String newToken = (String) request.getAttribute("token");
			String userId = (String) request.getAttribute("userid");
			Long time = data.getLong("time");
			Integer postNum = data.getInteger("postNum");
			
			json2.put("token", newToken);
			
			debugMap.put("time", time);
			debugMap.put("postNum", postNum);
			debugMap.put("userId", userId);
			//第一次访问,返回三种数组
			if (time == 0) {
				//推荐话题
				List<Tags> recList = this.tagsService.selectTagsListByRec();
				JSONArray recArray = new JSONArray();
				if (recList != null && recList.size()>0) {
					this.tagsService.parseTagsListToJson(recList, recArray);
				}
				json2.put("recommend", recArray);
				//热度话题
				List<Tags> hotList = this.tagsService.selectTagsListByHot();
				JSONArray hotArray = new JSONArray();
				if (hotList != null && hotList.size()>0) {
					this.tagsService.parseTagsListToJson(hotList, hotArray);
					//热度的最后一个帖子数
					postNum = hotList.get(hotList.size()-1).getPostNum();
				}
				
				json2.put("hot", hotArray);
				json2.put("postNum", postNum);
				
				//新鲜话题
				Tags tags1 = new Tags();
				tags1.setCreateAt(new Timestamp(System.currentTimeMillis()));
				tags1.setPostNum(postNum);
				List<Tags> newList = this.tagsService.selectTagsListByNew(tags1);
				JSONArray newArray = new JSONArray();
				if (newList != null && newList.size()>0) {
					this.tagsService.parseTagsListToJson(newList, newArray);
					//新鲜的最后一个标签的时间戳 秒
					time = newList.get(newList.size()-1).getCreateAt().getTime()/1000;
					if (newList.size()>10) {
						json2.put("hasNext", 1);
					}else{
						json2.put("hasNext", 0);
					}
				}else{
					json2.put("hasNext", 0);
				}
				json2.put("time", time);
				json2.put("new", newArray);
			}else{//加载更多新鲜话题
				Tags tags1 = new Tags();
				tags1.setCreateAt(new Timestamp(time*1000));
				tags1.setPostNum(postNum);
				List<Tags> newList = this.tagsService.selectTagsListByNew(tags1);
				JSONArray newArray = new JSONArray();
				if (newList != null && newList.size()>0) {
					this.tagsService.parseTagsListToJson(newList, newArray);
					//新鲜的最后一个标签的时间戳 秒
					time = newList.get(newList.size()-1).getCreateAt().getTime()/1000;
					if (newList.size()>9) {
						json2.put("hasNext", 1);
					}else{
						json2.put("hasNext", 0);
					}
				}else{
					json2.put("hasNext", 0);
				}
				json2.put("time", time);
				json2.put("new", newArray);
			}
			
			json.put("data", json2);
			json.put("state", "200");
			
			debugMap.put("json", json);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("getTagList:{}",debugMap);
			}
			return ResponseEntity.ok(json);
		} catch (Exception e) {
			json.put("state", "300");
			json.put("error", e.getMessage());
			if (LOGGER.isErrorEnabled()) {
				LOGGER.error("getTagList_err:{}",e.getMessage());
			}
		}
		return ResponseEntity.ok(json);
	}

}
