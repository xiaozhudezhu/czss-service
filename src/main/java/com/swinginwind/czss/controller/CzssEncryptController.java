package com.swinginwind.czss.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swinginwind.core.dto.Result;
import com.swinginwind.czss.dto.EncryptContentPager;
import com.swinginwind.czss.dto.EncryptLogPager;
import com.swinginwind.czss.entity.EncryptContentWithBLOBs;
import com.swinginwind.czss.entity.EncryptUserInfo;
import com.swinginwind.czss.service.EncryptContentService;
import com.swinginwind.czss.service.EncryptLogService;
import com.swinginwind.czss.service.EncryptService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/czss/api2")
@Api("南航加密测试API-多级加密")
public class CzssEncryptController {

	@Autowired
	private EncryptLogService logService;

	@Autowired
	private EncryptService encryptService;
	
	@Autowired
	private EncryptContentService contentService;

	@GetMapping("/queryLogs")
	@ApiOperation("查询测试日志")
	public EncryptLogPager queryLogs(@ApiParam("测试日志查询条件") EncryptLogPager pager) {
		logService.queryLogs(pager);
		return pager;
	}

	@GetMapping("/queryEncryptUsers")
	@ApiOperation("查询加密账号")
	public List<EncryptUserInfo> queryEncryptUsers() {
		return encryptService.queryEncryptUsers();
	}
	
	@GetMapping("/queryDecryptUsers")
	@ApiOperation("查询解密账号")
	public List<EncryptUserInfo> queryDecryptUsers(@ApiParam(value = "加密账号ID", required = true) @RequestParam Integer encryptUserId) {
		return encryptService.queryDecryptUsers(encryptUserId);
	}
	
	@GetMapping("/queryUsersAll")
	@ApiOperation("查询所有账号")
	public List<EncryptUserInfo> queryUsers(@ApiParam(value = "加密账号ID", required = true) @RequestParam Integer encryptUserId) {
		return encryptService.queryUsersAll();
	}
	
	@GetMapping("/queryContents")
	@ApiOperation("查询加密内容")
	public EncryptContentPager queryContents(@ApiParam("加密内容查询条件") EncryptContentPager pager) {
		contentService.queryContents(pager);
		return pager;
	}
	
	@PostMapping("/encrypt")
	@ApiOperation("加密，返回加密信息")
	public Result<EncryptContentWithBLOBs> encrypt(@ApiParam(value = "加密账号ID", required = true) @RequestParam Integer userId,
			@ApiParam(value = "待加密明文", required = true) @RequestParam String plainContent) {
		if (userId == null)
			return Result.newFailure("userId不能为空", null);
		if (StringUtils.isEmpty(plainContent))
			return Result.newFailure("plainContent不能为空", null);
		EncryptContentWithBLOBs result = encryptService.encrypt(userId, plainContent);
		return Result.newSuccess(result);
	}
	
	@PostMapping("/decrypt")
	@ApiOperation("解密，返回明文信息")
	public Result<String> decrypt(@ApiParam(value = "解密账号ID", required = true) @RequestParam Integer decriptUserId,
			@ApiParam(value = "加密账号ID", required = true) @RequestParam Integer encriptUserId,
			@ApiParam(value = "密文", required = true) @RequestParam String encryptContent) {
		if (decriptUserId == null)
			return Result.newFailure("decriptUserId不能为空", null);
		if (encriptUserId == null)
			return Result.newFailure("encriptUserId不能为空", null);
		if (StringUtils.isEmpty(encryptContent))
			return Result.newFailure("plainContent不能为空", null);
		String result = encryptService.decrypt(encriptUserId, decriptUserId, encryptContent);
		if(!StringUtils.isEmpty(result))
			return Result.newSuccess(result);
		else
			return Result.newFailure("解密失败", null);
	}


	@PostMapping("/batchTest")
	@ApiOperation("批量测试，后台线程执行")
	public Result<String> batchTest(@ApiParam(value = "批量运行次数", required = true) @RequestParam Integer n) {
		if (n == null || n <= 0) {
			return Result.newFailure("批量运行次数参数必须大于0", null);
		}
		if (n > 100000)
			return Result.newFailure("批量运行次数参数必须小于100000", null);
		new Thread() {
			public void run() {
				encryptService.batchEncryptTest(n);
			}
		}.start();
		return Result.newSuccess("Batch Test Running");
	}

}
