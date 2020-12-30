package com.swinginwind.czss.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("多级加密账号")
public class EncryptUserInfo {
    /**
     * 
     * 表 : encrypt_user_info
     * 对应字段 : id
     */
	@ApiModelProperty("id")
    private Integer id;

    /**
     * 
     * 表 : encrypt_user_info
     * 对应字段 : name
     */
	@ApiModelProperty("名称")
    private String name;

    /**
     * 
     * 表 : encrypt_user_info
     * 对应字段 : pid
     */
	@ApiModelProperty("父ID")
    private Integer pid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
}