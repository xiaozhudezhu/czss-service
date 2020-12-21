package com.swinginwind.czss.dto;

import com.swinginwind.core.dto.Page;
import com.swinginwind.czss.entity.AuthUserInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("账号分页信息")
public class AuthUserInfoPager extends Page<AuthUserInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("用户名（模糊匹配）")
	private String name;
	
	@ApiModelProperty("手机号（模糊匹配）")
	private String mobile;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
