package com.sunfield.microframe.domain.base;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sunfield.microframe.common.utils.DateUtils;
import com.sunfield.microframe.common.utils.UUID;

import io.swagger.annotations.ApiModelProperty;

public class BaseDomain{
	
	@ApiModelProperty(value="主键", dataType="String")
	private String id;

	@ApiModelProperty(value="创建者", dataType="String")
	private String createBy;

	@ApiModelProperty(value="创建时间", dataType="Date")
	private Date createDate;

	@ApiModelProperty(value="更新者", dataType="String")
	private String updateBy;

	@ApiModelProperty(value="更新时间", dataType="Date")
	private Date updateDate;

	@ApiModelProperty(value="备注", dataType="String")
	private  String remarks;

	/**逻辑删除**/
	private String status;
	
	@ApiModelProperty(value="每页条数", dataType="Integer")
	private int pageSize;
	
	@ApiModelProperty(value="页码", dataType="Integer")
	private int pageNumber;
	
	public void preInsert(){
		Date d = new Date();
		this.setId(UUID.getUUID());
		this.setCreateDate(d);
		this.setUpdateDate(d);
	}
	
	public void preUpdate(){
		Date d = new Date();
		this.setUpdateDate(d);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@JsonFormat(pattern = DateUtils.DEFAULT_FORMAT, timezone = DateUtils.DEFAULT_TIME_ZONE)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@JsonFormat(pattern = DateUtils.DEFAULT_FORMAT, timezone = DateUtils.DEFAULT_TIME_ZONE)
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	@JsonIgnore
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
