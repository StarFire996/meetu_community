package com.meetu.community.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meetu.community.domain.SysSettings;
import com.meetu.community.mapper.SysSettingsMapper;
import com.meetu.community.service.SysSettingsService;

@Service
public class SysSettingsServiceImpl implements SysSettingsService {

	@Autowired
	private SysSettingsMapper sysSettingsDao;
	
	@Override
	public void insertOper(SysSettings settings) throws Exception {
		sysSettingsDao.insertOper(settings);
	}

	@Override
	public SysSettings selectDataByKey(String key) throws Exception {
		return sysSettingsDao.selectDataByKey(key);
	}

}
