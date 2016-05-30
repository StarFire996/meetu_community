package com.meetu.community.mapper;

import com.meetu.community.domain.Praise;

public interface PraiseMapper {
	
	public void insertPraise(Praise praise);
	
	public Praise selectPraiseById(Integer id);
	
	public void deletePraiseById(Integer id);
	
	public void deletePraiseByPostId(Integer postId);

	public Praise selectPraiseByUserCodeAndPostId(Praise praise);
	
	
}
