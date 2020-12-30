package com.swinginwind.czss.service;

import java.util.List;

import com.swinginwind.czss.entity.EncryptContentWithBLOBs;
import com.swinginwind.czss.entity.EncryptUserInfo;

public interface EncryptService {
	
	public EncryptContentWithBLOBs encrypt(Integer userId, String plainContent);
	
	public String decrypt(Integer encryptUserId, Integer decryptUserId, String encryptContent);

	void batchEncryptTest(int runTimes);

	List<EncryptUserInfo> queryEncryptUsers();

	List<EncryptUserInfo> queryDecryptUsers(Integer encryptUserId);

	List<EncryptUserInfo> queryUsersAll();

}
