package com.swinginwind.czss.service;

import java.util.List;

import com.swinginwind.czss.dto.EncryptLogPager;
import com.swinginwind.czss.entity.EncryptLog;

public interface EncryptLogService {
	
	public List<EncryptLog> queryLogs(EncryptLogPager pager);

}
