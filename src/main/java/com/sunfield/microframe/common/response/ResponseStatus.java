package com.sunfield.microframe.common.response;

/**
 * common response status
 * @author wangnan
 *
 */
public enum ResponseStatus {

	SUCCESS,
	FAIL,
	NO_DATA,
	PARAMS_ERROR,
	BUSY,
	FORBIDEN;
	
	public static String getMsg(ResponseStatus rs){
		switch (rs) {
			case SUCCESS:
				return "请求成功";
			case FAIL:
				return "请求失败";
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
