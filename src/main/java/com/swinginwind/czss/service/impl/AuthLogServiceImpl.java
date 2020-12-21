package com.swinginwind.czss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swinginwind.czss.dto.AuthLogPager;
import com.swinginwind.czss.entity.AuthLog;
import com.swinginwind.czss.mapper.AuthLogMapper;
import com.swinginwind.czss.service.AuthLogService;

@Service
public class AuthLogServiceImpl implements AuthLogService {
	
	@Autowired
	private AuthLogMapper logMapper;

	@Override
	public List<AuthLog> queryLogs(AuthLogPager pager) {
		return logMapper.select(pager);
	}

}
