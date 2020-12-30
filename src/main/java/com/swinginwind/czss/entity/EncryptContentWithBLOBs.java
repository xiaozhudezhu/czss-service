package com.swinginwind.czss.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("多级加密内容")
public class EncryptContentWithBLOBs extends EncryptContent {
    /**
     * 
     * 表 : encrypt_content
     * 对应字段 : content
     */
	@ApiModelProperty("内容（密文）")
    private String content;

    /**
     * 
     * 表 : encrypt_content
     * 对应字段 : content_plain
     */
	@ApiModelProperty("内容（明文）")
    private String contentPlain;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getContentPlain() {
        return contentPlain;
    }

    public void setContentPlain(String contentPlain) {
        this.contentPlain = contentPlain == null ? null : contentPlain.trim();
    }
}