package com.meetu.community.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meetu.community.domain.Biu;
import com.meetu.community.mapper.BiuMapper;
import com.meetu.community.service.BiuService;

@Service
@Transactional
public class BiuServiceImpl implements BiuService{
	
	@Autowired
	private BiuMapper biuMapper;
	
	public List<Biu> selectBiuByUserCode(Integer userCodeMine) {
		return this.biuMapper.selectBiuByUserCode(userCodeMine);
	}

	public void insertBiu(Biu biu) {
		this.biuMapper.insertBiu(biu);
	}

	public void acceptBiu(Biu biu) {
		this.biuMapper.acceptBiu(biu);
	}

	public void setBiuRead(Integer userCodeMine) {
		this.biuMapper.setBiuRead(userCodeMine);
	}

	public Integer selectBiuUnRead(Integer userCodeMine) {
		return this.biuMapper.selectBiuUnRead(userCodeMine);
	}

}
