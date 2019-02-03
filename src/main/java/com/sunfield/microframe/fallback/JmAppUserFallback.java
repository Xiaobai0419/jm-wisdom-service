package com.sunfield.microframe.fallback;

import java.util.List;

import com.sunfield.microframe.common.response.Page;
import com.sunfield.microframe.common.response.ResponseBean;
import com.sunfield.microframe.common.response.ResponseStatus;

import com.sunfield.microframe.domain.JmAppUser;

/**
 * jm_app_user fallback
 * @author sunfield coder
 */
public class JmAppUserFallback {
	
    public ResponseBean<List<JmAppUser>> findListFallback(JmAppUser obj) {
    	return new ResponseBean<List<JmAppUser>>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<Page<JmAppUser>> findPageFallback(JmAppUser obj) {
    	return new ResponseBean<Page<JmAppUser>>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<JmAppUser> findOneFallback(JmAppUser obj) {
		return new ResponseBean<JmAppUser>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<JmAppUser> insertFallback(JmAppUser obj) {
    	return new ResponseBean<JmAppUser>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<JmAppUser> updateFallback(JmAppUser obj) {
    	return new ResponseBean<JmAppUser>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<JmAppUser> deleteFallback(JmAppUser obj) {
    	return new ResponseBean<JmAppUser>(ResponseStatus.BUSY);
    }
}
