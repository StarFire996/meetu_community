package com.meetu.community.service.impl;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meetu.community.domain.Notify;
import com.meetu.community.domain.Post;
import com.meetu.community.domain.Praise;
import com.meetu.community.mapper.PraiseMapper;
import com.meetu.community.service.ImageService;
import com.meetu.community.service.NotifyService;
import com.meetu.community.service.PostService;
import com.meetu.community.service.PraiseService;

@Service
@Transactional
public class PraiseSerivceImpl implements PraiseService {

	@Autowired
	private PraiseMapper praiseMapper;
	
	@Autowired
	private NotifyService notifyService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private ImageService imageService;


	@Override
	public Praise selectPraiseById(Integer id) {
		return this.praiseMapper.selectPraiseById(id);
	}

	@Override
	public void deletePraiseById(Integer id) {
		this.praiseMapper.deletePraiseById(id);
	}

	public Integer isPraise(Praise praise) {
		Praise praise2 = this.praiseMapper.selectPraiseByUserCodeAndPostId(praise);
		if (praise2 != null) {
			return 1;
		}
		return 0;
	}

	@Override
	public void doPraise(Praise praise) {
		Post post = this.postService.selectPostById(praise.getPostId());
		Praise praise2 = this.praiseMapper.selectPraiseByUserCodeAndPostId(praise);
		if (praise2 == null) {// 动作为点赞,且赞不存在
			//帖子点赞数加1
			post.setPraiseNum(post.getPraiseNum()+1);
			this.postService.updatePost(post);
			
			//插入点赞数据
			this.praiseMapper.insertPraise(praise);
			
			//插入通知数据
			Notify notify = new Notify();
			notify.setComment("给你点了赞");
			notify.setContent(post.getContent());
			notify.setCreateAt(new Timestamp(System.currentTimeMillis()));
			notify.setIsRead(0);
			notify.setPostId(post.getId());
			notify.setType(1);
			notify.setUserFrom(praise.getUserFrom());
			notify.setUserTo(praise.getUserTo());
			String imgs = post.getImgs();
			if (StringUtils.isNotBlank(imgs)) {
				JSONArray array = new JSONArray();
				this.imageService.parseImgToJson(array, imgs);
				notify.setImgs(array.get(0).toString());
			}
			this.notifyService.insertNotify(notify);
		} else if(praise2 != null) {// 动作为取消赞,且赞存在
			//帖子点赞数减1
			Integer praiseNum = post.getPraiseNum();
			if (praiseNum >0) {
				post.setPraiseNum(praiseNum-1);
			}
			this.postService.updatePost(post);
			//删除点赞数据
			this.praiseMapper.deletePraiseById(praise2.getId());
			//删除通知
			Notify notify = new Notify();
			notify.setPostId(post.getId());
			notify.setType(1);
			notify.setUserFrom(praise2.getUserFrom());
			notify.setUserTo(praise2.getUserTo());
			this.notifyService.deletePraiseNotify(notify);
		}
	}

	public void deletePraiseByPostId(Integer postId) {
		this.praiseMapper.deletePraiseByPostId(postId);
	}

}
