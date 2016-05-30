package com.meetu.community.service;

import java.util.List;
import java.util.Map;

import com.meetu.community.domain.MeetuFriendsRel;

public interface MeetuFriendsRelService {

	public void insertOper(MeetuFriendsRel friendsRel) throws Exception;
	public MeetuFriendsRel isFriends(Map<String,Object> ids) throws Exception;
	public void deleteByUserIds(String user_id1, String user_id2)throws Exception;
	public List<Map<String, Object>> selectFriendsListByUserId(String userid);
	public List<Integer> selectFriendsCodeListByUserId(String userId);
}
