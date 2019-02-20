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

import com.sunfield.microframe.domain.JmWisdomVideos;
import com.sunfield.microframe.mapper.JmWisdomVideosMapper;

/**
 * jm_wisdom_videos service
 * @author sunfield coder
 */
@Service
public class JmWisdomVideosService implements ITxTransaction{

	@Autowired
	private JmWisdomVideosMapper mapper;
	@Autowired
	private JmWisdomUserQuestionsMapper jmWisdomUserQuestionsMapper;
	
	public List<JmWisdomVideos> findList(JmWisdomVideos obj){
		return mapper.findList(obj);
	}
	
	public Page<JmWisdomVideos> findPage(JmWisdomVideos obj){
		List<JmWisdomVideos> totalList = mapper.findList(obj);
		if(!totalList.isEmpty()){
			List<JmWisdomVideos> pageList = mapper.findPage(obj);
			return new Page<JmWisdomVideos>(totalList.size(), obj.getPageSize(), obj.getPageNumber(), pageList);
		}else{
			return new Page<JmWisdomVideos>();
		}
	}
	
	public JmWisdomVideos findOne(JmWisdomVideos videos){

		JmWisdomVideos jmWisdomVideos = mapper.findOne(videos.getId());
		JmWisdomUserQuestions jmWisdomUserQuestions = new JmWisdomUserQuestions();
		//新增查询：访问用户对该视频的赞状态
		if(jmWisdomVideos != null && StringUtils.isNotBlank(videos.getVisitUserId())) {//注意是传入对象的访问用户id,必须判断，后台管理或未登录用户可以不传递访问者id
			jmWisdomUserQuestions.setType(4);
			jmWisdomUserQuestions.setQuestionId(jmWisdomVideos.getId());
			jmWisdomUserQuestions.setUserId(videos.getVisitUserId());//注意是传入对象的访问用户id
			JmWisdomUserQuestions result = jmWisdomUserQuestionsMapper.findOne(jmWisdomUserQuestions);
			jmWisdomVideos.setVisitUserYesOrNo((result != null && result.getYesorno() != null) ? result.getYesorno() : 0);
		}
		return jmWisdomVideos;
	}
	
	@Transactional
	public JmWisdomVideos insert(JmWisdomVideos obj){
		obj.preInsert();
		if(mapper.insert(obj) > 0) {
			return obj;
		} else {
			return null;
		}
	}
	
	@Transactional
	public JmWisdomVideos update(JmWisdomVideos obj){
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
