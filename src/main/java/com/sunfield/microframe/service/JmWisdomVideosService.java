package com.sunfield.microframe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codingapi.tx.annotation.ITxTransaction;

import com.sunfield.microframe.common.response.Page;

import com.sunfield.microframe.domain.JmWisdomVideos;
import com.sunfield.microframe.mapper.JmWisdomVideosMapper;

/**
 * jm_wisdom_videos service
 * @author sunfield coder
 */
@Service
public class JmWisdomVideosService implements ITxTransaction{

	@Autowired
	private JmWisdomVideosMapper mapper;
	
	public List<JmWisdomVideos> findList(JmWisdomVideos obj){
		return mapper.findList(obj);
	}
	
	public Page<JmWisdomVideos> findPage(JmWisdomVideos obj){
		List<JmWisdomVideos> totalList = mapper.findList(obj);
		if(!totalList.isEmpty()){
			List<JmWisdomVideos> pageList = mapper.findPage(obj);
			return new Page<JmWisdomVideos>(totalList.size(), obj.getPageSize(), obj.getPageNumber(), pageList);
		}else{
			return new Page<JmWisdomVideos>();
		}
	}
	
	public JmWisdomVideos findOne(String id){
		return mapper.findOne(id);
	}
	
	@Transactional
	public JmWisdomVideos insert(JmWisdomVideos obj){
		obj.preInsert();
		if(mapper.insert(obj) > 0) {
			return obj;
		} else {
			return null;
		}
	}
	
	@Transactional
	public JmWisdomVideos update(JmWisdomVideos obj){
		obj.preUpdate();
		if(mapper.update(obj) > 0) {
			return obj;
		} else {
			return null;
		}
	}
	
	@Transactional
	public int delete(String id){
		return mapper.delete(id);
	}
	
}
