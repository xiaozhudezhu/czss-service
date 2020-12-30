package com.swinginwind.czss.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("多级加密加密内容")
public class EncryptContent {
    /**
     * 
     * 表 : encrypt_content
     * 对应字段 : id
     */
	@ApiModelProperty("id")
    private Integer id;

    /**
     * 
     * 表 : encrypt_content
     * 对应字段 : create_user
     */
	@ApiModelProperty("创建用户")
    private Integer createUser;

    /**
     * 
     * 表 : encrypt_content
     * 对应字段 : create_time
     */
	@ApiModelProperty("创建时间")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}