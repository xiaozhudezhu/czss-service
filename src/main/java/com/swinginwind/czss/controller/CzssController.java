package com.swinginwind.czss.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swinginwind.core.dto.Result;
import com.swinginwind.czss.dto.AuthLogPager;
import com.swinginwind.czss.dto.AuthUserInfoPager;
import com.swinginwind.czss.service.AuthLogService;
import com.swinginwind.czss.service.AuthUserInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/czss/api")
@Api("南航加密测试API-同态身份认证")
public class CzssController {

	@Autowired
	private AuthLogService logService;

	@Autowired
	private AuthUserInfoService userService;

	@GetMapping("/queryLogs")
	@ApiOperation("查询测试日志")
	public AuthLogPager queryLogs(@ApiParam("测试日志查询条件") AuthLogPager pager) {
		logService.queryLogs(pager);
		return pager;
	}

	@GetMapping("/queryUserInfos")
	@ApiOperation("查询测试账号")
	public AuthUserInfoPager queryUserInfos(@ApiParam("测试账号查询条件") AuthUserInfoPager pager) {
		userService.queryUserInfos(pager);
		return pager;
	}

	@PostMapping("/createTestUsers")
	@ApiOperation("创建测试账号（将会先清空测试账号）")
	public Result<Integer> createTestUsers(@ApiParam(value = "创建数量", defaultValue = "10000") @RequestParam Integer num) {
		if (num == null || num == 0)
			num = 10000;
		Integer count = userService.createTestUsers(num);
		return Result.newSuccess(count);
	}

	@PostMapping("/manualVerifyUserInfo")
	@ApiOperation("手动身份认证，返回是否认证成功")
	public Result<Boolean> manualVerifyUserInfo(@ApiParam(value = "账号明文", required = true) @RequestParam String memberId,
			@ApiParam(value = "用户名明文", required = true) @RequestParam String name,
			@ApiParam(value = "手机号明文", required = true) @RequestParam String mobile) {
		if (StringUtils.isEmpty(memberId))
			return Result.newFailure("memberId不能为空", null);
		if (StringUtils.isEmpty(name))
			return Result.newFailure("name不能为空", null);
		if (StringUtils.isEmpty(mobile))
			return Result.newFailure("mobile不能为空", null);
		Boolean result = userService.manualVerifyUserInfo(memberId, name, mobile);
		return Result.newSuccess(result);
	}

	@PostMapping("/batchVerifyUserInfo")
	@ApiOperation("批量身份认证测试，后台线程执行")
	public Result<String> batchVerifyUserInfo(@ApiParam(value = "批量运行次数", required = true) @RequestParam Integer n) {
		if (n == null || n <= 0) {
			return Result.newFailure("批量运行次数参数必须大于0", null);
		}
		if (n > 100000)
			return Result.newFailure("批量运行次数参数必须小于100000", null);
		new Thread() {
			public void run() {
				userService.batchVerifyUserInfo(n);
			}
		}.start();
		return Result.newSuccess("Batch Test Running");
	}

}
