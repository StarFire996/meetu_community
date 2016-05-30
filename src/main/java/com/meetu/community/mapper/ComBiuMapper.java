package com.meetu.community.mapper;

import java.util.HashMap;
import java.util.List;

import com.meetu.community.domain.ComBiu;

public interface ComBiuMapper {
	
	public List<ComBiu> selectBiuByUserCodeMine(HashMap<String, Object> map);
	
	public void insertBiu(ComBiu biu);
	
	public void acceptBiu(ComBiu biu);
	
	public void setBiuRead(Integer userCodeMine);
	
	public Integer selectBiuNumUnRead(Integer userCodeMine);
}
