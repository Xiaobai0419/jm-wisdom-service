package com.sunfield.microframe.common.response;

/**
 * common response status
 * @author wangnan
 *
 */
public enum ResponseStatus {

	SUCCESS,
	FAIL,
	NO_MOBILE,
	NO_DATA,
	PARAMS_ERROR,
	BUSY,
	FORBIDEN;

	public static String getStatus(ResponseStatus rs){
		switch (rs) {
			case SUCCESS:
				return "SUCCESS";
			case FAIL:
				return "FAIL";
			case NO_MOBILE:
				return "NO_MOBILE";
			case NO_DATA:
				return "NO_DATA";
			case PARAMS_ERROR:
				return "PARAMS_ERROR";
			case FORBIDEN:
				return "FORBIDEN";
			case BUSY:
				return "BUSY";
			default:
				return "UNKNOWN";
		}
	}

	public static String getMsg(ResponseStatus rs){
		switch (rs) {
			case SUCCESS:
				return "请求成功";
			case FAIL:
				return "请求失败";
			case NO_MOBILE:
				return "未绑定手机号";
			case NO_DATA:
				return "无返回数据";
			case PARAMS_ERROR:
				return "参数错误";
			case FORBIDEN:
				return "无权限";
			case BUSY:
				return "系统繁忙";
			default:
				return "未知错误";
		}
	}
}
