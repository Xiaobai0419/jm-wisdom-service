package com.sunfield.microframe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codingapi.tx.annotation.ITxTransaction;

import com.sunfield.microframe.common.response.Page;

import com.sunfield.microframe.domain.JmWisdomQuestions;
import com.sunfield.microframe.mapper.JmWisdomQuestionsMapper;

/**
 * jm_wisdom_questions service
 * @author sunfield coder
 */
@Service
public class JmWisdomQuestionsService implements ITxTransaction{

	@Autowired
	private JmWisdomQuestionsMapper mapper;
	
	public List<JmWisdomQuestions> findList(JmWisdomQuestions obj){
		return mapper.findList(obj);
	}
	
	public Page<JmWisdomQuestions> findPage(JmWisdomQuestions obj){
		List<JmWisdomQuestions> totalList = mapper.findList(obj);
		if(!totalList.isEmpty()){
			List<JmWisdomQuestions> pageList = mapper.findPage(obj);
			return new Page<JmWisdomQuestions>(totalList.size(), obj.getPageSize(), obj.getPageNumber(), pageList);
		}else{
			return new Page<JmWisdomQuestions>();
		}
	}
	
	public JmWisdomQuestions findOne(String id){
		return mapper.findOne(id);
	}
	
	@Transactional
	public JmWisdomQuestions insert(JmWisdomQuestions obj){
		obj.preInsert();
		if(mapper.insert(obj) > 0) {
			return obj;
		} else {
			return null;
		}
	}
	
	@Transactional
	public JmWisdomQuestions update(JmWisdomQuestions obj){
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
