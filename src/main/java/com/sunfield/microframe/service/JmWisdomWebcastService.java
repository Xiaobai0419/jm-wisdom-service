package com.sunfield.microframe.service;

import java.util.List;

import com.sunfield.microframe.common.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codingapi.tx.annotation.ITxTransaction;

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

	public JmWisdomWebcast findCurrent(){
		return mapper.findCurrent();
	}

	@Transactional
	public WebcastResponseBean<JmWisdomWebcast> insert(JmWisdomWebcast obj){
		obj.preInsert();
		//判断开始时间<结束时间
		if(obj.getBeginTime().equals(obj.getEndTime()) || obj.getBeginTime().after(obj.getEndTime())) {
			return new WebcastResponseBean<JmWisdomWebcast>(WebcastResponseStatus.BEGIN_AFTER_END);
		}
		//查询是否与当前直播冲突
		JmWisdomWebcast current = mapper.findCurrent();
		if(current != null && (obj.getBeginTime().before(current.getEndTime()) || obj.getBeginTime().equals(current.getEndTime()))) {
			return new WebcastResponseBean<JmWisdomWebcast>(WebcastResponseStatus.CONFLICT);
		}
		if(mapper.insert(obj) > 0) {
			return new WebcastResponseBean<JmWisdomWebcast>(WebcastResponseStatus.SUCCESS, obj);
		} else {
			return new WebcastResponseBean<JmWisdomWebcast>(WebcastResponseStatus.FAIL);
		}
	}
	
	@Transactional
	public WebcastResponseBean<JmWisdomWebcast> update(JmWisdomWebcast obj){
		obj.preUpdate();
		//判断开始时间<结束时间
		if(obj.getBeginTime().equals(obj.getEndTime()) || obj.getBeginTime().after(obj.getEndTime())) {
			return new WebcastResponseBean<JmWisdomWebcast>(WebcastResponseStatus.BEGIN_AFTER_END);
		}
		//查询是否与当前直播冲突
		JmWisdomWebcast current = mapper.findCurrent();
		if(current != null && (obj.getBeginTime().before(current.getEndTime()) || obj.getBeginTime().equals(current.getEndTime()))) {
			return new WebcastResponseBean<JmWisdomWebcast>(WebcastResponseStatus.CONFLICT);
		}
		if(mapper.update(obj) > 0) {
			return new WebcastResponseBean<JmWisdomWebcast>(WebcastResponseStatus.SUCCESS, obj);
		} else {
			return new WebcastResponseBean<JmWisdomWebcast>(WebcastResponseStatus.FAIL);
		}
	}
	
	@Transactional
	public int delete(String id){
		return mapper.delete(id);
	}
	
}
