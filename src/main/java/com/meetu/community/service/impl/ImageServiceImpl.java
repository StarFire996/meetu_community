package com.meetu.community.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meetu.community.domain.Image;
import com.meetu.community.mapper.ImageMapper;
import com.meetu.community.service.ImageService;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ImageMapper imageMapper;
	
	private static String IMG_URL="http://protect-app.img-cn-beijing.aliyuncs.com/";

	public void insertImage(Image image) {
		this.imageMapper.insertImage(image);
	}

	public Image selectImageById(Integer id) {
		return this.imageMapper.selectImageById(id);
	}

	public void deleteImageById(Integer id) {
		this.imageMapper.deleteImageById(id);
	}

	// 将post中存储的image id 转换为json串
	public void parseImgToJson(JSONArray imgArray, String imgs) {
		if (StringUtils.isNotBlank(imgs)) {
			String[] imgIds = imgs.split(",");
			for (String imgId : imgIds) {
				JSONObject jsonObject = new JSONObject();
				Image image = this.imageMapper.selectImageById(Integer
						.parseInt(imgId));
				if (image!=null) {
					jsonObject.put("url", IMG_URL+image.getUrl());
					jsonObject.put("w", image.getWeight());
					jsonObject.put("h", image.getHight());
				}
				imgArray.add(jsonObject);
			}
		}
	}

	public void deleteImage(String imgs) {
		if (StringUtils.isNotBlank(imgs)) {
			String[] imgIds = imgs.split(",");
			for (String imgId : imgIds) {
				this.imageMapper.deleteImageById(Integer.parseInt(imgId));
			}
		}

	}
}
