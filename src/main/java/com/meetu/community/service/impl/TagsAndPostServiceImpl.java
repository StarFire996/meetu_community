package com.meetu.community.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meetu.community.mapper.TagsAndPostMapper;
import com.meetu.community.service.TagsAndPostService;

@Service
@Transactional
public class TagsAndPostServiceImpl implements TagsAndPostService{

	@Autowired
	private TagsAndPostMapper tagsAndPostMapper;
	
	@Override
	public void insertPost(Integer postId,Integer tagsId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("postId", postId);
		map.put("tagsId", tagsId);
		this.tagsAndPostMapper.insertObject(map);
	}

	@Override
	public void deleteTagsAndPostByPostId(Integer postId) {
		this.tagsAndPostMapper.deleteTagsAndPost(postId);
	}


}
