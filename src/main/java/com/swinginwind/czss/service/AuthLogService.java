package com.swinginwind.czss.service;

import java.util.List;

import com.swinginwind.czss.dto.AuthLogPager;
import com.swinginwind.czss.entity.AuthLog;

public interface AuthLogService {
	
	public List<AuthLog> queryLogs(AuthLogPager pager);

}
