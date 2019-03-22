package com.sunfield.microframe.service;

import java.util.Date;
import java.util.List;

import com.sunfield.microframe.common.utils.CacheUtils;
import com.sunfield.microframe.domain.*;
import com.sunfield.microframe.feign.JmAppUserFeignService;
import com.sunfield.microframe.feign.JmIndustriesFeignService;
import com.sunfield.microframe.mapper.JmWisdomAnswersMapper;
import com.sunfield.microframe.mapper.JmWisdomUserQuestionsMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codingapi.tx.annotation.ITxTransaction;

import com.sunfield.microframe.common.response.Page;

import com.sunfield.microframe.mapper.JmWisdomQuestionsMapper;

/**
 * jm_wisdom_questions service
 * @author sunfield coder
 */
@Slf4j
@Service
public class JmWisdomQuestionsService implements ITxTransaction{

	@Autowired
	private JmWisdomQuestionsMapper mapper;
	@Autowired
	private JmWisdomAnswersMapper answersMapper;
	@Autowired
	private JmWisdomUserQuestionsMapper jmWisdomUserQuestionsMapper;
	@Autowired
	@Qualifier("jmAppUserFeignService")
	private JmAppUserFeignService jmAppUserFeignService;
	@Autowired
	@Qualifier("jmIndustriesFeignService")
	private JmIndustriesFeignService jmIndustriesFeignService;
	@Autowired
	private CacheUtils cacheUtils;

	//实时查最新，不能缓存
	private JmAppUser findUser(String userId) {
		return jmAppUserFeignService.findOne(userId).getData();
	}//userId为null会造成Feign服务调用失败

	//实时查最新，不能缓存
	private JmIndustries findIndustry(JmIndustries industry) {
		return jmIndustriesFeignService.findOne(industry).getData();
	}

	public List<JmWisdomQuestions> findList(JmWisdomQuestions obj){
		//目前前台精品排序不需要用户相关信息，只有详情页需要，需要时添加用户信息获取代码即可
		return mapper.findList(obj);
	}

	public Page<JmWisdomQuestions> findPage(JmWisdomQuestions obj){
		List<JmWisdomQuestions> totalList = mapper.findList(obj);
		if(!totalList.isEmpty()){
			List<JmWisdomQuestions> pageList = mapper.findPage(obj);
			if(pageList != null && pageList.size() > 0) {
				JmWisdomUserQuestions jmWisdomUserQuestions = new JmWisdomUserQuestions();
				for(JmWisdomQuestions jmWisdomQuestions : pageList) {
					//问题的（综合排序）第一条回答
					if(jmWisdomQuestions != null && StringUtils.isNotBlank(jmWisdomQuestions.getId())) {
						JmWisdomAnswers firstAnswer = answersMapper.findFirst(jmWisdomQuestions);
						jmWisdomQuestions.setFirstAnswer(firstAnswer);
					}
					//新增查询：访问用户对该问题的踩赞状态
					if(jmWisdomQuestions != null && StringUtils.isNotBlank(obj.getVisitUserId())) {//注意是传入对象的访问用户id,必须判断，后台管理或未登录用户可以不传递访问者id
						jmWisdomUserQuestions.setType(1);
						jmWisdomUserQuestions.setQuestionId(jmWisdomQuestions.getId());
						jmWisdomUserQuestions.setUserId(obj.getVisitUserId());//注意是传入对象的访问用户id
						JmWisdomUserQuestions result = jmWisdomUserQuestionsMapper.findOne(jmWisdomUserQuestions);
						jmWisdomQuestions.setVisitUserYesOrNo((result != null && result.getYesorno() != null) ? result.getYesorno() : 0);
					}
					JmAppUser user = null;
					if(StringUtils.isNotBlank(jmWisdomQuestions.getUserId())) {
						user = cacheUtils.getUserCache().get(jmWisdomQuestions.getUserId());//Map传入空key会报空指针
						//如果缓存没有，降级去远程调用，这个调用不能缓存，必定要获取最新
						if(user == null) {
							user = findUser(jmWisdomQuestions.getUserId());
						}
					}
					jmWisdomQuestions.setUser(user);
					JmIndustries industries = null;
					if(StringUtils.isNotBlank(jmWisdomQuestions.getIndustryId())) {
						industries = cacheUtils.getIndustriesCache().get(jmWisdomQuestions.getIndustryId());
						if(industries == null) {
							JmIndustries industriesInput =new JmIndustries();
							industriesInput.setId(jmWisdomQuestions.getIndustryId());
							industries = findIndustry(industriesInput);
						}
					}
					jmWisdomQuestions.setIndustry(industries);
				}
			}
			return new Page<JmWisdomQuestions>(totalList.size(), obj.getPageSize(), obj.getPageNumber(), pageList);
		}else{
			return new Page<JmWisdomQuestions>();
		}
	}

	public Page<JmWisdomQuestions> findByUserIdsPage(String[] userIds, Date dateStart, Date dateEnd,
													 Integer pageNumber, Integer pageSize){
		List<JmWisdomQuestions> totalList = mapper.findByUserIds(userIds,dateStart, dateEnd);
		if(!totalList.isEmpty()){
			List<JmWisdomQuestions> pageList = mapper.findByUserIdsPage(userIds, dateStart, dateEnd,pageNumber, pageSize);
			if(pageList != null && pageList.size() > 0) {
				JmWisdomUserQuestions jmWisdomUserQuestions = new JmWisdomUserQuestions();
				for(JmWisdomQuestions jmWisdomQuestions : pageList) {
					JmAppUser user = null;
					if(StringUtils.isNotBlank(jmWisdomQuestions.getUserId())) {
						user = cacheUtils.getUserCache().get(jmWisdomQuestions.getUserId());//Map传入空key会报空指针
						//如果缓存没有，降级去远程调用，这个调用不能缓存，必定要获取最新
						if(user == null) {
							user = findUser(jmWisdomQuestions.getUserId());
						}
					}
					jmWisdomQuestions.setUser(user);
					JmIndustries industries = null;
					if(StringUtils.isNotBlank(jmWisdomQuestions.getIndustryId())) {
						industries = cacheUtils.getIndustriesCache().get(jmWisdomQuestions.getIndustryId());
						if(industries == null) {
							JmIndustries industriesInput =new JmIndustries();
							industriesInput.setId(jmWisdomQuestions.getIndustryId());
							industries = findIndustry(industriesInput);
						}
					}
					jmWisdomQuestions.setIndustry(industries);
				}
			}
			return new Page<JmWisdomQuestions>(totalList.size(), pageSize, pageNumber, pageList);
		}else{
			return new Page<JmWisdomQuestions>();
		}
	}

	public JmWisdomQuestions findOne(JmWisdomQuestions questions){
		JmWisdomQuestions jmWisdomQuestions = mapper.findOne(questions.getId());
		if(jmWisdomQuestions != null && StringUtils.isNotBlank(jmWisdomQuestions.getUserId())) {
			JmAppUser user = cacheUtils.getUserCache().get(jmWisdomQuestions.getUserId());//Map传入空key会报空指针
			//如果缓存没有，降级去远程调用
			if(user == null) {
				user = findUser(jmWisdomQuestions.getUserId());
			}
			jmWisdomQuestions.setUser(user);
		}
		if(jmWisdomQuestions != null && StringUtils.isNotBlank(jmWisdomQuestions.getIndustryId())) {
			JmIndustries industries = cacheUtils.getIndustriesCache().get(jmWisdomQuestions.getIndustryId());
			if(industries == null) {
				JmIndustries industriesInput =new JmIndustries();
				industriesInput.setId(jmWisdomQuestions.getIndustryId());
				industries = findIndustry(industriesInput);
			}
			jmWisdomQuestions.setIndustry(industries);
		}
		JmWisdomUserQuestions jmWisdomUserQuestions = new JmWisdomUserQuestions();
		//新增查询：访问用户对该问题的踩赞状态
		if(jmWisdomQuestions != null && StringUtils.isNotBlank(questions.getVisitUserId())) {//注意是传入对象的访问用户id,必须判断，后台管理或未登录用户可以不传递访问者id
			jmWisdomUserQuestions.setType(1);
			jmWisdomUserQuestions.setQuestionId(jmWisdomQuestions.getId());
			jmWisdomUserQuestions.setUserId(questions.getVisitUserId());//注意是传入对象的访问用户id
			JmWisdomUserQuestions result = jmWisdomUserQuestionsMapper.findOne(jmWisdomUserQuestions);
			jmWisdomQuestions.setVisitUserYesOrNo((result != null && result.getYesorno() != null) ? result.getYesorno() : 0);
		}
		return jmWisdomQuestions;
	}
	
	@Transactional
	public JmWisdomQuestions insert(JmWisdomQuestions obj){
		obj.preInsert();
		if(mapper.insert(obj) > 0) {
			return obj;
		} else {
			return null;
		}
	}
	
	@Transactional
	public JmWisdomQuestions update(JmWisdomQuestions obj){
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
//		log.info(Thread.currentThread().getName() + ":Main Thread Begin->" + System.currentTimeMillis());
		//上一个任务开始后的delay时间开始执行，但如果上一个任务超时，后面的任务会延后，不会并发
//		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable() {
//			@Override
//			public void run() {
//				log.info(System.currentTimeMillis() + "-Hi,I'm another call!");
//				try {
//					Thread.sleep(5000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		},2000,2000, TimeUnit.MILLISECONDS);
		//上一个任务完成后的delay时间开始执行
//		Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(new Runnable() {
//			@Override
//			public void run() {
//				log.info(Thread.currentThread().getName() + ":" + System.currentTimeMillis() + "-Hi,I'm another call!");
//				try {
//					Thread.sleep(5000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		},2000,2000, TimeUnit.MILLISECONDS);
//		log.info(Thread.currentThread().getName() + ":Main Thread End->" + System.currentTimeMillis());
//		Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(new Runnable() {
//			@Override
//			public void run() {
//				log.info(Thread.currentThread().getName() + ":" + System.currentTimeMillis() + "-Hi,I'm another call!");
//				try {
//					for(Integer i=0;i < 100;i++) {
//						userCache.put(i+"",new JmAppUser());
//					}
//					for(Integer i=0;i < 100;i++) {
//						industriesCache.put(i+"",new JmIndustries());
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				log.info(Thread.currentThread().getName() + ":" + userCache.get("59") + "/" + industriesCache.get("84"));
//			}
//		},2000,2000, TimeUnit.MILLISECONDS);
	}
}
