package com.sunfield.microframe.common.response;

public class WebcastResponseBean<T> {

    private String status;

    private String msg;

    private T data;

    public WebcastResponseBean(WebcastResponseStatus status){
        this.status = WebcastResponseStatus.getStatus(status);
        this.msg = WebcastResponseStatus.getMsg(status);
    }

    public WebcastResponseBean(WebcastResponseStatus status, T data) {
        this.status = WebcastResponseStatus.getStatus(status);
        this.msg = WebcastResponseStatus.getMsg(status);
        this.data = data;
    }

    public WebcastResponseBean(WebcastResponseStatus status, String msg){
        this.status = WebcastResponseStatus.getStatus(status);
        this.msg = msg;
    }

    public WebcastResponseBean(WebcastResponseStatus status, String msg, T data) {
        this.status = WebcastResponseStatus.getStatus(status);
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
