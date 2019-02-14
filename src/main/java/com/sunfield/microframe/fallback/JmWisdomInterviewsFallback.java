package com.sunfield.microframe.fallback;

import java.util.List;

import com.sunfield.microframe.common.response.Page;
import com.sunfield.microframe.common.response.ResponseBean;
import com.sunfield.microframe.common.response.ResponseStatus;

import com.sunfield.microframe.domain.JmWisdomInterviews;

/**
 * jm_wisdom_interviews fallback
 * @author sunfield coder
 */
public class JmWisdomInterviewsFallback {
	
    public ResponseBean<List<JmWisdomInterviews>> findListFallback(JmWisdomInterviews obj) {
    	return new ResponseBean<List<JmWisdomInterviews>>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<Page<JmWisdomInterviews>> findPageFallback(JmWisdomInterviews obj) {
    	return new ResponseBean<Page<JmWisdomInterviews>>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<JmWisdomInterviews> findOneFallback(JmWisdomInterviews obj) {
		return new ResponseBean<JmWisdomInterviews>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<JmWisdomInterviews> insertFallback(JmWisdomInterviews obj) {
    	return new ResponseBean<JmWisdomInterviews>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<JmWisdomInterviews> updateFallback(JmWisdomInterviews obj) {
    	return new ResponseBean<JmWisdomInterviews>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<JmWisdomInterviews> deleteFallback(JmWisdomInterviews obj) {
    	return new ResponseBean<JmWisdomInterviews>(ResponseStatus.BUSY);
    }
}
