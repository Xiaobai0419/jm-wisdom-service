package com.sunfield.microframe.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

import com.sunfield.microframe.domain.base.BaseDomain;

/**
 * jm_wisdom_videos bean
 * @author sunfield coder
 */
@ApiModel(value="JmWisdomVideos", description="")
public class JmWisdomVideos extends BaseDomain{

	@ApiModelProperty(value="标题", dataType="String")
	private String title;
	
	@ApiModelProperty(value="访谈ID,关联角马访谈表ID", dataType="String")
	private String interviewId;
	
	@ApiModelProperty(value="封面地址", dataType="String")
	private String coverUrl;
	
	@ApiModelProperty(value="视频存储地址", dataType="String")
	private String videoUrl;
	
	@ApiModelProperty(value="是否允许评论和点赞，1允许，2不允许", dataType="Integer")
	private Integer allowComments;
	
	@ApiModelProperty(value="是否会员专属，1是，2否", dataType="Integer")
	private Integer leaguerOnly;
	
	@ApiModelProperty(value="免费时长，单位秒", dataType="Integer")
	private Integer freeDuration;
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getInterviewId() {
		return interviewId;
	}

	public void setInterviewId(String interviewId) {
		this.interviewId = interviewId;
	}
	
	public String getCoverUrl() {
		return coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}
	
	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	
	public Integer getAllowComments() {
		return allowComments;
	}

	public void setAllowComments(Integer allowComments) {
		this.allowComments = allowComments;
	}
	
	public Integer getLeaguerOnly() {
		return leaguerOnly;
	}

	public void setLeaguerOnly(Integer leaguerOnly) {
		this.leaguerOnly = leaguerOnly;
	}
	
	public Integer getFreeDuration() {
		return freeDuration;
	}

	public void setFreeDuration(Integer freeDuration) {
		this.freeDuration = freeDuration;
	}
	
}