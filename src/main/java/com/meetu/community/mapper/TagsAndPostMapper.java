package com.meetu.community.mapper;

import java.util.HashMap;

public interface TagsAndPostMapper {

	public void insertObject(HashMap<String, Object> map);
	
	public void deleteTagsAndPost(Integer postId);

}
