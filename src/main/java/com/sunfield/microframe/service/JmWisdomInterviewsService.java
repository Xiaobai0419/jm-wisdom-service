package com.sunfield.microframe.service;

import java.util.List;

import com.sunfield.microframe.domain.JmWisdomUserQuestions;
import com.sunfield.microframe.mapper.JmWisdomUserQuestionsMapper;
import org.apache.commons.lang.StringUtils;
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
	@Autowired
	private JmWisdomUserQuestionsMapper jmWisdomUserQuestionsMapper;
	
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
	
	public JmWisdomInterviews findOne(JmWisdomInterviews interviews){

		JmWisdomInterviews jmWisdomInterviews = mapper.findOne(interviews.getId());
		JmWisdomUserQuestions jmWisdomUserQuestions = new JmWisdomUserQuestions();
		//新增查询：访问用户对该访谈的收藏状态
		if(jmWisdomInterviews != null && StringUtils.isNotBlank(interviews.getVisitUserId())) {//注意是传入对象的访问用户id,必须判断，后台管理或未登录用户可以不传递访问者id
			jmWisdomUserQuestions.setType(3);
			jmWisdomUserQuestions.setQuestionId(jmWisdomInterviews.getId());
			jmWisdomUserQuestions.setUserId(interviews.getVisitUserId());//注意是传入对象的访问用户id
			JmWisdomUserQuestions result = jmWisdomUserQuestionsMapper.findOne(jmWisdomUserQuestions);
			jmWisdomInterviews.setVisitUserYesOrNo((result != null && result.getYesorno() != null) ? result.getYesorno() : 0);
		}
		return jmWisdomInterviews;
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
