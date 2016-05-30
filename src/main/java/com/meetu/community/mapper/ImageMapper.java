package com.meetu.community.mapper;

import com.meetu.community.domain.Image;

public interface ImageMapper {
	
	public void insertImage(Image image);
	
	public Image selectImageById(Integer id);
	
	public void deleteImageById(Integer id);
}
