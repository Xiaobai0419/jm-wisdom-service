package com.sunfield.microframe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codingapi.tx.annotation.ITxTransaction;

import com.sunfield.microframe.common.response.Page;

import com.sunfield.microframe.domain.JmWisdomWebcast;
import com.sunfield.microframe.mapper.JmWisdomWebcastMapper;

/**
 * jm_wisdom_webcast service
 * @author sunfield coder
 */
@Service
public class JmWisdomWebcastService implements ITxTransaction{

	@Autowired
	private JmWisdomWebcastMapper mapper;
	
	public List<JmWisdomWebcast> findList(JmWisdomWebcast obj){
		return mapper.findList(obj);
	}
	
	public Page<JmWisdomWebcast> findPage(JmWisdomWebcast obj){
		List<JmWisdomWebcast> totalList = mapper.findList(obj);
		if(!totalList.isEmpty()){
			List<JmWisdomWebcast> pageList = mapper.findPage(obj);
			return new Page<JmWisdomWebcast>(totalList.size(), obj.getPageSize(), obj.getPageNumber(), pageList);
		}else{
			return new Page<JmWisdomWebcast>();
		}
	}
	
	public JmWisdomWebcast findOne(String id){
		return mapper.findOne(id);
	}
	
	@Transactional
	public JmWisdomWebcast insert(JmWisdomWebcast obj){
		obj.preInsert();
		if(mapper.insert(obj) > 0) {
			return obj;
		} else {
			return null;
		}
	}
	
	@Transactional
	public JmWisdomWebcast update(JmWisdomWebcast obj){
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
