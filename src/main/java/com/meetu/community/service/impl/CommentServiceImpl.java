package com.meetu.community.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meetu.community.domain.Comment;
import com.meetu.community.domain.Notify;
import com.meetu.community.domain.Post;
import com.meetu.community.domain.User;
import com.meetu.community.mapper.CommentMapper;
import com.meetu.community.service.CommentService;
import com.meetu.community.service.NotifyService;
import com.meetu.community.service.PostService;
import com.meetu.community.service.UserService;
import com.meetu.util.StsService;

@Service
@Transactional
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private CommentMapper commentMapper;
	
	@Autowired
	private NotifyService notifyService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private UserService userService;
	
	public void saveComment(Comment comment) {
		//插入评论
		this.commentMapper.insertComment(comment);
		//帖子评论计数加1
		Post post = this.postService.selectPostById(comment.getPostId());
		post.setCommentNum(post.getCommentNum()+1);
		this.postService.updatePost(post);
		
		//插入通知
		Notify notify = new Notify();
		notify.setComment(comment.getContent());
		notify.setContent(post.getContent());
		notify.setCreateAt(new Timestamp(System.currentTimeMillis()));
		notify.setIsRead(0);
		notify.setPostId(post.getId());
		notify.setType(0);
		notify.setUserFrom(comment.getUserFrom());
		notify.setUserTo(comment.getUserTo());
		
		String imgs = post.getImgs();
		if (StringUtils.isNotBlank(imgs)) {
			JSONArray array = JSONObject.parseArray(imgs);
			notify.setImgs(array.get(0).toString());
		}
		this.notifyService.insertNotify(notify);
		
		//推送通知(待定)

	}

	@Override
	public void deleteCommentById(Integer commentId) {
		this.commentMapper.deleteCommentById(commentId);
	}

	@Override
	public void deleteCommentByPostId(Integer postId) {
		this.commentMapper.deleteCommentByPostId(postId);
	}

	@Override
	public List<Comment> selectCommontByPostId(Integer postId,Timestamp timestamp) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("postId", postId);
		map.put("timestamp", timestamp);
		return this.commentMapper.selectCommontByPostId(map);
	}

	@Override
	public void parseCommentListToJson(List<Comment> comList, JSONArray comArray) throws Exception {
		for (Comment comment : comList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("commentId", comment.getId());
			jsonObject.put("parentId", comment.getParentId());
			Integer userFromCode = comment.getUserFrom();
			User userFrom =this.userService.selectUserByCode(userFromCode);
			String userFromName = userFrom.getNickname();
			String userFromHead = StsService.generateCircleUrl(userFrom.getIcon_url());
			String userFromSchool = userFrom.getSchool();
			String userFromSex = userFrom.getSex();
			jsonObject.put("userFromCode", userFromCode);
			jsonObject.put("userFromName", userFromName);
			jsonObject.put("userFromHead", userFromHead);
			jsonObject.put("userFromSchool", userFromSchool);
			jsonObject.put("userFromSex", userFromSex);
			
			Integer userToCode = comment.getUserTo();
			User userTo =this.userService.selectUserByCode(userToCode);
			String userToName = userTo.getNickname();
			String userToSex = userTo.getSex();
			jsonObject.put("userToCode", userToCode);
			jsonObject.put("userToName", userToName);
			jsonObject.put("userToSex", userToSex);
			jsonObject.put("content", comment.getContent());
			jsonObject.put("createAt", comment.getCreateAt().getTime()/1000);
			comArray.add(jsonObject);
		}
	}
}
