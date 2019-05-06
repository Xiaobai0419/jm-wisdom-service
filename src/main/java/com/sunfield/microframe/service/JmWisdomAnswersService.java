package com.sunfield.microframe.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sunfield.microframe.common.utils.CacheUtils;
import com.sunfield.microframe.domain.JmAppUser;
import com.sunfield.microframe.domain.JmWisdomQuestions;
import com.sunfield.microframe.domain.JmWisdomUserQuestions;
import com.sunfield.microframe.feign.JmAppUserFeignService;
import com.sunfield.microframe.mapper.JmWisdomQuestionsMapper;
import com.sunfield.microframe.mapper.JmWisdomUserQuestionsMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
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
	private JmWisdomQuestionsMapper jmWisdomQuestionsMapper;
	@Autowired
	private JmWisdomUserQuestionsMapper jmWisdomUserQuestionsMapper;
	@Autowired
	@Qualifier("jmAppUserFeignService")
	private JmAppUserFeignService jmAppUserFeignService;
	@Autowired
	private CacheUtils cacheUtils;

	//实时查最新，不能缓存
	private JmAppUser findUser(String userId) {
		return jmAppUserFeignService.findOne(userId).getData();
	}

	public List<JmWisdomAnswers> findList(JmWisdomAnswers obj){
		List<JmWisdomAnswers> list = mapper.findList(obj);
		if(list != null && list.size() > 0) {
			JmWisdomUserQuestions jmWisdomUserQuestions = new JmWisdomUserQuestions();
			for(JmWisdomAnswers answers : list) {
				if(answers != null && StringUtils.isNotBlank(answers.getUserId())) {
					JmAppUser user = null;
					user = cacheUtils.getUserCache().get(answers.getUserId());//Map传入空key会报空指针
					//如果缓存没有，降级去远程调用，这个调用不能缓存，必定要获取最新
					if(user == null) {
						user = findUser(answers.getUserId());
					}
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
						JmAppUser user = null;
						user = cacheUtils.getUserCache().get(answers.getUserId());//Map传入空key会报空指针
						//如果缓存没有，降级去远程调用，这个调用不能缓存，必定要获取最新
						if(user == null) {
							user = findUser(answers.getUserId());
						}
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
		if(answers != null && StringUtils.isNotBlank(answers.getUserId()) ) {
			JmAppUser user = null;
			user = cacheUtils.getUserCache().get(answers.getUserId());//Map传入空key会报空指针
			//如果缓存没有，降级去远程调用，这个调用不能缓存，必定要获取最新
			if(user == null) {
				user = findUser(answers.getUserId());
			}
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

	//新增接口：根据问题id数组查询问题第一条回答详情的集合
	public Map<String,JmWisdomAnswers> findFirstAnswers(String[] questionIds){
		Map<String,JmWisdomAnswers> answersMap = new HashMap<>();
		if(questionIds != null && questionIds.length > 0) {
			JmWisdomQuestions question = new JmWisdomQuestions();
			for(String questionId : questionIds) {
				question.setId(questionId);
				JmWisdomAnswers answer = mapper.findFirst(question);
				answersMap.put(questionId,answer);//问题id为key,第一条回答详情为value
			}
		}
		return answersMap;
	}

	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED,readOnly = false)
	public JmWisdomAnswers insert(JmWisdomAnswers obj){
		obj.preInsert();
		int result1 = mapper.insert(obj);
		int result2 = 0;
		//回答对应问题的回答数+1--后台删除回答对应问题回答数-1的功能做另外接口，因后台无法传递questionId而暂未实现
		//插入成功才+1
		if(result1 > 0 && obj != null && StringUtils.isNotBlank(obj.getQuestionId())) {
			JmWisdomQuestions jmWisdomQuestions = new JmWisdomQuestions();
			jmWisdomQuestions.preUpdate();//注意更新
			jmWisdomQuestions.setId(obj.getQuestionId());
			jmWisdomQuestions.setAnswerTag(1);//表示更新回答数
			result2 = jmWisdomQuestionsMapper.update(jmWisdomQuestions);
		}
		if(result1 > 0 && result2 > 0) {
			return obj;
		} else {
			return null;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED,readOnly = false)
	public JmWisdomAnswers update(JmWisdomAnswers obj){
		obj.preUpdate();
		if(mapper.update(obj) > 0) {
			return obj;
		} else {
			return null;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED,readOnly = false)
	public int delete(String id){
		return mapper.delete(id);
	}


	public static void main(String[] args) {
		JmWisdomUserQuestions result = null;
		System.out.println((result != null && result.getYesorno() != null) ? result.getYesorno() : 0);
	}
}
