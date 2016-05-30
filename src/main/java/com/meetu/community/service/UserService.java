package com.meetu.community.service;

import java.util.List;
import java.util.Map;

import com.meetu.community.domain.User;

public interface UserService {

	public void insertOper(User user) throws Exception;
	public String checkPhoneAndPwd(User user) throws Exception;
	public void deleteByPrimaryKey(String id) throws Exception;
	public String selectIdByPhone(String phone) throws Exception;
	public User selectUserByLoginName(String loginName) throws Exception;
	public Integer selectCodeById(String id) throws Exception;
	
	public User selectUserById(String id) throws Exception;
	public User selectUserByCode(Integer code) throws Exception;
	public void updateUserInfo(User user) throws Exception;
	public void updatePwdByPhone(User user) throws Exception;
	public void updateIcon(User user) throws Exception;
	public List<Map<String, Object>> selectBiu(Map<String, Object> map) throws Exception;
	public void updateLocation(User user) throws Exception;
	
	public void addVC(User user) throws Exception;
	public boolean isEnough(String userid, Integer vc) throws Exception;
	public String selectIdByCode(Integer code) throws Exception;
	public Integer selectVC(String id);
	public Integer selectCodeByPhone(String phone);
	
	public List<Map<String, Object>> selectBiuTest(String id);
	public String selectSexById(String userid);
	public String selectAppStatus(String userid);
	public List<Map<String, Object>> paginate(int start, int pageSize);
	public List<Map<String, Object>> selectTargetBiuList(Map<String, Object> map);
}
