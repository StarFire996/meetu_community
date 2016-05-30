package com.meetu.community.mapper;

import java.util.HashMap;
import java.util.List;

import com.meetu.community.domain.Comment;

public interface CommentMapper {
	
	public Comment selectCommentById(Integer id);
	
	public void insertComment(Comment comment);
	
	public void deleteCommentById(Integer id);
	
	public void deleteCommentByPostId(Integer postId);

	public List<Comment> selectCommontByPostId(HashMap<String, Object> map);

}
