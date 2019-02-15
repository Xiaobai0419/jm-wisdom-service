package com.sunfield.microframe.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

import com.sunfield.microframe.domain.base.BaseDomain;

/**
 * jm_wisdom_webcast bean
 * @author sunfield coder
 */
@ApiModel(value="JmWisdomWebcast", description="")
public class JmWisdomWebcast extends BaseDomain{

	@ApiModelProperty(value="标题", dataType="String")
	private String title;
	
	@ApiModelProperty(value="封面地址", dataType="String")
	private String coverUrl;
	
	@ApiModelProperty(value="直播链接", dataType="String")
	private String webcastLink;
	
	@ApiModelProperty(value="直播开始时间", dataType="Date")
	private Date beginTime;
	
	@ApiModelProperty(value="直播结束时间", dataType="Date")
	private Date endTime;

	@ApiModelProperty(value="直播启禁用状态", dataType="String")
	private String status1;

	public String getStatus1() {
		return status1;
	}

	public void setStatus1(String status1) {
		this.status1 = status1;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getCoverUrl() {
		return coverUrl;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}
	
	public String getWebcastLink() {
		return webcastLink;
	}

	public void setWebcastLink(String webcastLink) {
		this.webcastLink = webcastLink;
	}
	
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}