package com.sunfield.microframe.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

import com.sunfield.microframe.domain.base.BaseDomain;

/**
 * jm_wisdom_answers bean
 * @author sunfield coder
 */
@ApiModel(value="JmWisdomAnswers", description="")
public class JmWisdomAnswers extends BaseDomain{

	@ApiModelProperty(value="标题", dataType="String")
	private String title;
	
	@ApiModelProperty(value="问题ID,关联角马问题表ID", dataType="String")
	private String questionId;
	
	@ApiModelProperty(value="回答者ID,关联用户信息表ID", dataType="String")
	private String userId;
	
	@ApiModelProperty(value="内容", dataType="String")
	private String content;
	
	@ApiModelProperty(value="赞数,缓存", dataType="Integer")
	private Integer ayes;
	
	@ApiModelProperty(value="踩数,缓存", dataType="Integer")
	private Integer antis;
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public Integer getAyes() {
		return ayes;
	}

	public void setAyes(Integer ayes) {
		this.ayes = ayes;
	}
	
	public Integer getAntis() {
		return antis;
	}

	public void setAntis(Integer antis) {
		this.antis = antis;
	}
	
}