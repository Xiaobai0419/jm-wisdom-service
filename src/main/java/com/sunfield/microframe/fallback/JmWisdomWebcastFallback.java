package com.sunfield.microframe.fallback;

import java.util.List;

import com.sunfield.microframe.common.response.Page;
import com.sunfield.microframe.common.response.ResponseBean;
import com.sunfield.microframe.common.response.ResponseStatus;

import com.sunfield.microframe.domain.JmWisdomWebcast;

/**
 * jm_wisdom_webcast fallback
 * @author sunfield coder
 */
public class JmWisdomWebcastFallback {
	
    public ResponseBean<List<JmWisdomWebcast>> findListFallback(JmWisdomWebcast obj) {
    	return new ResponseBean<List<JmWisdomWebcast>>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<Page<JmWisdomWebcast>> findPageFallback(JmWisdomWebcast obj) {
    	return new ResponseBean<Page<JmWisdomWebcast>>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<JmWisdomWebcast> findOneFallback(JmWisdomWebcast obj) {
		return new ResponseBean<JmWisdomWebcast>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<JmWisdomWebcast> insertFallback(JmWisdomWebcast obj) {
    	return new ResponseBean<JmWisdomWebcast>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<JmWisdomWebcast> updateFallback(JmWisdomWebcast obj) {
    	return new ResponseBean<JmWisdomWebcast>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<JmWisdomWebcast> deleteFallback(JmWisdomWebcast obj) {
    	return new ResponseBean<JmWisdomWebcast>(ResponseStatus.BUSY);
    }
}
