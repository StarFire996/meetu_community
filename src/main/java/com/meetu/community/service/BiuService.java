package com.meetu.community.service;

import java.util.List;

import com.meetu.community.domain.Biu;

public interface BiuService {
	
	public List<Biu> selectBiuByUserCode(Integer userCodeMine);

	public void insertBiu(Biu biu);

	public void acceptBiu(Biu biu);

	public void setBiuRead(Integer userCodeMine);
	
	public Integer selectBiuUnRead(Integer userCodeMine);
}
