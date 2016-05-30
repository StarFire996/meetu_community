package com.meetu.community.mapper;

import com.meetu.community.domain.SysSettings;


public interface SysSettingsMapper {

	public void insertOper(SysSettings settings);

	public SysSettings selectDataByKey(String parameter);

}
