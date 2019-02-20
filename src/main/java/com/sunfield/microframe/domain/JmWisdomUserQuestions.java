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

	@ApiModelProperty(value="问答类型，1代表问题，2代表回答，3代表访谈，4代表访谈视频，类型不单设表", dataType="Integer")
	private Integer type;
	
	@ApiModelProperty(value="赞/踩/访谈收藏/访谈视频点赞用户id，关联用户表id", dataType="String")
	private String userId;
	
	@ApiModelProperty(value="问题/回答/访谈/访谈视频ID，依类型不同，关联问题表或问题回答表或访谈表或访谈视频表ID", dataType="String")
	private String questionId;
	
	@ApiModelProperty(value="赞/踩/收藏，赞/收藏为1，踩为2，不赞不踩不收藏则不记录，取消赞/踩/收藏，则逻辑删除", dataType="Integer")
	private Integer yesorno = 0;
	
	
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