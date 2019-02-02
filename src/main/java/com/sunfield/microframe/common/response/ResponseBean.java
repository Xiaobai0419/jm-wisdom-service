package com.sunfield.microframe.common.response;

/**
 * common response bean
 * @author wangnan
 *
 */
public class ResponseBean<T> {

	private ResponseStatus status;
	
	private String msg;
	
	private T data;
	
	public ResponseBean(){
		this.status = ResponseStatus.SUCCESS;
		this.msg = ResponseStatus.getMsg(this.status);
	}
	
	public ResponseBean(ResponseStatus status){
		this.status = status;
		this.msg = ResponseStatus.getMsg(status);
	}
	
	public ResponseBean(ResponseStatus status, T data) {
		this.status = status;
		this.msg = ResponseStatus.getMsg(status);
		this.data = data;
	}

	public ResponseBean(ResponseStatus status, String msg){
		this.status = status;
		this.msg = msg;
	}
	
	public ResponseBean(ResponseStatus status, String msg, T data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
	}
	
	public Boolean hasError(){
		return !ResponseStatus.SUCCESS.equals(this.getStatus());
	}
	
	public ResponseStatus getStatus() {
		return status;
	}

	public void setStatus(ResponseStatus status) {
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
