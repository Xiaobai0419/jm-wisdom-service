package com.sunfield.microframe.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

import com.sunfield.microframe.domain.base.BaseDomain;

/**
 * jm_industries bean
 * @author sunfield coder
 */
@ApiModel(value="JmIndustries", description="")
public class JmIndustries extends BaseDomain{

	@ApiModelProperty(value="行业类别", dataType="String")
	private String name;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}