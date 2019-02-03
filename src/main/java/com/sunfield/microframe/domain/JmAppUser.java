package com.sunfield.microframe.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

import com.sunfield.microframe.domain.base.BaseDomain;

/**
 * jm_app_user bean
 * @author sunfield coder
 */
@ApiModel(value="JmAppUser", description="")
public class JmAppUser extends BaseDomain{

	@ApiModelProperty(value="手机号", dataType="String")
	private String mobile;
	
	@ApiModelProperty(value="昵称", dataType="String")
	private String nickName;
	
	@ApiModelProperty(value="签名", dataType="String")
	private String sign;
	
	@ApiModelProperty(value="公司名称", dataType="String")
	private String companyName;
	
	@ApiModelProperty(value="行业", dataType="String")
	private String industry;
	
	@ApiModelProperty(value="职务", dataType="String")
	private String post;
	
	@ApiModelProperty(value="性别(1:男;2:女)", dataType="Integer")
	private Integer gender;
	
	@ApiModelProperty(value="头像url", dataType="String")
	private String headPicUrl;
	
	@ApiModelProperty(value="名片url", dataType="String")
	private String cardPicUrl;
	
	@ApiModelProperty(value="名片认证状态（0：未认证；1：认证中；2：已认证；3：已驳回）", dataType="Integer")
	private Integer cardStatus;
	
	@ApiModelProperty(value="微信openid", dataType="String")
	private String wxOpenId;
	
	@ApiModelProperty(value="qq openid", dataType="String")
	private String qqOpenId;
	
	@ApiModelProperty(value="新浪微博openid", dataType="String")
	private String sinaOpenId;
	
	@ApiModelProperty(value="专家认证状态（0：未认证；1：认证中；2：已认证；3：已驳回）", dataType="Integer")
	private Integer expertStatus;
	
	
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
	
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}
	
	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}
	
	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}
	
	public String getHeadPicUrl() {
		return headPicUrl;
	}

	public void setHeadPicUrl(String headPicUrl) {
		this.headPicUrl = headPicUrl;
	}
	
	public String getCardPicUrl() {
		return cardPicUrl;
	}

	public void setCardPicUrl(String cardPicUrl) {
		this.cardPicUrl = cardPicUrl;
	}
	
	public Integer getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(Integer cardStatus) {
		this.cardStatus = cardStatus;
	}
	
	public String getWxOpenId() {
		return wxOpenId;
	}

	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}
	
	public String getQqOpenId() {
		return qqOpenId;
	}

	public void setQqOpenId(String qqOpenId) {
		this.qqOpenId = qqOpenId;
	}
	
	public String getSinaOpenId() {
		return sinaOpenId;
	}

	public void setSinaOpenId(String sinaOpenId) {
		this.sinaOpenId = sinaOpenId;
	}
	
	public Integer getExpertStatus() {
		return expertStatus;
	}

	public void setExpertStatus(Integer expertStatus) {
		this.expertStatus = expertStatus;
	}
	
}