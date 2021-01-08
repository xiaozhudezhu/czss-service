package com.swinginwind.czss.entity;

import io.swagger.annotations.ApiModelProperty;

public class AuthUserInfo {
    /**
     * 
     * 表 : auth_user_info
     * 对应字段 : id
     */
	@ApiModelProperty("id（Hash后）")
    private String id;

    /**
     * 
     * 表 : auth_user_info
     * 对应字段 : name
     */
	@ApiModelProperty("用户名（加密后）")
    private String name;

    /**
     * 
     * 表 : auth_user_info
     * 对应字段 : mobile
     */
	@ApiModelProperty("手机号（加密后）")
    private String mobile;
	
	/**
     * 
     * 表 : auth_user_info
     * 对应字段 : id_num
     */
	@ApiModelProperty("身份证号（加密后）")
    private String idNum;

    /**
     * 
     * 表 : auth_user_info
     * 对应字段 : id_plain
     */
	@ApiModelProperty("id明文")
    private String idPlain;

    /**
     * 
     * 表 : auth_user_info
     * 对应字段 : name_plain
     */
	@ApiModelProperty("用户名（明文）")
    private String namePlain;

    /**
     * 
     * 表 : auth_user_info
     * 对应字段 : mobile_plain
     */
	@ApiModelProperty("手机号（明文）")
    private String mobilePlain;
	
	/**
     * 
     * 表 : auth_user_info
     * 对应字段 : id_num_plain
     */
	@ApiModelProperty("身份证号（明文）")
    private String idNumPlain;

    /**
     * 
     * 表 : auth_user_info
     * 对应字段 : row_num
     */
	@ApiModelProperty("行号（用于随机批量测试）")
    private Integer rowNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getIdPlain() {
        return idPlain;
    }

    public void setIdPlain(String idPlain) {
        this.idPlain = idPlain == null ? null : idPlain.trim();
    }

    public String getNamePlain() {
        return namePlain;
    }

    public void setNamePlain(String namePlain) {
        this.namePlain = namePlain == null ? null : namePlain.trim();
    }

    public String getMobilePlain() {
        return mobilePlain;
    }

    public void setMobilePlain(String mobilePlain) {
        this.mobilePlain = mobilePlain == null ? null : mobilePlain.trim();
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	public String getIdNumPlain() {
		return idNumPlain;
	}

	public void setIdNumPlain(String idNumPlain) {
		this.idNumPlain = idNumPlain;
	}
}