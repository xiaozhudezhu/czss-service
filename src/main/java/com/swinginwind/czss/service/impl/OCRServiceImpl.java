package com.swinginwind.czss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swinginwind.czss.lib.CzssLib;
import com.swinginwind.czss.service.OCRService;

@Service
public class OCRServiceImpl implements OCRService {
	
	@Autowired
	private CzssLib czssLibOCR;

	@Override
	public int execute(String image) {
		return czssLibOCR.execute(image);
	}

	@Override
	public String encryptImage(String image) {
		return czssLibOCR.encryptImage(image);
	}

}
