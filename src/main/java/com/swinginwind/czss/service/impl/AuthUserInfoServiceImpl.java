package com.swinginwind.czss.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	private boolean isRunningCreateTestUsers = false;
	
	@Value("${czss.logEnabled:true}")
	private boolean logEnabled;
	
	@Override
	public int createTestUsers(int num) {
		setRunningCreateTestUsers(true);
		try {
			userMapper.deleteAll();
			AuthLog log = new AuthLog();
			log.setCreateTime(new Date());
			log.setRunTimes(num);
			log.setType("CreateUsers");
			log.setProgressPercent("0/" + num);
			log.setProgressStatus("Running");
			logMapper.insert(log);
			List<AuthUserInfo> list = new ArrayList<AuthUserInfo>();
			for(int i = 0; i < num; i ++) {
				
				AuthUserInfo userInfo = new AuthUserInfo();
				String testMemberId = "test_member_id_" + (i + 1);
				userInfo.setId(MD5Util.encrypt(testMemberId));
				String testName = "test_name_" + (i + 1);
				String testPhone = "1" + StringUtils.leftPad(String.valueOf((i + 1)), 10, '0');
				userInfo.setName(czssLib.encryptString(testName));
				userInfo.setMobile(czssLib.encryptString(testPhone));
				userInfo.setIdPlain(testMemberId);
				userInfo.setNamePlain(testName);
				userInfo.setMobilePlain(testPhone);
				userInfo.setRowNum(i);
				list.add(userInfo);
				if((i + 1) % 100 == 0) {
					if(list.size() > 0) {
						userMapper.insertBatch(list);
					}
					list = new ArrayList<AuthUserInfo>();
					AuthLog logTmp = new AuthLog();
					logTmp.setId(log.getId());
					logTmp.setProgressPercent((i + 1) + "/" + num);
					logMapper.updateByPrimaryKeySelective(logTmp);
				}
				if((i == num - 1)) {
					if(list.size() > 0) {
						userMapper.insertBatch(list);
					}
					AuthLog logTmp = new AuthLog();
					logTmp.setId(log.getId());
					logTmp.setProgressPercent(num + "/" + num);
					logTmp.setProgressStatus("Completed");
					logMapper.updateByPrimaryKeySelective(logTmp);
				}
			}
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			setRunningCreateTestUsers(false);
		}
		return num;
	}

	@Override
	public AuthUserInfo queryByMemberId(String memberId) {
		String encrypt = MD5Util.encrypt(memberId);
		AuthUserInfo info = userMapper.selectByPrimaryKey(encrypt);
		return info;
	}
	
	
	@Override
	public Map<String, Object> verifyUserInfo(String memberId, String name, String mobile) {
		long startTimeAuth = System.nanoTime();
		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = false;
		AuthUserInfo userInfo = queryByMemberId(memberId);
		AuthLog log = new AuthLog();
		log.setCreateTime(new Date());
		log.setRunTimes(1);
		log.setType("Normal");
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
		if(logEnabled)
			logMapper.insert(log);
		map.put("result", result);
		map.put("detail", log);
		return map;
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
				//随机修改一位
				if(i % 3 == 0) {
					StringBuilder strBuilder = new StringBuilder(name);
					strBuilder.setCharAt(r.nextInt(name.length() - 1), 'x');
					name = strBuilder.toString();
				}
				String mobile = testUser.getMobilePlain();
				//随机修改一位
				if(i % 6 == 0) {
					StringBuilder strBuilder = new StringBuilder(mobile);
					strBuilder.setCharAt(r.nextInt(mobile.length() - 1), 'x');
					mobile = strBuilder.toString();
				}
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
				
				if(((i + 1) % 100 == 0)) {
					AuthLog logTmp = new AuthLog();
					logTmp.setId(log.getId());
					logTmp.setProgressPercent((i + 1) + "/" + runTimes);
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

	/**
	 * @return the isRunningCreateTestUsers
	 */
	@Override
	public boolean isRunningCreateTestUsers() {
		return isRunningCreateTestUsers;
	}

	/**
	 * @param isRunningCreateTestUsers the isRunningCreateTestUsers to set
	 */
	public void setRunningCreateTestUsers(boolean isRunningCreateTestUsers) {
		this.isRunningCreateTestUsers = isRunningCreateTestUsers;
	}
}
