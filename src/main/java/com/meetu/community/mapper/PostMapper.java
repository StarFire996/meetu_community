package com.meetu.community.mapper;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import com.meetu.community.domain.Post;


public interface PostMapper {
	
	public Post selectPostById(Integer id);
	
	public Integer insertPost(Post post);
	
	public void updatePost(Post post);
	
	public List<Post> selectPostListByCode(HashMap<String, Object> map);
	
	public List<Post> selectFriendPostListById(HashMap<String, Object> map);
	
	public List<Post> selectPostListByTagId(HashMap<String, Object> map);

	public List<Post> selectRecommendPostList(Timestamp timestamp);

	public List<Post> selectNewPostList(Timestamp timestamp);

	public void deletePostById(Integer postId);

}
