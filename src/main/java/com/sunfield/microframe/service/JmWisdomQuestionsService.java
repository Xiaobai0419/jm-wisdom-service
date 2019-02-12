package com.sunfield.microframe.service;

import java.util.List;

import com.sunfield.microframe.domain.JmAppUser;
import com.sunfield.microframe.feign.JmAppUserFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codingapi.tx.annotation.ITxTransaction;

import com.sunfield.microframe.common.response.Page;

import com.sunfield.microframe.domain.JmWisdomQuestions;
import com.sunfield.microframe.mapper.JmWisdomQuestionsMapper;

/**
 * jm_wisdom_questions service
 * @author sunfield coder
 */
@Service
public class JmWisdomQuestionsService implements ITxTransaction{

	@Autowired
	private JmWisdomQuestionsMapper mapper;
	@Autowired
	private JmAppUserFeignService jmAppUserFeignService;

	private JmAppUser findUser(String userId) {
		return jmAppUserFeignService.findOne(userId).getData();
	}//userId为null会造成Feign服务调用失败
	
	public List<JmWisdomQuestions> findList(JmWisdomQuestions obj){
		//目前前台列表不需要用户相关信息，只有详情页需要，需要时添加用户信息获取代码即可
		return mapper.findList(obj);
	}
	
	public Page<JmWisdomQuestions> findPage(JmWisdomQuestions obj){
		List<JmWisdomQuestions> totalList = mapper.findList(obj);
		if(!totalList.isEmpty()){
			List<JmWisdomQuestions> pageList = mapper.findPage(obj);
			if(pageList != null && pageList.size() > 0) {
				for(JmWisdomQuestions jmWisdomQuestions : pageList) {
					if(jmWisdomQuestions != null && jmWisdomQuestions.getUserId() != null) {//userId为null会造成Feign服务调用失败
						//远程调用用户服务，获取业务所需用户具体信息，目前用于后台管理列表功能
						JmAppUser user = findUser(jmWisdomQuestions.getUserId());//注意不要写成obj,改善参数命名方式以区分！
						jmWisdomQuestions.setUser(user);
					}
				}
			}
			return new Page<JmWisdomQuestions>(totalList.size(), obj.getPageSize(), obj.getPageNumber(), pageList);
		}else{
			return new Page<JmWisdomQuestions>();
		}
	}
	
	public JmWisdomQuestions findOne(String id){
		JmWisdomQuestions jmWisdomQuestions = mapper.findOne(id);
		if(jmWisdomQuestions != null && jmWisdomQuestions.getUserId() != null) {
			//远程调用用户服务，获取业务所需用户具体信息
			JmAppUser user = findUser(jmWisdomQuestions.getUserId());
			jmWisdomQuestions.setUser(user);
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
	
}
