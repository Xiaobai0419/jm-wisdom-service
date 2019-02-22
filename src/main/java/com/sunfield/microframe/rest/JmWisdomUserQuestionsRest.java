package com.sunfield.microframe.rest;

import java.util.List;

import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.sunfield.microframe.common.response.Page;
import com.sunfield.microframe.common.response.ResponseBean;
import com.sunfield.microframe.common.response.ResponseStatus;

import com.sunfield.microframe.domain.JmWisdomUserQuestions;
import com.sunfield.microframe.fallback.JmWisdomUserQuestionsFallback;
import com.sunfield.microframe.service.JmWisdomUserQuestionsService;

/**
 * jm_wisdom_user_questions rest
 * @author sunfield coder
 */
@Api(tags = "jm-wisdom-user-questions")
@RestController
@RequestMapping(value = "/JmWisdomUserQuestions")
public class JmWisdomUserQuestionsRest extends JmWisdomUserQuestionsFallback{
	
	@Autowired
	private JmWisdomUserQuestionsService service;
	
//	@ApiOperation(value="查询列表")
//	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomUserQuestions")
//	@RequestMapping(value = "/findList", method = RequestMethod.POST)
//	@HystrixCommand(fallbackMethod = "findListFallback")
//    public ResponseBean<List<JmWisdomUserQuestions>> findList(@RequestBody JmWisdomUserQuestions obj) {
//		List<JmWisdomUserQuestions> list = service.findList(obj);
//		if(!list.isEmpty()) {
//			return new ResponseBean<List<JmWisdomUserQuestions>>(ResponseStatus.SUCCESS, list);
//		} else {
//			return new ResponseBean<List<JmWisdomUserQuestions>>(ResponseStatus.NO_DATA);
//		}
//    }
	
//	@ApiOperation(value="分页查询")
//	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomUserQuestions")
//	@RequestMapping(value = "/findPage", method = RequestMethod.POST)
//	@HystrixCommand(fallbackMethod = "findPageFallback")
//    public ResponseBean<Page<JmWisdomUserQuestions>> findPage(@RequestBody JmWisdomUserQuestions obj) {
//    	return new ResponseBean<Page<JmWisdomUserQuestions>>(ResponseStatus.SUCCESS, service.findPage(obj));
//    }
	
//	@ApiOperation(value="查询自身：用户获取自身对各类目标对象的赞、踩、收藏情况，传递type，userId，questionId字段，" +
//			"其他不传，无数据返回代表用户对该对象没有任何赞、踩、收藏")
//	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomUserQuestions")
//	@RequestMapping(value = "/findOne", method = RequestMethod.POST)
//	@HystrixCommand(fallbackMethod = "findOneFallback")
//    public ResponseBean<JmWisdomUserQuestions> findOne(@RequestBody JmWisdomUserQuestions obj) {
//    	JmWisdomUserQuestions object = service.findOne(obj);
//    	if(object != null) {
//    		return new ResponseBean<JmWisdomUserQuestions>(ResponseStatus.SUCCESS, object);
//    	} else {
//    		return new ResponseBean<JmWisdomUserQuestions>(ResponseStatus.NO_DATA);
//		}
//    }
	
	@ApiOperation(value="新增：用户对各类目标对象的赞、踩、收藏，传递type，userId，questionId，yesorno字段，" +
			"其中统一的部分：questionId为业务对象ID,赞、收藏时，yesorno字段传1，踩时，yesorno字段传2，" +
			"业务区分部分：角马问题，type传1；角马问题回答，type传2；角马访谈收藏，type传3；" +
			"角马访谈视频点赞，type传4。用户取消赞、踩、收藏请访问删除接口。用户没有任何赞、踩、收藏请不要调用。")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomUserQuestions")
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "insertFallback")
    public ResponseBean<JmWisdomUserQuestions> insert(@RequestBody JmWisdomUserQuestions obj) {
		JmWisdomUserQuestions object = service.insert(obj);
		if(object != null) {
			return new ResponseBean<JmWisdomUserQuestions>(ResponseStatus.SUCCESS, object);
		} else {
			return new ResponseBean<JmWisdomUserQuestions>(ResponseStatus.FAIL);
		}
    }

    //禁用更新接口
//	@ApiOperation(value="更新")
//	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomUserQuestions")
//	@RequestMapping(value = "/update", method = RequestMethod.POST)
//	@HystrixCommand(fallbackMethod = "updateFallback")
//    public ResponseBean<JmWisdomUserQuestions> update(@RequestBody JmWisdomUserQuestions obj) {
//    	if(StringUtils.isBlank(obj.getId())) {
//			return new ResponseBean<JmWisdomUserQuestions>(ResponseStatus.PARAMS_ERROR);
//    	}
//    	JmWisdomUserQuestions object = service.update(obj);
//    	if(object != null) {
//			return new ResponseBean<JmWisdomUserQuestions>(ResponseStatus.SUCCESS, object);
//		} else {
//			return new ResponseBean<JmWisdomUserQuestions>(ResponseStatus.FAIL);
//		}
//    }
	
	@ApiOperation(value="删除：用户取消赞、踩、收藏，传递type，userId，questionId，yesorno字段，" +
			"其中统一的部分：questionId为业务对象ID,取消赞、收藏时，yesorno字段传1，取消踩时，yesorno字段传2，" +
			"业务区分部分：角马问题，type传1；角马问题回答，type传2；角马访谈收藏，type传3；" +
			"角马访谈视频点赞，type传4。")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomUserQuestions")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "deleteFallback")
    public ResponseBean<JmWisdomUserQuestions> delete(@RequestBody JmWisdomUserQuestions obj) {
    	if(service.delete(obj) > 0) {
    		return new ResponseBean<JmWisdomUserQuestions>();
    	} else {
    		return new ResponseBean<JmWisdomUserQuestions>(ResponseStatus.NO_DATA);
		}
    }
    
}
