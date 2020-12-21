package com.swinginwind.czss.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swinginwind.core.utils.MD5Util;
import com.swinginwind.czss.dto.AuthUserInfoPager;
import com.swinginwind.czss.entity.AuthLog;
import com.swinginwind.czss.entity.AuthUserInfo;
import com.swinginwind.czss.lib.CzssLib;
import com.swinginwind.czss.mapper.AuthLogMapper;
import com.swinginwind.czss.mapper.AuthUserInfoMapper;
import com.swinginwind.czss.service.AuthUserInfoService;

@Service
public class AuthUserInfoServiceImpl implements AuthUserInfoService {

	@Autowired
	private AuthLogMapper logMapper;
	
	@Autowired
	private AuthUserInfoMapper userMapper;
	
	@Autowired
	private CzssLib czssLib;
	
	@Override
	public int createTestUsers(int num) {
		userMapper.deleteAll();
		for(int i = 0; i < num; i ++) {
			AuthUserInfo userInfo = new AuthUserInfo();
			String testMemberId = "test_member_id_" + (i + 1);
			userInfo.setId(MD5Util.encrypt(testMemberId));
			String testName = "test_name_" + (i + 1);
			String testPhone = "123456" + StringUtils.leftPad(String.valueOf((i + 1)), 5, '0');
			userInfo.setName(czssLib.encryptString(testName));
			userInfo.setMobile(czssLib.encryptString(testPhone));
			userInfo.setIdPlain(testMemberId);
			userInfo.setNamePlain(testName);
			userInfo.setMobilePlain(testPhone);
			userInfo.setRowNum(i);
			userMapper.insert(userInfo);
		}
		return num;
	}

	@Override
	public AuthUserInfo queryByMemberId(String memberId) {
		return userMapper.selectByPrimaryKey(MD5Util.encrypt(memberId));
	}

	@Override
	public boolean manualVerifyUserInfo(String memberId, String name, String mobile) {
		long startTimeAuth = System.nanoTime();
		boolean result = false;
		AuthUserInfo userInfo = queryByMemberId(memberId);
		AuthLog log = new AuthLog();
		log.setCreateTime(new Date());
		log.setRunTimes(1);
		log.setType("Manual");
		//log.setType("Batch");
		if(userInfo != null) {
			long startTimeEncrypt = System.nanoTime();
			String encryptName = czssLib.encryptString(name);
			String encryptMobile = czssLib.encryptString(mobile);
			log.setEncryptTime(new BigDecimal(System.nanoTime() - startTimeEncrypt).divideToIntegralValue(new BigDecimal(1000)));
			long startTimeCompare = System.nanoTime();
			boolean encryptNameSame = czssLib.isSame(encryptName, userInfo.getName());
			boolean encryptMobileSame = czssLib.isSame(encryptMobile, userInfo.getMobile());
			boolean encryptIsSame = encryptNameSame && encryptMobileSame;
			log.setCompareTime(new BigDecimal(System.nanoTime() - startTimeCompare).divideToIntegralValue(new BigDecimal(1000)));
			String decryptName = userInfo.getNamePlain();//czssLib.decryptString(userInfo.getName());
			String decryptMobile = userInfo.getMobilePlain();//czssLib.decryptString(userInfo.getMobile());
			boolean decryptNameSame = decryptName.equals(name);
			boolean decryptMobileSame = decryptMobile.equals(mobile);
			boolean isCorrect = (encryptNameSame == decryptNameSame) && (encryptMobileSame == decryptMobileSame);
			log.setProgressPercent("1/1");
			if(isCorrect)
				log.setResult(new BigDecimal(100));
			else
				log.setResult(new BigDecimal(0));
			if(encryptIsSame)
				result = true;
			else
				result = false;
		}
		else
			log.setRemark("User Not Exists");
		log.setDetail(String.valueOf(result));
		log.setProgressStatus("Completed");
		log.setAuthTime(new BigDecimal(System.nanoTime() - startTimeAuth).divideToIntegralValue(new BigDecimal(1000)));
		logMapper.insert(log);
		return result;
	}

	@Override
	public void batchVerifyUserInfo(int runTimes) {
		int maxRowNum = userMapper.selectMaxRowNum();
		AuthLog log = new AuthLog();
		log.setCreateTime(new Date());
		log.setRunTimes(runTimes);
		log.setType("Batch");
		log.setProgressPercent("0/" + runTimes);
		log.setProgressStatus("Running");
		logMapper.insert(log);
		Random r = new Random();
		long authTimeTotal = 0, encryptTimeTotal = 0, compareTimeTotal = 0;
		int correctCount = 0;
		for(int i = 0; i < runTimes;) {
			int rowNum = r.nextInt(maxRowNum);
			AuthUserInfo testUser = userMapper.selectByRowNum(rowNum);
			if(testUser != null) {
				String memberId = testUser.getIdPlain();
				String name = testUser.getNamePlain();
				String mobile = testUser.getMobilePlain();
				long startTimeAuth = System.nanoTime();
				AuthUserInfo userInfo = queryByMemberId(memberId);
				long startTimeEncrypt = System.nanoTime();
				String encryptName = czssLib.encryptString(name);
				String encryptMobile = czssLib.encryptString(mobile);
				encryptTimeTotal += (System.nanoTime() - startTimeEncrypt);
				long startTimeCompare = System.nanoTime();
				boolean encryptNameSame = czssLib.isSame(encryptName, userInfo.getName());
				boolean encryptMobileSame = czssLib.isSame(encryptMobile, userInfo.getMobile());
				//boolean encryptIsSame = encryptNameSame && encryptMobileSame;
				compareTimeTotal += (System.nanoTime() - startTimeCompare);
				String decryptName = userInfo.getNamePlain();//czssLib.decryptString(userInfo.getName());
				String decryptMobile = userInfo.getMobilePlain();//czssLib.decryptString(userInfo.getMobile());
				boolean decryptNameSame = decryptName.equals(name);
				boolean decryptMobileSame = decryptMobile.equals(mobile);
				boolean isCorrect = (encryptNameSame == decryptNameSame) && (encryptMobileSame == decryptMobileSame);
				if(isCorrect)
					correctCount ++;
				System.out.println(correctCount);
				authTimeTotal += (System.nanoTime() - startTimeAuth);
				
				if((i != 0 && i % 100 == 0)) {
					AuthLog logTmp = new AuthLog();
					logTmp.setId(log.getId());
					logTmp.setProgressPercent(i + "/" + runTimes);
					logMapper.updateByPrimaryKeySelective(logTmp);
				}
				if(i == runTimes - 1) {
					AuthLog logTmp = new AuthLog();
					logTmp.setId(log.getId());
					logTmp.setProgressPercent(runTimes + "/" + runTimes);
					logTmp.setProgressStatus("Completed");
					logTmp.setAuthTime(new BigDecimal(authTimeTotal/runTimes).divideToIntegralValue(new BigDecimal(1000)));
					logTmp.setEncryptTime(new BigDecimal(encryptTimeTotal/runTimes).divideToIntegralValue(new BigDecimal(1000)));
					logTmp.setCompareTime(new BigDecimal(compareTimeTotal/runTimes).divideToIntegralValue(new BigDecimal(1000)));
					logTmp.setResult(new BigDecimal(correctCount * 100/runTimes));
					logMapper.updateByPrimaryKeySelective(logTmp);
				}
				i ++;
			}
		}
	}

	@Override
	public List<AuthUserInfo> queryUserInfos(AuthUserInfoPager pager) {
		return userMapper.select(pager);
	}
}
