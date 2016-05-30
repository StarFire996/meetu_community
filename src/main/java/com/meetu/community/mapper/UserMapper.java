package com.meetu.community.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.meetu.community.domain.User;

public interface UserMapper {

	public User selectUserById(String userId);
	public User selectUserByLoginName(String loginName);
	public User selectUserByCode(Integer code);
	
	public void insertOper(User user);
	
	public Integer checkPhone(String phone);
	public String checkPhoneAndPwd(User user);
	public void deleteByPrimaryKey(String id);
	public String selectIdByPhone(String phone);
	public Integer selectCodeById(String id);
	public void updateUserInfo(User user);
	public void updatePwdByPhone(User user);
	public void updateIcon(User user);
	public List<Map<String, Object>> selectBiu(Map<String, Object> map);
	public List<Map<String, Object>> selectBiuTest(String id);
	
	public void updateLocation(User user);
	public void addVC(User user);
	public String isEnough(Map<String, Object> map);
	public String selectIdByCode(Integer code);
	
	public Integer selectVC(String id);
	
	public Integer selectCodeByPhone(String phone);
	public String selectSexById(String userid);
	public String selectAppStatus(String id);
	public List<Map<String, Object>> paginate(@Param("start") int start, @Param("pageSize") int pageSize);
	
	public List<String> selectAllUserId();
	public List<Map<String, Object>> selectTargetBiuList(Map<String, Object> map);
}
