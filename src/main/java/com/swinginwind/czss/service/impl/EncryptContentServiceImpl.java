package com.swinginwind.czss.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swinginwind.czss.dto.EncryptContentPager;
import com.swinginwind.czss.entity.EncryptContentWithBLOBs;
import com.swinginwind.czss.mapper.EncryptContentMapper;
import com.swinginwind.czss.service.EncryptContentService;

@Service
public class EncryptContentServiceImpl implements EncryptContentService {
	
	@Autowired
	private EncryptContentMapper contentMapper;

	@Override
	public List<EncryptContentWithBLOBs> queryContents(EncryptContentPager pager) {
		return contentMapper.select(pager);
	}

}
