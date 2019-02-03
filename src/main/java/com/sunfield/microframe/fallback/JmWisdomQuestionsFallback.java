package com.sunfield.microframe.fallback;

import java.util.List;

import com.sunfield.microframe.common.response.Page;
import com.sunfield.microframe.common.response.ResponseBean;
import com.sunfield.microframe.common.response.ResponseStatus;

import com.sunfield.microframe.domain.JmWisdomQuestions;

/**
 * jm_wisdom_questions fallback
 * @author sunfield coder
 */
public class JmWisdomQuestionsFallback {
	
    public ResponseBean<List<JmWisdomQuestions>> findListFallback(JmWisdomQuestions obj) {
    	return new ResponseBean<List<JmWisdomQuestions>>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<Page<JmWisdomQuestions>> findPageFallback(JmWisdomQuestions obj) {
    	return new ResponseBean<Page<JmWisdomQuestions>>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<JmWisdomQuestions> findOneFallback(JmWisdomQuestions obj) {
		return new ResponseBean<JmWisdomQuestions>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<JmWisdomQuestions> insertFallback(JmWisdomQuestions obj) {
    	return new ResponseBean<JmWisdomQuestions>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<JmWisdomQuestions> updateFallback(JmWisdomQuestions obj) {
    	return new ResponseBean<JmWisdomQuestions>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<JmWisdomQuestions> deleteFallback(JmWisdomQuestions obj) {
    	return new ResponseBean<JmWisdomQuestions>(ResponseStatus.BUSY);
    }
}
