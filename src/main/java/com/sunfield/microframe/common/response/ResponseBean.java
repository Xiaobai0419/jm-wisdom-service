package com.sunfield.microframe.common.response;

/**
 * common response bean
 * @author wangnan
 *
 */
public class ResponseBean<T> {

	private String status;
	
	private String msg;
	
	private T data;
	
	public ResponseBean(){
		this.status = ResponseStatus.getStatus(ResponseStatus.SUCCESS);
		this.msg = ResponseStatus.getMsg(ResponseStatus.SUCCESS);
	}
	
	public ResponseBean(ResponseStatus status){
		this.status = ResponseStatus.getStatus(status);
		this.msg = ResponseStatus.getMsg(status);
	}
	
	public ResponseBean(ResponseStatus status, T data) {
		this.status = ResponseStatus.getStatus(status);
		this.msg = ResponseStatus.getMsg(status);
		this.data = data;
	}

	public ResponseBean(ResponseStatus status, String msg){
		this.status = ResponseStatus.getStatus(status);
		this.msg = msg;
	}
	
	public ResponseBean(ResponseStatus status, String msg, T data) {
		this.status = ResponseStatus.getStatus(status);
		this.msg = msg;
		this.data = data;
	}
	
	public Boolean hasError(){
		return "SUCCESS".equals(this.getStatus());
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
