package com.sunfield.microframe.fallback;

import java.util.List;

import com.sunfield.microframe.common.response.Page;
import com.sunfield.microframe.common.response.ResponseBean;
import com.sunfield.microframe.common.response.ResponseStatus;

import com.sunfield.microframe.domain.JmIndustries;

/**
 * jm_industries fallback
 * @author sunfield coder
 */
public class JmIndustriesFallback {
	
    public ResponseBean<List<JmIndustries>> findListFallback(JmIndustries obj) {
    	return new ResponseBean<List<JmIndustries>>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<Page<JmIndustries>> findPageFallback(JmIndustries obj) {
    	return new ResponseBean<Page<JmIndustries>>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<JmIndustries> findOneFallback(JmIndustries obj) {
		return new ResponseBean<JmIndustries>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<JmIndustries> insertFallback(JmIndustries obj) {
    	return new ResponseBean<JmIndustries>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<JmIndustries> updateFallback(JmIndustries obj) {
    	return new ResponseBean<JmIndustries>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<JmIndustries> deleteFallback(JmIndustries obj) {
    	return new ResponseBean<JmIndustries>(ResponseStatus.BUSY);
    }
}
