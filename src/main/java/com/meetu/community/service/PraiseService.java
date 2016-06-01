package com.meetu.community.service;

import com.meetu.community.domain.Praise;

public interface PraiseService {


	public Praise selectPraiseById(Integer id);

	public void deletePraiseById(Integer id);

	public Integer isPraise(Praise praise);

	public void doPraise(Praise praise);

	public void deletePraiseByPostId(Integer postId);
}
