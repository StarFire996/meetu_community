package com.meetu.community.service;

import java.sql.Timestamp;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meetu.community.domain.ComBiu;

public interface ComBiuService {
	
	public void insertBiu(ComBiu biu);

	public void setBiuRead(Integer userCodeMine);
	
	public Integer selectBiuUnRead(Integer userCodeMine);

	public List<ComBiu> selectMyBiuList(Integer userFrom,Timestamp timestamp);
	
	public void parseBiuToJson(List<ComBiu> biuList, JSONArray biuArr) throws Exception;

	public void deleteBiuByUserCode(Integer userFrom);

	public JSONObject acceptComBiu(Integer userFrom, Integer userTo) throws Exception;
}
