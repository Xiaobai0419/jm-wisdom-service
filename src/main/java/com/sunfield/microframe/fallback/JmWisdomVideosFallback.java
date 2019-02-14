package com.sunfield.microframe.fallback;

import java.util.List;

import com.sunfield.microframe.common.response.Page;
import com.sunfield.microframe.common.response.ResponseBean;
import com.sunfield.microframe.common.response.ResponseStatus;

import com.sunfield.microframe.domain.JmWisdomVideos;

/**
 * jm_wisdom_videos fallback
 * @author sunfield coder
 */
public class JmWisdomVideosFallback {
	
    public ResponseBean<List<JmWisdomVideos>> findListFallback(JmWisdomVideos obj) {
    	return new ResponseBean<List<JmWisdomVideos>>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<Page<JmWisdomVideos>> findPageFallback(JmWisdomVideos obj) {
    	return new ResponseBean<Page<JmWisdomVideos>>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<JmWisdomVideos> findOneFallback(JmWisdomVideos obj) {
		return new ResponseBean<JmWisdomVideos>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<JmWisdomVideos> insertFallback(JmWisdomVideos obj) {
    	return new ResponseBean<JmWisdomVideos>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<JmWisdomVideos> updateFallback(JmWisdomVideos obj) {
    	return new ResponseBean<JmWisdomVideos>(ResponseStatus.BUSY);
    }
	
    public ResponseBean<JmWisdomVideos> deleteFallback(JmWisdomVideos obj) {
    	return new ResponseBean<JmWisdomVideos>(ResponseStatus.BUSY);
    }
}
