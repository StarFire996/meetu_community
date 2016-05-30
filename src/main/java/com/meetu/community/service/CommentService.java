package com.meetu.community.service;

import java.sql.Timestamp;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.meetu.community.domain.Comment;

public interface CommentService {

	public void saveComment(Comment comment);

	public void deleteCommentById(Integer commentId);

	public void deleteCommentByPostId(Integer postId);

	public List<Comment> selectCommontByPostId(Integer postId,Timestamp timestamp);

	public void parseCommentListToJson(List<Comment> comList, JSONArray comArray) throws Exception;

}
