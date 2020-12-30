package com.swinginwind.czss.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.swinginwind.core.dto.Page;
import com.swinginwind.czss.entity.EncryptLog;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("分级加密日志分页信息")
public class EncryptLogPager extends Page<EncryptLog> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("类型，Encrypt/Decrypt/Batch")
	private String type;
	
	@ApiModelProperty("查询起始日期，格式yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
	private Date startTime;
	
	@ApiModelProperty("查询结束日期，格式yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
	private Date endTime;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
