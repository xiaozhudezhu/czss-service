package com.swinginwind.czss.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.swinginwind.czss.entity.EncryptUserInfo;
import com.swinginwind.czss.mapper.EncryptUserInfoMapper;


@Configuration
public class EncyptUserInfoConfig {
		
	@Bean("encryptUserInfoMap")
	public Map<Integer, List<EncryptUserInfo>> getEncryptUserInfoMap(EncryptUserInfoMapper userInfoMapper) {
		Map<Integer, List<EncryptUserInfo>> map = new HashMap<Integer, List<EncryptUserInfo>>();
		List<EncryptUserInfo> userList = userInfoMapper.selectAll();
		Map<Integer, EncryptUserInfo> userMap = new HashMap<Integer, EncryptUserInfo>();
		List<Integer> hasChild = new ArrayList<Integer>();
		for(EncryptUserInfo user : userList) {
			userMap.put(user.getId(), user);
			if(!hasChild.contains(user.getPid()))
				hasChild.add(user.getPid());
		}
		for(EncryptUserInfo user : userList) {
			if(!hasChild.contains(user.getId())) {
				List<EncryptUserInfo> list = new ArrayList<EncryptUserInfo>();
				EncryptUserInfo userTmp = user;
				list.add(userTmp);
				while(userTmp.getPid() != 0) {
					userTmp = userMap.get(userTmp.getPid());
					list.add(userTmp);
				}
				Collections.reverse(list);
				map.put(user.getId(), list);
			}
		}
		
		return map;
	}
	

}
