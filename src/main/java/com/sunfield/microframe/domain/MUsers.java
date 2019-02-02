package com.sunfield.microframe.domain;

import com.sunfield.microframe.domain.base.BaseDomain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * m_users bean
 * @author sunfield coder
 */
@ApiModel(value="MUsers", description="")
public class MUsers extends BaseDomain{

	@ApiModelProperty(value="手机号", dataType="String")
	private String mobile;
	
	@ApiModelProperty(value="昵称", dataType="String")
	private String nickName;
	
	@ApiModelProperty(value="年龄", dataType="Integer")
	private Integer age;
	
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
}