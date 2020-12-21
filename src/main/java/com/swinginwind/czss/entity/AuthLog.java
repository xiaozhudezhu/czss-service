package com.swinginwind.czss.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("身份认证测试日志")
public class AuthLog {
    /**
     * 
     * 表 : auth_log
     * 对应字段 : id
     */
	@ApiModelProperty("id")
    private Integer id;

    /**
     * 
     * 表 : auth_log
     * 对应字段 : type
     */
	@ApiModelProperty("类型，Manual/Batch")
    private String type;

    /**
     * 
     * 表 : auth_log
     * 对应字段 : run_times
     */
	@ApiModelProperty("运行次数")
    private Integer runTimes;

    /**
     * 
     * 表 : auth_log
     * 对应字段 : auth_time
     */
	@ApiModelProperty("平均认证时间(微妙)")
    private BigDecimal authTime;

    /**
     * 
     * 表 : auth_log
     * 对应字段 : encrypt_time
     */
	@ApiModelProperty("平均加密时间(微妙)")
    private BigDecimal encryptTime;

    /**
     * 
     * 表 : auth_log
     * 对应字段 : compare_time
     */
	@ApiModelProperty("平均比较时间(微妙)")
    private BigDecimal compareTime;

    /**
     * 
     * 表 : auth_log
     * 对应字段 : result
     */
	@ApiModelProperty("认证结果，true：成功，false：失败")
    private BigDecimal result;

    /**
     * 
     * 表 : auth_log
     * 对应字段 : detail
     */
	@ApiModelProperty("")
    private String detail;

    /**
     * 
     * 表 : auth_log
     * 对应字段 : progress_status
     */
	@ApiModelProperty("运行状态，Running/Completed")
    private String progressStatus;

    /**
     * 
     * 表 : auth_log
     * 对应字段 : progress_percent
     */
	@ApiModelProperty("运行进度")
    private String progressPercent;

    /**
     * 
     * 表 : auth_log
     * 对应字段 : create_time
     */
	@ApiModelProperty("创建时间")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;

    /**
     * 
     * 表 : auth_log
     * 对应字段 : remark
     */
	@ApiModelProperty("备注")
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getRunTimes() {
        return runTimes;
    }

    public void setRunTimes(Integer runTimes) {
        this.runTimes = runTimes;
    }

    public BigDecimal getAuthTime() {
        return authTime;
    }

    public void setAuthTime(BigDecimal authTime) {
        this.authTime = authTime;
    }

    public BigDecimal getEncryptTime() {
        return encryptTime;
    }

    public void setEncryptTime(BigDecimal encryptTime) {
        this.encryptTime = encryptTime;
    }

    public BigDecimal getCompareTime() {
        return compareTime;
    }

    public void setCompareTime(BigDecimal compareTime) {
        this.compareTime = compareTime;
    }

    public BigDecimal getResult() {
        return result;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public String getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(String progressStatus) {
        this.progressStatus = progressStatus == null ? null : progressStatus.trim();
    }

    public String getProgressPercent() {
        return progressPercent;
    }

    public void setProgressPercent(String progressPercent) {
        this.progressPercent = progressPercent == null ? null : progressPercent.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}