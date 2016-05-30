package com.meetu.community.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meetu.community.domain.Tags;
import com.meetu.community.mapper.TagsMapper;
import com.meetu.community.service.TagsService;

@Service
@Transactional
public class TagsServiceImpl implements TagsService{
	
	@Autowired
	private TagsMapper tagsMapper;
	
	public Tags selectTagsById(Integer id) {
		return this.tagsMapper.selectTagsById(id);
	}

	public void insertTag(Tags tags) {
		this.tagsMapper.insertTag(tags);
	}

	public void updateTag(Tags tags) {
		this.tagsMapper.updateTag(tags);
	}

	public List<Tags> selectTagsListByName(String searchStr) {
		return this.tagsMapper.selectTagsListByName(searchStr);
	}

	public List<Tags> selectTagsListByRec() {
		return this.tagsMapper.selectTagsListByRec();
	}

	public List<Tags> selectTagsListByHot() {
		return this.tagsMapper.selectTagsListByHot();
	}

	public List<Tags> selectTagsListByNew(Tags tags) {
		return this.tagsMapper.selectTagsListByNew(tags);
	}

	public void parseTagsListToJson(List<Tags> list, JSONArray jsonArray) {
		for (Tags tags : list) {
			JSONObject jsonOb = new JSONObject();
			jsonOb.put("id", tags.getId());
			jsonOb.put("conteng",tags.getContent());
			jsonArray.add(jsonOb);
		}
	}

	public List<Tags> selectTagsListByPostId(Integer postId) {
		return this.tagsMapper.selectTagsListByPostId(postId);
	}

}
