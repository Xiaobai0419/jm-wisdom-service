package com.sunfield.microframe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codingapi.tx.annotation.ITxTransaction;

import com.sunfield.microframe.common.response.Page;

import com.sunfield.microframe.domain.MUsers;
import com.sunfield.microframe.mapper.MUsersMapper;

/**
 * m_users service
 * @author sunfield coder
 */
@Service
@CacheConfig(cacheNames = "MUsers")
public class MUsersService implements ITxTransaction{

	@Autowired
	private MUsersMapper mapper;
	
	public List<MUsers> findList(MUsers obj){
		return mapper.findList(obj);
	}
	
	public Page<MUsers> findPage(MUsers obj){
		List<MUsers> totalList = mapper.findList(obj);
		if(!totalList.isEmpty()){
			List<MUsers> pageList = mapper.findPage(obj);
			return new Page<MUsers>(totalList.size(), obj.getPageSize(), obj.getPageNumber(), pageList);
		}else{
			return new Page<MUsers>();
		}
	}

	@Cacheable(key ="#p0")
	public MUsers findOne(String id){
		return mapper.findOne(id);
	}
	
	@Transactional
	public MUsers insert(MUsers obj){
		obj.preInsert();
		if(mapper.insert(obj) > 0)
			return obj;
		else
			return null;
	}
	
	@Transactional
	@CachePut(key = "#p0.id")
	public MUsers update(MUsers obj){
		obj.preUpdate();
		if(mapper.update(obj) > 0)
			return obj;
		else
			return null;
	}
	
	@Transactional
	public int delete(String id){
		return mapper.delete(id);
	}

}
