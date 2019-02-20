package com.sunfield.microframe.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

import com.sunfield.microframe.domain.base.BaseDomain;

/**
 * jm_wisdom_interviews bean
 * @author sunfield coder
 */
@ApiModel(value="JmWisdomInterviews", description="")
public class JmWisdomInterviews extends BaseDomain{

	@ApiModelProperty(value="标题", dataType="String")
	private String title;
	
	@ApiModelProperty(value="访谈介绍", dataType="String")
	private String content;
	
	@ApiModelProperty(value="封面地址", dataType="String")
	private String coverUrl;
	
	@ApiModelProperty(value="推荐排序，从1开始，空代表不推荐", dataType="Integer")
	private Integer selectOrder;
	
	@ApiModelProperty(value="视频板块推荐排序，从1开始，空代表不推荐", dataType="Integer")
	private Integer videoSelectOrder;
	
	@ApiModelProperty(value="收藏人数,缓存", dataType="Integer")
	private Integer favorites = 0;

	@ApiModelProperty(value="访问用户ID,关联用户信息表ID", dataType="String")
	private String visitUserId;

	@ApiModelProperty(value="访问者对该访谈的收藏状态", dataType="Integer")
	private Integer visitUserYesOrNo = 0;

	public String getVisitUserId() {
		return visitUserId;
	}

	public void setVisitUserId(String visitUserId) {
		this.visitUserId = visitUserId;
	}

	public Integer getVisitUserYesOrNo() {
		return visitUserYesOrNo;
	}

	public void setVisitUserYesOrNo(Integer visitUserYesOrNo) {
		this.visitUserYesOrNo = visitUserYesOrNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getCoverUrl() {
		return coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}
	
	public Integer getSelectOrder() {
		return selectOrder;
	}

	public void setSelectOrder(Integer selectOrder) {
		this.selectOrder = selectOrder;
	}
	
	public Integer getVideoSelectOrder() {
		return videoSelectOrder;
	}

	public void setVideoSelectOrder(Integer videoSelectOrder) {
		this.videoSelectOrder = videoSelectOrder;
	}
	
	public Integer getFavorites() {
		return favorites;
	}

	public void setFavorites(Integer favorites) {
		this.favorites = favorites;
	}
	
}