package com.sunfield.microframe.service;

import java.util.List;
import java.util.concurrent.*;

import com.sunfield.microframe.domain.*;
import com.sunfield.microframe.feign.JmAppUserFeignService;
import com.sunfield.microframe.feign.JmIndustriesFeignService;
import com.sunfield.microframe.mapper.JmWisdomAnswersMapper;
import com.sunfield.microframe.mapper.JmWisdomUserQuestionsMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
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

	//应用层缓存--用户量大的情形下要移除到Redis集群中，防止应用层内存溢出;每次从Redis获取数据（但又变成了网络连接）;使用单独定时任务工程查询所有用户更新到Redis缓存
	private ConcurrentMap<String,JmAppUser> userCache = new ConcurrentHashMap<>();
	private ConcurrentMap<String,JmIndustries> industriesCache = new ConcurrentHashMap<>();

	public ConcurrentMap<String, JmAppUser> getUserCache() {
		return userCache;
	}

	public ConcurrentMap<String, JmIndustries> getIndustriesCache() {
		return industriesCache;
	}

	//定时任务定期查询数据更新应用层缓存，用于一定时间后重新获取新数据--这时保证单例和单例实例化的同步操作就非常有必要
	public JmWisdomQuestionsService() {
		Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					List<JmAppUser> users = findUsers();//远程调用有超时、熔断设置，且这里是另起一个线程异步，不用担心因容器初始化Bean未完成导致的服务长期起不来
					List<JmIndustries> industries = findIndustries();
					if(users != null && users.size() > 0) {
						for(JmAppUser user : users) {
							userCache.put(user.getId(),user);//无论是否并发，操作ConcurrentMap是安全的，能不同步尽量不同步，无论整体存入是否完成，数据获取者只是并发获取其中的一些数据而已，上一次有旧数据未被覆盖，可用，如果没有，降级，自己去远程调用
						}
					}
					if(industries != null && industries.size() > 0) {
						for(JmIndustries jmIndustries : industries) {
							industriesCache.put(jmIndustries.getId(),jmIndustries);
						}
					}
					log.info(Thread.currentThread().getName() + "->userCache and industryCache updated!");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		},2000,20000, TimeUnit.MILLISECONDS);
	}

	//实时查最新，不能缓存
	private JmAppUser findUser(String userId) {
		return jmAppUserFeignService.findOne(userId).getData();
	}//userId为null会造成Feign服务调用失败

	//实时查最新，不能缓存
	private JmIndustries findIndustry(JmIndustries industry) {
		return jmIndustriesFeignService.findOne(industry).getData();
	}

	//缓存所有用户--改为定时任务查询最新存入应用层缓存，这里要最新数据，不能缓存
//	@Cacheable(value = "jm_wisdom_user_cache",key = "'jm_wisdom_user_cache'")
	public List<JmAppUser> findUsers() {
		return jmAppUserFeignService.findList().getData();
	}

	//缓存所有行业--改为定时任务查询最新存入应用层缓存，这里要最新数据，不能缓存
//	@Cacheable(value = "jm_wisdom_industry_cache",key = "'jm_wisdom_industry_cache'")
	public List<JmIndustries> findIndustries() {
		return jmIndustriesFeignService.findList(new JmIndustries()).getData();
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
						user = userCache.get(jmWisdomQuestions.getUserId());//Map传入空key会报空指针
						//如果缓存没有，降级去远程调用，这个调用不能缓存，必定要获取最新
						if(user == null) {
							user = findUser(jmWisdomQuestions.getUserId());
						}
					}
					jmWisdomQuestions.setUser(user);
					JmIndustries industries = null;
					if(StringUtils.isNotBlank(jmWisdomQuestions.getIndustryId())) {
						industries = industriesCache.get(jmWisdomQuestions.getIndustryId());
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
	
	public JmWisdomQuestions findOne(JmWisdomQuestions questions){
		JmWisdomQuestions jmWisdomQuestions = mapper.findOne(questions.getId());
		if(jmWisdomQuestions != null && StringUtils.isNotBlank(jmWisdomQuestions.getUserId())) {
			JmAppUser user = userCache.get(jmWisdomQuestions.getUserId());//Map传入空key会报空指针
			//如果缓存没有，降级去远程调用
			if(user == null) {
				user = findUser(jmWisdomQuestions.getUserId());
			}
			jmWisdomQuestions.setUser(user);
		}
		if(jmWisdomQuestions != null && StringUtils.isNotBlank(jmWisdomQuestions.getIndustryId())) {
			JmIndustries industries = industriesCache.get(jmWisdomQuestions.getIndustryId());
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
