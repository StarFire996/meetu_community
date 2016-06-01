package com.meetu.community.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meetu.community.domain.Report;
import com.meetu.community.mapper.ReportMapper;
import com.meetu.community.service.ReportService;

@Service
@Transactional
public class ReportServiceImpl implements ReportService{

	@Autowired
	private ReportMapper reportMapper;
	

	@Override
	public void insertReport(Report report) {
		
		this.reportMapper.insertReport(report);
	}

}
