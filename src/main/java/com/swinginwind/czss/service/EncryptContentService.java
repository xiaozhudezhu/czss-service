package com.swinginwind.czss.service;

import java.util.List;

import com.swinginwind.czss.dto.EncryptContentPager;
import com.swinginwind.czss.entity.EncryptContentWithBLOBs;

public interface EncryptContentService {
	
	public List<EncryptContentWithBLOBs> queryContents(EncryptContentPager pager);

}
