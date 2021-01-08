package com.swinginwind.czss.service;

import java.util.List;
import java.util.Map;

import com.swinginwind.czss.dto.AuthUserInfoPager;
import com.swinginwind.czss.entity.AuthUserInfo;

public interface AuthUserInfoService {
	
	public int createTestUsers(int num);
	
	public AuthUserInfo queryByMemberId(String memberId);
	
	public boolean manualVerifyUserInfo(String memberId, String name, String mobile, String idNum);
	
	public void batchVerifyUserInfo(int runTimes);
	
	public List<AuthUserInfo> queryUserInfos(AuthUserInfoPager pager);

	boolean isRunningCreateTestUsers();

	Map<String, Object> verifyUserInfo(String memberId, String name, String mobile, String idNum);


}
