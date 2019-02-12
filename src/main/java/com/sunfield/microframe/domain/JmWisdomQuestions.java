package com.sunfield.microframe.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

import com.sunfield.microframe.domain.base.BaseDomain;

/**
 * jm_wisdom_questions bean
 * @author sunfield coder
 */
@ApiModel(value="JmWisdomQuestions", description="")
public class JmWisdomQuestions extends BaseDomain{

	@ApiModelProperty(value="标题", dataType="String")
	private String title;
	
	@ApiModelProperty(value="行业分类ID,关联行业分类表ID", dataType="String")
	private String industryId;
	
	@ApiModelProperty(value="提问者ID,关联用户信息表ID", dataType="String")
	private String userId;
	
	@ApiModelProperty(value="问题详情", dataType="String")
	private String content;
	
	@ApiModelProperty(value="图片/视频存储地址,多个逗号分隔，OSS存储", dataType="String")
	private String ossUrls;
	
	@ApiModelProperty(value="赞数,缓存", dataType="Integer")
	private Integer ayes;
	
	@ApiModelProperty(value="踩数,缓存", dataType="Integer")
	private Integer antis;
	
	@ApiModelProperty(value="回答数", dataType="Integer")
	private Integer answers;
	
	@ApiModelProperty(value="精品排序，从1开始，空代表非精品问答", dataType="Integer")
	private Integer selectOrder;

	private JmAppUser user;

	private JmIndustries industry;

	public JmAppUser getUser() {
		return user;
	}

	public void setUser(JmAppUser user) {
		this.user = user;
	}

	public JmIndustries getIndustry() {
		return industry;
	}

	public void setIndustry(JmIndustries industry) {
		this.industry = industry;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getIndustryId() {
		return industryId;
	}

	public void setIndustryId(String industryId) {
		this.industryId = industryId;
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
	
	public String getOssUrls() {
		return ossUrls;
	}

	public void setOssUrls(String ossUrls) {
		this.ossUrls = ossUrls;
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
	
	public Integer getAnswers() {
		return answers;
	}

	public void setAnswers(Integer answers) {
		this.answers = answers;
	}
	
	public Integer getSelectOrder() {
		return selectOrder;
	}

	public void setSelectOrder(Integer selectOrder) {
		this.selectOrder = selectOrder;
	}

	public static void main(String[] args) {
		JmWisdomQuestions obj = new JmWisdomQuestions();
		System.out.println(obj.industryId);
		System.out.println(obj.getSelectOrder());
	}
}