package com.swinginwind.czss.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.swinginwind.core.dto.Page;
import com.swinginwind.czss.entity.AuthLog;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("身份认证日志分页信息")
public class EncryptContentPager extends Page<AuthLog> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("创建用户")
	private Integer createUser;
	
	@ApiModelProperty("内容（模糊匹配）")
	private String content;
	
	@ApiModelProperty("查询起始日期，格式yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
	private Date startTime;
	
	@ApiModelProperty("查询结束日期，格式yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
	private Date endTime;
	

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	

}
