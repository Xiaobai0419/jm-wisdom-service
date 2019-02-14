package com.sunfield.microframe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codingapi.tx.annotation.ITxTransaction;

import com.sunfield.microframe.common.response.Page;

import com.sunfield.microframe.domain.JmWisdomInterviews;
import com.sunfield.microframe.mapper.JmWisdomInterviewsMapper;

/**
 * jm_wisdom_interviews service
 * @author sunfield coder
 */
@Service
public class JmWisdomInterviewsService implements ITxTransaction{

	@Autowired
	private JmWisdomInterviewsMapper mapper;
	
	public List<JmWisdomInterviews> findList(JmWisdomInterviews obj){
		return mapper.findList(obj);
	}
	
	public Page<JmWisdomInterviews> findPage(JmWisdomInterviews obj){
		List<JmWisdomInterviews> totalList = mapper.findList(obj);
		if(!totalList.isEmpty()){
			List<JmWisdomInterviews> pageList = mapper.findPage(obj);
			return new Page<JmWisdomInterviews>(totalList.size(), obj.getPageSize(), obj.getPageNumber(), pageList);
		}else{
			return new Page<JmWisdomInterviews>();
		}
	}
	
	public JmWisdomInterviews findOne(String id){
		return mapper.findOne(id);
	}
	
	@Transactional
	public JmWisdomInterviews insert(JmWisdomInterviews obj){
		obj.preInsert();
		if(mapper.insert(obj) > 0) {
			return obj;
		} else {
			return null;
		}
	}
	
	@Transactional
	public JmWisdomInterviews update(JmWisdomInterviews obj){
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
