package com.meetu.community.service;

import com.meetu.community.domain.SysSettings;

public interface SysSettingsService {
	public void insertOper(SysSettings settings) throws Exception;
	public SysSettings selectDataByKey(String parameter) throws Exception;
}
