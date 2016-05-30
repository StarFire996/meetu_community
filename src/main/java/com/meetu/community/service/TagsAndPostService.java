package com.meetu.community.service;



public interface TagsAndPostService {
	
	public void insertPost(Integer postId,Integer tagsId);

	public void deleteTagsAndPostByPostId(Integer postId);
}
