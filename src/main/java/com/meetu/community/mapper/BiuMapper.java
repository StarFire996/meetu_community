package com.meetu.community.mapper;

import java.util.List;

import com.meetu.community.domain.Biu;

public interface BiuMapper {
	
	public List<Biu> selectBiuByUserCode(Integer userCodeMine);
	
	public void insertBiu(Biu biu);
	
	public void acceptBiu(Biu biu);
	
	public void setBiuRead(Integer userCodeMine);
	
	public Integer selectBiuUnRead(Integer userCodeMine);
}
