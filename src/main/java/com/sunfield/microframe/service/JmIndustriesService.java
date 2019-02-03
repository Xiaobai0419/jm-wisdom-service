package com.sunfield.microframe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codingapi.tx.annotation.ITxTransaction;

import com.sunfield.microframe.common.response.Page;

import com.sunfield.microframe.domain.JmIndustries;
import com.sunfield.microframe.mapper.JmIndustriesMapper;

/**
 * jm_industries service
 * @author sunfield coder
 */
@Service
public class JmIndustriesService implements ITxTransaction{

	@Autowired
	private JmIndustriesMapper mapper;
	
	public List<JmIndustries> findList(JmIndustries obj){
		return mapper.findList(obj);
	}
	
	public Page<JmIndustries> findPage(JmIndustries obj){
		List<JmIndustries> totalList = mapper.findList(obj);
		if(!totalList.isEmpty()){
			List<JmIndustries> pageList = mapper.findPage(obj);
			return new Page<JmIndustries>(totalList.size(), obj.getPageSize(), obj.getPageNumber(), pageList);
		}else{
			return new Page<JmIndustries>();
		}
	}
	
	public JmIndustries findOne(String id){
		return mapper.findOne(id);
	}
	
	@Transactional
	public JmIndustries insert(JmIndustries obj){
		obj.preInsert();
		if(mapper.insert(obj) > 0) {
			return obj;
		} else {
			return null;
		}
	}
	
	@Transactional
	public JmIndustries update(JmIndustries obj){
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
