package com.sunfield.microframe.common.response;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页数据
 * @author wangnan
 *
 */
public class Page<T> {

	/**总数**/
	private Integer totalNum;
	
	/**总页数**/
	private Integer totalPage;
	
	/**每页条数**/
	private Integer pageSize;
	
	/**当前页码**/
	private Integer pageNumber;
	
	/**本页数据**/
	private List<T> data;
	
	public Page(Integer totalNum, Integer pageSize, Integer pageNumber, List<T> data){
		this.totalNum = totalNum;
		this.totalPage = totalNum % pageSize == 0 ? totalNum / pageSize : totalNum / pageSize + 1;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.data = data;
	}
	
	public Page(Integer totalNum, Integer pageSize, Integer pageNumber){
		new Page<T>(totalNum, pageSize, pageNumber, null);
	}
	
	public Page(){
		this.totalNum = 0;
		this.totalPage = 0;
		this.pageNumber = 0;
		this.pageSize = 0;
		this.data = new ArrayList<T>();
	}
	
	/**
	 * 判断当前对象是否存在数据
	 * @return true:存在数据   false:不存在数据
	 */
	public Boolean hasData(){
		return this.totalNum > 0;
	}
	
	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
