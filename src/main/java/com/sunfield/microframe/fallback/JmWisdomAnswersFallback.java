package com.sunfield.microframe.fallback;

import java.util.List;

import com.sunfield.microframe.common.response.Page;
import com.sunfield.microframe.common.response.ResponseBean;
import com.sunfield.microframe.common.response.ResponseStatus;

import com.sunfield.microframe.domain.JmWisdomAnswers;

/**
 * jm_wisdom_answers fallback
 * @author sunfield coder
 */
public class JmWisdomAnswersFallback {
	
    public ResponseBean<List<JmWisdomAnswers>> findListFallback(JmWisdomAnswers obj) {
    	return new ResponseBean<List<JmWisdomAnswers>>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<Page<JmWisdomAnswers>> findPageFallback(JmWisdomAnswers obj) {
    	return new ResponseBean<Page<JmWisdomAnswers>>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<JmWisdomAnswers> findOneFallback(JmWisdomAnswers obj) {
		return new ResponseBean<JmWisdomAnswers>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<JmWisdomAnswers> insertFallback(JmWisdomAnswers obj) {
    	return new ResponseBean<JmWisdomAnswers>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<JmWisdomAnswers> updateFallback(JmWisdomAnswers obj) {
    	return new ResponseBean<JmWisdomAnswers>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<JmWisdomAnswers> deleteFallback(JmWisdomAnswers obj) {
    	return new ResponseBean<JmWisdomAnswers>(ResponseStatus.BUSY);
    }
}
