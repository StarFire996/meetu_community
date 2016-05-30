package com.meetu.community.service;

import com.alibaba.fastjson.JSONArray;
import com.meetu.community.domain.Image;

public interface ImageService {
	
	public void insertImage(Image image);

	public Image selectImageById(Integer id);

	public void deleteImageById(Integer id);

	public void parseImgToJson(JSONArray imgArray, String imgs);

	public void deleteImage(String imgs);
}
