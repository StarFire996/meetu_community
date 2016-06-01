package com.meetu.community.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meetu.community.domain.User;
import com.meetu.community.mapper.UserMapper;
import com.meetu.community.service.UserService;
import com.meetu.util.RedisUtil;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userDao;
	
	@Override
	public void insertOper(User user)  throws Exception{
		userDao.insertOper(user);
	}


	@Override
	public String checkPhoneAndPwd(User user) {
		return userDao.checkPhoneAndPwd(user);
	}

	@Override
	public void deleteByPrimaryKey(String id) throws Exception {
		userDao.deleteByPrimaryKey(id);
	}


	@Override
	public String selectIdByPhone(String phone) throws Exception {
		return userDao.selectIdByPhone(phone);
	}


	@Override
	public User selectUserByLoginName(String loginName) {
		return userDao.selectUserByLoginName(loginName);
	}


	@Override
	public Integer selectCodeById(String id) throws Exception {
		return userDao.selectCodeById(id);
	}


	@Override
	public User selectUserById(String userId) throws Exception {
		Integer userCode = this.selectCodeById(userId);
		return this.selectUserByCode(userCode);
	}


	@Override
	public void updateUserInfo(User user) throws Exception {
		userDao.updateUserInfo(user);
		RedisUtil.setUserByCode(user);
	}


	@Override
	public void updatePwdByPhone(User user) throws Exception {
		userDao.updatePwdByPhone(user);
	}


	@Override
	public User selectUserByCode(Integer code) throws Exception {
		User user = RedisUtil.getUserByCode(code);
		if (user != null) {
			return user;
		}else{
			user = userDao.selectUserByCode(code);
			RedisUtil.setUserByCode(user);
		}
//		User user = this.userDao.selectUserByCode(code);
		return user;
	}


	@Override
	public void updateIcon(User user) throws Exception {
		userDao.updateIcon(user);
	}


	@Override
	public List<Map<String, Object>> selectBiu(Map<String, Object> map)
			throws Exception {
		return userDao.selectBiu(map);
	}


	@Override
	public void updateLocation(User user) throws Exception {
		userDao.updateLocation(user);
	}


	@Override
	public void addVC(User user) throws Exception {
		userDao.addVC(user);
	}
	//判断用户biu币是否充足，比发biu消耗的biu币多返回true，否则返回false
	public boolean isEnough(String userid, Integer vc) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", userid);
		map.put("vc", vc);
		String ret = userDao.isEnough(map);
		if(ret.equals("1")){
			return true;
		}else{
			return false;
		}
	}


	@Override
	public String selectIdByCode(Integer code) throws Exception {
		return userDao.selectIdByCode(code);
	}


	@Override
	public Integer selectVC(String id) {
		// TODO Auto-generated method stub
		return userDao.selectVC(id);
	}


	@Override
	public Integer selectCodeByPhone(String phone) {
		// TODO Auto-generated method stub
		return userDao.selectCodeByPhone(phone);
	}


	@Override
	public List<Map<String, Object>> selectBiuTest(String id) {
		// TODO Auto-generated method stub
		return userDao.selectBiuTest(id);
	}


	@Override
	public String selectSexById(String userid) {
		// TODO Auto-generated method stub
		return userDao.selectSexById(userid);
	}


	@Override
	public String selectAppStatus(String userid) {
		// TODO Auto-generated method stub
		return userDao.selectAppStatus(userid);
	}


	@Override
	public List<Map<String, Object>> paginate(int start, int pageSize) {
		// TODO Auto-generated method stub
		return userDao.paginate(start, pageSize);
	}


	@Override
	public List<Map<String, Object>> selectTargetBiuList(Map<String, Object> map) {
		
		return userDao.selectTargetBiuList(map);
	}

}
