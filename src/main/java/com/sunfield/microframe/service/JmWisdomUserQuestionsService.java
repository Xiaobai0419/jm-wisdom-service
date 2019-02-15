package com.sunfield.microframe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codingapi.tx.annotation.ITxTransaction;

import com.sunfield.microframe.common.response.Page;

import com.sunfield.microframe.domain.JmWisdomUserQuestions;
import com.sunfield.microframe.mapper.JmWisdomUserQuestionsMapper;

/**
 * jm_wisdom_user_questions service
 * @author sunfield coder
 */
@Service
public class JmWisdomUserQuestionsService implements ITxTransaction{

	@Autowired
	private JmWisdomUserQuestionsMapper mapper;
	
	public List<JmWisdomUserQuestions> findList(JmWisdomUserQuestions obj){
		return mapper.findList(obj);
	}
	
	public Page<JmWisdomUserQuestions> findPage(JmWisdomUserQuestions obj){
		List<JmWisdomUserQuestions> totalList = mapper.findList(obj);
		if(!totalList.isEmpty()){
			List<JmWisdomUserQuestions> pageList = mapper.findPage(obj);
			return new Page<JmWisdomUserQuestions>(totalList.size(), obj.getPageSize(), obj.getPageNumber(), pageList);
		}else{
			return new Page<JmWisdomUserQuestions>();
		}
	}
	
//	public JmWisdomUserQuestions findOne(String id){
//		return mapper.findOne(id);
//	}

	//用户获取自身对各类目标对象的赞、踩情况
	public JmWisdomUserQuestions findOne(JmWisdomUserQuestions obj){
		return mapper.findOne(obj);
	}
	
	@Transactional
	public JmWisdomUserQuestions insert(JmWisdomUserQuestions obj){
		obj.preInsert();
		if(mapper.insert(obj) > 0) {
			return obj;
		} else {
			return null;
		}
	}
	
	@Transactional
	public JmWisdomUserQuestions update(JmWisdomUserQuestions obj){
		obj.preUpdate();
		if(mapper.update(obj) > 0) {
			return obj;
		} else {
			return null;
		}
	}
	
//	@Transactional
//	public int delete(String id){
//		return mapper.delete(id);
//	}

	//用户取消踩/赞/收藏时，进行逻辑删除
	@Transactional
	public int delete(JmWisdomUserQuestions obj){
		return mapper.delete(obj);
	}
}
