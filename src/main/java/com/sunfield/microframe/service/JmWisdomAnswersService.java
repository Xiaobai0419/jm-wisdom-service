package com.sunfield.microframe.service;

import java.util.List;

import com.sunfield.microframe.domain.JmAppUser;
import com.sunfield.microframe.feign.JmAppUserFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
	@Autowired
	@Qualifier("jmAppUserFeignService")
	private JmAppUserFeignService jmAppUserFeignService;

	private JmAppUser findUser(String userId) {
		return jmAppUserFeignService.findOne(userId).getData();
	}

	public List<JmWisdomAnswers> findList(JmWisdomAnswers obj){
		List<JmWisdomAnswers> list = mapper.findList(obj);
		if(list != null && list.size() > 0) {
			for(JmWisdomAnswers answers : list) {
				if(answers != null && answers.getUserId() != null) {
					JmAppUser user = findUser(answers.getUserId());
					answers.setUser(user);
				}
			}
		}
		return list;
	}
	
	public Page<JmWisdomAnswers> findPage(JmWisdomAnswers obj){
		List<JmWisdomAnswers> totalList = mapper.findList(obj);
		if(!totalList.isEmpty()){
			List<JmWisdomAnswers> pageList = mapper.findPage(obj);
			if(pageList != null && pageList.size() > 0) {
				for(JmWisdomAnswers answers : pageList) {
					if(answers != null && answers.getUserId() != null) {
						JmAppUser user = findUser(answers.getUserId());
						answers.setUser(user);
					}
				}
			}
			return new Page<JmWisdomAnswers>(totalList.size(), obj.getPageSize(), obj.getPageNumber(), pageList);
		}else{
			return new Page<JmWisdomAnswers>();
		}
	}
	
	public JmWisdomAnswers findOne(String id){
		JmWisdomAnswers answers = mapper.findOne(id);
		if(answers != null && answers.getUserId() != null) {
			JmAppUser user = findUser(answers.getUserId());
			answers.setUser(user);
		}
		return answers;
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
