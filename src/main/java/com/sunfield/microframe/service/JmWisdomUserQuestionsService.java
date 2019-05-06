package com.sunfield.microframe.service;

import java.util.ArrayList;
import java.util.List;

import com.sunfield.microframe.common.utils.PageUtils;
import com.sunfield.microframe.domain.*;
import com.sunfield.microframe.mapper.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codingapi.tx.annotation.ITxTransaction;

import com.sunfield.microframe.common.response.Page;

/**
 * jm_wisdom_user_questions service
 * @author sunfield coder
 */
@Service
public class JmWisdomUserQuestionsService implements ITxTransaction{

	@Autowired
	private JmWisdomUserQuestionsMapper mapper;
	@Autowired
	private JmWisdomQuestionsMapper jmWisdomQuestionsMapper;
	@Autowired
	private JmWisdomAnswersMapper jmWisdomAnswersMapper;
	@Autowired
	private JmWisdomInterviewsMapper jmWisdomInterviewsMapper;
	@Autowired
	private JmWisdomVideosMapper jmWisdomVideosMapper;
	
//	public List<JmWisdomUserQuestions> findList(JmWisdomUserQuestions obj){
//		return mapper.findList(obj);
//	}
//
//	public Page<JmWisdomUserQuestions> findPage(JmWisdomUserQuestions obj){
//		List<JmWisdomUserQuestions> totalList = mapper.findList(obj);
//		if(!totalList.isEmpty()){
//			List<JmWisdomUserQuestions> pageList = mapper.findPage(obj);
//			return new Page<JmWisdomUserQuestions>(totalList.size(), obj.getPageSize(), obj.getPageNumber(), pageList);
//		}else{
//			return new Page<JmWisdomUserQuestions>();
//		}
//	}
	
//	public JmWisdomUserQuestions findOne(String id){
//		return mapper.findOne(id);
//	}

	//用户获取自身对各类目标对象的赞、踩、收藏情况
	public JmWisdomUserQuestions findOne(JmWisdomUserQuestions obj){
		return mapper.findOne(obj);
	}

	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED,readOnly = false)
	public JmWisdomUserQuestions insert(JmWisdomUserQuestions obj){
		obj.preInsert();
		int result1 = mapper.insert(obj);
		int result2 = 0;
		//根据对不同对象的赞、踩、收藏情况，将对应对象记录中赞、踩、收藏数+1
		//插入成功才+1
		if(result1 > 0 && obj != null && StringUtils.isNotBlank(obj.getQuestionId()) && obj.getType() != null
				&& obj.getYesorno() != null) {
			String questionId = obj.getQuestionId();
			int yesorno = obj.getYesorno();
			switch (obj.getType()) {
				case 1:
					JmWisdomQuestions jmWisdomQuestions = new JmWisdomQuestions();
					jmWisdomQuestions.setId(questionId);
					jmWisdomQuestions.preUpdate();
					switch(yesorno) {
						case 1://赞/收藏
							jmWisdomQuestions.setAyesTag(1);//代表赞数+1
							result2 = jmWisdomQuestionsMapper.update(jmWisdomQuestions);
							break;
						case 2://踩
							jmWisdomQuestions.setAntisTag(1);//代表踩数+1
							result2 = jmWisdomQuestionsMapper.update(jmWisdomQuestions);
							break;
						default:
							;
					}
					break;
				case 2:
					JmWisdomAnswers jmWisdomAnswers = new JmWisdomAnswers();
					jmWisdomAnswers.setId(questionId);
					jmWisdomAnswers.preUpdate();
					switch(yesorno) {
						case 1://赞/收藏
							jmWisdomAnswers.setAyesTag(1);//代表赞数+1
							result2 = jmWisdomAnswersMapper.update(jmWisdomAnswers);
							break;
						case 2://踩
							jmWisdomAnswers.setAntisTag(1);//代表踩数+1
							result2 = jmWisdomAnswersMapper.update(jmWisdomAnswers);
							break;
						default:
							;
					}
					break;
				case 3:
					JmWisdomInterviews jmWisdomInterviews = new JmWisdomInterviews();
					jmWisdomInterviews.setId(questionId);
					jmWisdomInterviews.preUpdate();
					//收藏访谈
					if(yesorno == 1) {
						jmWisdomInterviews.setFavoriteTag(1);
						result2 = jmWisdomInterviewsMapper.update(jmWisdomInterviews);
					}
					break;
				case 4:
					JmWisdomVideos jmWisdomVideos = new JmWisdomVideos();
					jmWisdomVideos.setId(questionId);
					jmWisdomVideos.preUpdate();
					//赞视频
					if(yesorno == 1) {
						jmWisdomVideos.setAyesTag(1);
						result2 = jmWisdomVideosMapper.update(jmWisdomVideos);
					}
					break;
				default:
					;
			}
		}
		if(result1 > 0 && result2 > 0) {
			return obj;
		} else {
			return null;
		}
	}
	
//	@Transactional
//	public JmWisdomUserQuestions update(JmWisdomUserQuestions obj){
//		obj.preUpdate();
//		if(mapper.update(obj) > 0) {
//			return obj;
//		} else {
//			return null;
//		}
//	}
	
//	@Transactional
//	public int delete(String id){
//		return mapper.delete(id);
//	}

	//用户取消踩/赞/收藏时，进行逻辑删除
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED,readOnly = false)
	public int delete(JmWisdomUserQuestions obj){
		int result1 = mapper.delete(obj);
		int result2 = 0;
		//根据对不同对象的赞、踩、收藏情况，将对应对象记录中赞、踩、收藏数减1
		//有成功删除记录，才进行对应减1，防止出现没有删除对应记录而减1错误，比如该用户没有踩赞记录，删0条，而对应踩赞数误减1
		if(result1 > 0 && obj != null && StringUtils.isNotBlank(obj.getQuestionId()) && obj.getType() != null
				&& obj.getYesorno() != null) {
			String questionId = obj.getQuestionId();
			int yesorno = obj.getYesorno();
			switch (obj.getType()) {
				case 1:
					JmWisdomQuestions jmWisdomQuestions = new JmWisdomQuestions();
					jmWisdomQuestions.setId(questionId);
					jmWisdomQuestions.preUpdate();
					switch(yesorno) {
						case 1://赞/收藏
							jmWisdomQuestions.setAyesTag(1);//代表赞数-1
							result2 = jmWisdomQuestionsMapper.updateCancel(jmWisdomQuestions);
							break;
						case 2://踩
							jmWisdomQuestions.setAntisTag(1);//代表踩数-1
							result2 = jmWisdomQuestionsMapper.updateCancel(jmWisdomQuestions);
							break;
						default:
							;
					}
					break;
				case 2:
					JmWisdomAnswers jmWisdomAnswers = new JmWisdomAnswers();
					jmWisdomAnswers.setId(questionId);
					jmWisdomAnswers.preUpdate();
					switch(yesorno) {
						case 1://赞/收藏
							jmWisdomAnswers.setAyesTag(1);//代表赞数-1
							result2 = jmWisdomAnswersMapper.updateCancel(jmWisdomAnswers);
							break;
						case 2://踩
							jmWisdomAnswers.setAntisTag(1);//代表踩数-1
							result2 = jmWisdomAnswersMapper.updateCancel(jmWisdomAnswers);
							break;
						default:
							;
					}
					break;
				case 3:
					JmWisdomInterviews jmWisdomInterviews = new JmWisdomInterviews();
					jmWisdomInterviews.setId(questionId);
					jmWisdomInterviews.preUpdate();
					//取消收藏访谈
					if(yesorno == 1) {
						jmWisdomInterviews.setFavoriteTag(1);
						result2 = jmWisdomInterviewsMapper.updateCancel(jmWisdomInterviews);
					}
					break;
				case 4:
					JmWisdomVideos jmWisdomVideos = new JmWisdomVideos();
					jmWisdomVideos.setId(questionId);
					jmWisdomVideos.preUpdate();
					//取消赞视频
					if(yesorno == 1) {
						jmWisdomVideos.setAyesTag(1);
						result2 = jmWisdomVideosMapper.updateCancel(jmWisdomVideos);
					}
					break;
				default:
					;
			}
		}
		return (result1 > 0 && result2 > 0) ? 1:0;
	}

	//个人收藏的访谈列表
	public List<JmWisdomInterviews> findOnesInterviewsList(JmWisdomUserQuestions jmWisdomUserQuestions) {
		//类型3为访谈
		if(StringUtils.isNotBlank(jmWisdomUserQuestions.getUserId()) && jmWisdomUserQuestions.getType() == 3) {
			List<JmWisdomUserQuestions> userQuestions = mapper.findList(jmWisdomUserQuestions);
			if(userQuestions != null && userQuestions.size() > 0) {
				List<String> interviewIds = new ArrayList<>();
				for(JmWisdomUserQuestions userQuestion : userQuestions) {
					interviewIds.add(userQuestion.getQuestionId());
				}
				List<JmWisdomInterviews> interviews = jmWisdomInterviewsMapper.findListByIds(interviewIds.toArray(new String[interviewIds.size()]));
				return interviews;
			}
		}
		return new ArrayList<>();//避免返回null
	}

	//个人收藏的访谈列表--分页
	public Page<JmWisdomInterviews> findOnesInterviewsPage(JmWisdomUserQuestions jmWisdomUserQuestions) {
		//类型3为访谈
		int count = 0;
		if(StringUtils.isNotBlank(jmWisdomUserQuestions.getUserId()) && jmWisdomUserQuestions.getType() == 3) {
			List<JmWisdomInterviews> onesInterviews = findOnesInterviewsList(jmWisdomUserQuestions);
			count = onesInterviews.size();//应为该用户收藏的访谈表中实际存在的访谈数目
			if(count > 0) {
				//按该用户收藏的访谈表中实际存在的访谈，在应用层分页
				return PageUtils.pageList(onesInterviews,jmWisdomUserQuestions.getPageNumber(),
						jmWisdomUserQuestions.getPageSize());
			}
		}
		return new Page<>(count, jmWisdomUserQuestions.getPageSize(),
				jmWisdomUserQuestions.getPageNumber(),new ArrayList<>());//避免返回null
	}
}
