package com.swinginwind.czss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swinginwind.czss.dto.EncryptLogPager;
import com.swinginwind.czss.entity.EncryptLog;
import com.swinginwind.czss.mapper.EncryptLogMapper;
import com.swinginwind.czss.service.EncryptLogService;

@Service
public class EncryptLogServiceImpl implements EncryptLogService {

	@Autowired
	private EncryptLogMapper logMapper;
	
	
	@Override
	public List<EncryptLog> queryLogs(EncryptLogPager pager) {
		return logMapper.select(pager);
	}

}
