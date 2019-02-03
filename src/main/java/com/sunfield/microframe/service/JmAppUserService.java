package com.sunfield.microframe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codingapi.tx.annotation.ITxTransaction;

import com.sunfield.microframe.common.response.Page;

import com.sunfield.microframe.domain.JmAppUser;
import com.sunfield.microframe.mapper.JmAppUserMapper;

/**
 * jm_app_user service
 * @author sunfield coder
 */
@Service
public class JmAppUserService implements ITxTransaction{

	@Autowired
	private JmAppUserMapper mapper;
	
	public List<JmAppUser> findList(JmAppUser obj){
		return mapper.findList(obj);
	}
	
	public Page<JmAppUser> findPage(JmAppUser obj){
		List<JmAppUser> totalList = mapper.findList(obj);
		if(!totalList.isEmpty()){
			List<JmAppUser> pageList = mapper.findPage(obj);
			return new Page<JmAppUser>(totalList.size(), obj.getPageSize(), obj.getPageNumber(), pageList);
		}else{
			return new Page<JmAppUser>();
		}
	}
	
	public JmAppUser findOne(String id){
		return mapper.findOne(id);
	}
	
	@Transactional
	public JmAppUser insert(JmAppUser obj){
		obj.preInsert();
		if(mapper.insert(obj) > 0) {
			return obj;
		} else {
			return null;
		}
	}
	
	@Transactional
	public JmAppUser update(JmAppUser obj){
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
