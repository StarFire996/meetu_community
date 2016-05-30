package com.meetu.community.mapper;

import java.util.List;

import com.meetu.community.domain.Tags;


public interface TagsMapper {
	
	public Tags selectTagsById(Integer id);
	
	public void insertTag(Tags tags);
	
	public void updateTag(Tags tags);

	public List<Tags> selectTagsListByName(String searchStr);
	
	public List<Tags> selectTagsListByRec();
	
	public List<Tags> selectTagsListByHot();
	
	public List<Tags> selectTagsListByNew(Tags tags);

	public List<Tags> selectTagsListByPostId(Integer postId);
}
