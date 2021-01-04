package com.swinginwind.czss.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swinginwind.czss.entity.EncryptContentWithBLOBs;
import com.swinginwind.czss.entity.EncryptLog;
import com.swinginwind.czss.entity.EncryptUserInfo;
import com.swinginwind.czss.lib.CzssLib;
import com.swinginwind.czss.mapper.EncryptContentMapper;
import com.swinginwind.czss.mapper.EncryptLogMapper;
import com.swinginwind.czss.mapper.EncryptUserInfoMapper;
import com.swinginwind.czss.service.EncryptService;

@Service
public class EncryptServiceImpl implements EncryptService {

	@Autowired
	private EncryptLogMapper logMapper;

	@Autowired
	private EncryptContentMapper contentMapper;
	
	@Autowired
	private EncryptUserInfoMapper userInfoMapper;

	@Autowired
	private Map<Integer, List<EncryptUserInfo>> userInfoMap;

	@Autowired
	private CzssLib czssLibEncrypt;

	@Override
	public EncryptContentWithBLOBs encrypt(Integer userId, String plainContent) {
		List<EncryptUserInfo> userList = userInfoMap.get(userId);
		EncryptLog log = new EncryptLog();
		long startTime = System.nanoTime();
		String encryptContent = czssLibEncrypt.encryptString3T(userList.get(0).getId().toString(),
				userList.get(1).getId().toString(), userList.get(2).getId().toString(), plainContent);
		log.setExecuteTime(new BigDecimal(System.nanoTime() - startTime).divideToIntegralValue(new BigDecimal(1000)));
		log.setCreateTime(new Date());
		log.setProgressPercent("1/1");
		log.setProgressStatus("Completed");
		log.setRunTimes(1);
		log.setType("Encrypt");
		log.setResult(new BigDecimal(100));
		ObjectMapper om = new ObjectMapper();
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("inputUserId", userId);
		json.put("inputPlainContent", plainContent);
		json.put("result", encryptContent);
		String detail = null;
		try {
			detail = om.writeValueAsString(json);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.setDetail(detail);
		logMapper.insert(log);
		EncryptContentWithBLOBs content = new EncryptContentWithBLOBs();
		content.setContent(encryptContent);
		content.setContentPlain(plainContent);
		content.setCreateUser(userId);
		content.setCreateTime(new Date());
		contentMapper.insert(content);
		return content;
	}

	@Override
	public String decrypt(Integer encryptUserId, Integer decryptUserId, String encryptContent) {
		List<EncryptUserInfo> userList = userInfoMap.get(encryptUserId);
		if(userList == null) {
			return null;
		}
		List<String> tArray = new ArrayList<String>();
		for (EncryptUserInfo user : userList) {
			if (user.getId() != decryptUserId)
				tArray.add("T" + user.getId() + decryptUserId);
			else
				tArray.add(decryptUserId.toString());
		}
		EncryptLog log = new EncryptLog();
		long startTime = System.nanoTime();
		String decryptContent = czssLibEncrypt.decryptString3T(tArray.get(0), tArray.get(1),
				tArray.get(2), encryptContent);
		log.setExecuteTime(new BigDecimal(System.nanoTime() - startTime).divideToIntegralValue(new BigDecimal(1000)));
		log.setCreateTime(new Date());
		log.setProgressPercent("1/1");
		log.setProgressStatus("Completed");
		log.setRunTimes(1);
		log.setType("Decrypt");
		log.setResult(new BigDecimal(100));
		ObjectMapper om = new ObjectMapper();
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("inputEncryptUserId", encryptUserId);
		json.put("inputDecryptUserId", decryptUserId);
		json.put("inputEncryptContent", encryptContent);
		json.put("result", decryptContent);
		String detail = null;
		try {
			detail = om.writeValueAsString(json);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.setDetail(detail);
		logMapper.insert(log);
		return decryptContent;
	}

	@Override
	public void batchEncryptTest(int runTimes) {
		Object[] ids = userInfoMap.keySet().toArray();
		int size = ids.length;
		EncryptLog log = new EncryptLog();
		log.setCreateTime(new Date());
		log.setRunTimes(runTimes);
		log.setType("Batch");
		log.setProgressPercent("0/" + runTimes);
		log.setProgressStatus("Running");
		logMapper.insert(log);
		Random r = new Random();
		long timeTotal = 0;
		int correctCount = 0;
		for (int i = 0; i < runTimes;) {
			Integer userId = (Integer) ids[r.nextInt(size)];
			List<EncryptUserInfo> userList = userInfoMap.get(userId);
			String plainContent = RandomStringUtils.randomAlphanumeric(20);
			long startTime = System.nanoTime();
			String encryptContent = czssLibEncrypt.encryptString3T(userList.get(0).getId().toString(),
					userList.get(1).getId().toString(), userList.get(2).getId().toString(), plainContent);
			Integer userIdDecrypt = userList.get(r.nextInt(userList.size())).getId();
			List<String> tArray = new ArrayList<String>();
			for (EncryptUserInfo user : userList) {
				if (user.getId() != userIdDecrypt)
					tArray.add("T" + user.getId() + userIdDecrypt);
				else
					tArray.add(userIdDecrypt.toString());
			}
			String decryptContent = czssLibEncrypt.decryptString3T(tArray.get(0), tArray.get(1),
					tArray.get(2), encryptContent);
			boolean isCorrect = plainContent.equals(decryptContent);
			if (isCorrect)
				correctCount++;
			else {
				System.out.println("tArray:" + tArray);
				System.out.println("plainContent:" + plainContent);
				System.out.println("decryptContent:" + decryptContent);
			}
				
			timeTotal += (System.nanoTime() - startTime);

			if (((i + 1) % 100 == 0)) {
				EncryptLog logTmp = new EncryptLog();
				logTmp.setId(log.getId());
				logTmp.setProgressPercent((i + 1) + "/" + runTimes);
				logMapper.updateByPrimaryKeySelective(logTmp);
			}
			if (i == runTimes - 1) {
				EncryptLog logTmp = new EncryptLog();
				logTmp.setId(log.getId());
				logTmp.setProgressPercent(runTimes + "/" + runTimes);
				logTmp.setProgressStatus("Completed");
				logTmp.setExecuteTime(new BigDecimal(timeTotal / runTimes).divideToIntegralValue(new BigDecimal(1000)));
				logTmp.setResult(new BigDecimal(correctCount * 100 / runTimes));
				logMapper.updateByPrimaryKeySelective(logTmp);
			}
			i++;
		}
	}
	
	@Override
	public List<EncryptUserInfo> queryEncryptUsers() {
		List<EncryptUserInfo> list = new ArrayList<EncryptUserInfo>();
		for(Integer id : userInfoMap.keySet()) {
			List<EncryptUserInfo> list2 = userInfoMap.get(id);
			list.add(list2.get(list2.size() - 1));
		}
		return list;
		
	}
	
	@Override
	public List<EncryptUserInfo> queryDecryptUsers(Integer encryptUserId) {
		return userInfoMap.get(encryptUserId);
		
	}
	
	@Override
	public List<EncryptUserInfo> queryUsersAll() {
		return userInfoMapper.selectAll();
		
	}

}
