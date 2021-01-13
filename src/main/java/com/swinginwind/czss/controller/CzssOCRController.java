package com.swinginwind.czss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swinginwind.core.dto.Result;
import com.swinginwind.czss.service.OCRService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/czss/api3")
@Api("南航加密测试API-加密手写识别")
public class CzssOCRController {

	@Autowired
	private OCRService ocrService;

	@PostMapping("/encryptImage")
	@ApiOperation("加密图像，返回加密后的图像矩阵")
	public Result<String> encryptImage(@ApiParam(value = "灰度图像", required = true) @RequestParam String image) {
		if (image == null)
			return Result.newFailure("image不能为空", null);
		String result = ocrService.encryptImage(image);
		return Result.newSuccess(result);
	}
	
	@PostMapping("/execute")
	@ApiOperation("识别图像")
	public Result<Integer> execute(@ApiParam(value = "灰度图像", required = true) @RequestParam String image) {
		if (image == null)
			return Result.newFailure("image不能为空", null);
		int result = ocrService.execute(image);
		return Result.newSuccess(result);
	}
}
