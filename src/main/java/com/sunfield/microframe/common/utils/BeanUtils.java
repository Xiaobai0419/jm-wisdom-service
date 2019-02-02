package com.sunfield.microframe.common.utils;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class BeanUtils {

	public static <T> T parseMap2Object(Map<String, Object> paramMap, Class<T> cls) {
		return JSONObject.parseObject(JSONObject.toJSONString(paramMap), cls);
	}
	
}
