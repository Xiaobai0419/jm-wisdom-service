package com.sunfield.microframe.service;

import java.util.List;

import com.sunfield.microframe.domain.JmAppUser;
import com.sunfield.microframe.domain.JmWisdomUserQuestions;
import com.sunfield.microframe.feign.JmAppUserFeignService;
import com.sunfield.microframe.mapper.JmWisdomUserQuestionsMapper;
import org.apache.commons.lang.StringUtils;
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
	private JmWisdomUserQuestionsMapper jmWisdomUserQuestionsMapper;
	@Autowired
	@Qualifier("jmAppUserFeignService")
	private JmAppUserFeignService jmAppUserFeignService;

	private JmAppUser findUser(String userId) {
		return jmAppUserFeignService.findOne(userId).getData();
	}

	public List<JmWisdomAnswers> findList(JmWisdomAnswers obj){
		List<JmWisdomAnswers> list = mapper.findList(obj);
		if(list != null && list.size() > 0) {
			JmWisdomUserQuestions jmWisdomUserQuestions = new JmWisdomUserQuestions();
			for(JmWisdomAnswers answers : list) {
				if(answers != null && StringUtils.isNotBlank(answers.getUserId())) {
					JmAppUser user = findUser(answers.getUserId());
					answers.setUser(user);
				}
				//新增查询：访问用户对该回答的踩赞状态
				if(answers != null && StringUtils.isNotBlank(obj.getVisitUserId())) {//注意是传入对象的访问用户id,必须判断，后台管理或未登录用户可以不传递访问者id
					jmWisdomUserQuestions.setType(2);
					jmWisdomUserQuestions.setQuestionId(answers.getId());
					jmWisdomUserQuestions.setUserId(obj.getVisitUserId());//注意是传入对象的访问用户id
					JmWisdomUserQuestions result = jmWisdomUserQuestionsMapper.findOne(jmWisdomUserQuestions);
					answers.setVisitUserYesOrNo((result != null && result.getYesorno() != null) ? result.getYesorno() : 0);
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
				JmWisdomUserQuestions jmWisdomUserQuestions = new JmWisdomUserQuestions();
				for(JmWisdomAnswers answers : pageList) {
					if(answers != null && StringUtils.isNotBlank(answers.getUserId())) {
						JmAppUser user = findUser(answers.getUserId());
						answers.setUser(user);
					}
					//新增查询：访问用户对该回答的踩赞状态
					if(answers != null && StringUtils.isNotBlank(obj.getVisitUserId())) {//注意是传入对象的访问用户id,必须判断，后台管理或未登录用户可以不传递访问者id
						jmWisdomUserQuestions.setType(2);
						jmWisdomUserQuestions.setQuestionId(answers.getId());
						jmWisdomUserQuestions.setUserId(obj.getVisitUserId());//注意是传入对象的访问用户id
						JmWisdomUserQuestions result = jmWisdomUserQuestionsMapper.findOne(jmWisdomUserQuestions);
						answers.setVisitUserYesOrNo((result != null && result.getYesorno() != null) ? result.getYesorno() : 0);
					}
				}
			}
			return new Page<JmWisdomAnswers>(totalList.size(), obj.getPageSize(), obj.getPageNumber(), pageList);
		}else{
			return new Page<JmWisdomAnswers>();
		}
	}
	
	public JmWisdomAnswers findOne(JmWisdomAnswers jmWisdomAnswers){
		JmWisdomAnswers answers = mapper.findOne(jmWisdomAnswers.getId());
		if(answers != null && answers.getUserId() != null) {
			JmAppUser user = findUser(answers.getUserId());
			answers.setUser(user);
		}
		JmWisdomUserQuestions jmWisdomUserQuestions = new JmWisdomUserQuestions();
		//新增查询：访问用户对该回答的踩赞状态
		if(answers != null && StringUtils.isNotBlank(jmWisdomAnswers.getVisitUserId())) {//注意是传入对象的访问用户id,必须判断，后台管理或未登录用户可以不传递访问者id
			jmWisdomUserQuestions.setType(2);
			jmWisdomUserQuestions.setQuestionId(answers.getId());
			jmWisdomUserQuestions.setUserId(jmWisdomAnswers.getVisitUserId());//注意是传入对象的访问用户id
			JmWisdomUserQuestions result = jmWisdomUserQuestionsMapper.findOne(jmWisdomUserQuestions);
			answers.setVisitUserYesOrNo((result != null && result.getYesorno() != null) ? result.getYesorno() : 0);
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


	public static void main(String[] args) {
		JmWisdomUserQuestions result = null;
		System.out.println((result != null && result.getYesorno() != null) ? result.getYesorno() : 0);
	}
}
