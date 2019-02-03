package com.sunfield.microframe.fallback;

import java.util.List;

import com.sunfield.microframe.common.response.Page;
import com.sunfield.microframe.common.response.ResponseBean;
import com.sunfield.microframe.common.response.ResponseStatus;

import com.sunfield.microframe.domain.JmWisdomUserQuestions;

/**
 * jm_wisdom_user_questions fallback
 * @author sunfield coder
 */
public class JmWisdomUserQuestionsFallback {
	
    public ResponseBean<List<JmWisdomUserQuestions>> findListFallback(JmWisdomUserQuestions obj) {
    	return new ResponseBean<List<JmWisdomUserQuestions>>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<Page<JmWisdomUserQuestions>> findPageFallback(JmWisdomUserQuestions obj) {
    	return new ResponseBean<Page<JmWisdomUserQuestions>>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<JmWisdomUserQuestions> findOneFallback(JmWisdomUserQuestions obj) {
		return new ResponseBean<JmWisdomUserQuestions>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<JmWisdomUserQuestions> insertFallback(JmWisdomUserQuestions obj) {
    	return new ResponseBean<JmWisdomUserQuestions>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<JmWisdomUserQuestions> updateFallback(JmWisdomUserQuestions obj) {
    	return new ResponseBean<JmWisdomUserQuestions>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<JmWisdomUserQuestions> deleteFallback(JmWisdomUserQuestions obj) {
    	return new ResponseBean<JmWisdomUserQuestions>(ResponseStatus.BUSY);
    }
}
