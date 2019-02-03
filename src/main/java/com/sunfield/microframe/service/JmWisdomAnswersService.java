package com.sunfield.microframe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codingapi.tx.annotation.ITxTransaction;

import com.sunfield.microframe.common.response.Page;

import com.sunfield.microframe.domain.JmWisdomAnswers;
import com.sunfield.microframe.mapper.JmWisdomAnswersMapper;

/**
 * jm_wisdom_answers service
 * @author sunfield coder
 */
@Service
public class JmWisdomAnswersService implements ITxTransaction{

	@Autowired
	private JmWisdomAnswersMapper mapper;
	
	public List<JmWisdomAnswers> findList(JmWisdomAnswers obj){
		return mapper.findList(obj);
	}
	
	public Page<JmWisdomAnswers> findPage(JmWisdomAnswers obj){
		List<JmWisdomAnswers> totalList = mapper.findList(obj);
		if(!totalList.isEmpty()){
			List<JmWisdomAnswers> pageList = mapper.findPage(obj);
			return new Page<JmWisdomAnswers>(totalList.size(), obj.getPageSize(), obj.getPageNumber(), pageList);
		}else{
			return new Page<JmWisdomAnswers>();
		}
	}
	
	public JmWisdomAnswers findOne(String id){
		return mapper.findOne(id);
	}
	
	@Transactional
	public JmWisdomAnswers insert(JmWisdomAnswers obj){
		obj.preInsert();
		if(mapper.insert(obj) > 0) {
			return obj;
		} else {
			return null;
		}
	}
	
	@Transactional
	public JmWisdomAnswers update(JmWisdomAnswers obj){
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
