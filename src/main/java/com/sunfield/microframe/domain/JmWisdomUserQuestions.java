package com.sunfield.microframe.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

import com.sunfield.microframe.domain.base.BaseDomain;

/**
 * jm_wisdom_user_questions bean
 * @author sunfield coder
 */
@ApiModel(value="JmWisdomUserQuestions", description="")
public class JmWisdomUserQuestions extends BaseDomain{

	@ApiModelProperty(value="问答类型，1代表问题，2代表回答，类型不单设表", dataType="Integer")
	private Integer type;
	
	@ApiModelProperty(value="赞/踩用户id，关联用户表id", dataType="String")
	private String userId;
	
	@ApiModelProperty(value="问题/回答ID，依类型不同，关联问题表或问题回答表ID", dataType="String")
	private String questionId;
	
	@ApiModelProperty(value="赞/踩，默认0不赞不踩，赞为1，踩为2", dataType="Integer")
	private Integer yesorno;
	
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	
	public Integer getYesorno() {
		return yesorno;
	}

	public void setYesorno(Integer yesorno) {
		this.yesorno = yesorno;
	}
	
}