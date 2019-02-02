package com.sunfield.microframe.fallback;

import java.util.List;

import com.sunfield.microframe.common.response.Page;
import com.sunfield.microframe.common.response.ResponseBean;
import com.sunfield.microframe.common.response.ResponseStatus;

import com.sunfield.microframe.domain.MUsers;

/**
 * m_users fallback
 * @author sunfield coder
 */
public class MUsersFallback {
	
    public ResponseBean<List<MUsers>> findListFallback(MUsers obj) {
    	return new ResponseBean<List<MUsers>>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<Page<MUsers>> findPageFallback(MUsers obj) {
    	return new ResponseBean<Page<MUsers>>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<MUsers> findOneFallback(MUsers obj) {
		return new ResponseBean<MUsers>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<MUsers> insertFallback(MUsers obj) {
    	return new ResponseBean<MUsers>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<MUsers> updateFallback(MUsers obj) {
    	return new ResponseBean<MUsers>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<MUsers> deleteFallback(MUsers obj) {
    	return new ResponseBean<MUsers>(ResponseStatus.BUSY);
    }
}
