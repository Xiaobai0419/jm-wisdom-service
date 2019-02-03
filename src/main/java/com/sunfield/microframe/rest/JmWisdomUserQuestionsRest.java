package com.sunfield.microframe.rest;

import java.util.List;

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
@RestController
@RequestMapping(value = "/JmWisdomUserQuestions")
public class JmWisdomUserQuestionsRest extends JmWisdomUserQuestionsFallback{
	
	@Autowired
	private JmWisdomUserQuestionsService service;
	
	@ApiOperation(value="查询列表")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomUserQuestions")
	@RequestMapping(value = "/findList", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "findListFallback")
    public ResponseBean<List<JmWisdomUserQuestions>> findList(@RequestBody JmWisdomUserQuestions obj) {
		List<JmWisdomUserQuestions> list = service.findList(obj);
		if(!list.isEmpty()) {
			return new ResponseBean<List<JmWisdomUserQuestions>>(ResponseStatus.SUCCESS, list);
		} else {
			return new ResponseBean<List<JmWisdomUserQuestions>>(ResponseStatus.NO_DATA);
		}
    }
	
	@ApiOperation(value="分页查询")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomUserQuestions")
	@RequestMapping(value = "/findPage", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "findPageFallback")
    public ResponseBean<Page<JmWisdomUserQuestions>> findPage(@RequestBody JmWisdomUserQuestions obj) {
    	return new ResponseBean<Page<JmWisdomUserQuestions>>(ResponseStatus.SUCCESS, service.findPage(obj));
    }
	
	@ApiOperation(value="根据主键查询")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomUserQuestions")
	@RequestMapping(value = "/findOne", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "findOneFallback")
    public ResponseBean<JmWisdomUserQuestions> findOne(@RequestBody JmWisdomUserQuestions obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new ResponseBean<JmWisdomUserQuestions>(ResponseStatus.PARAMS_ERROR);
    	}
    	JmWisdomUserQuestions object = service.findOne(obj.getId());
    	if(object != null) {
    		return new ResponseBean<JmWisdomUserQuestions>(ResponseStatus.SUCCESS, object);
    	} else {
    		return new ResponseBean<JmWisdomUserQuestions>(ResponseStatus.NO_DATA);
		}
    }
	
	@ApiOperation(value="新增")
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
	
	@ApiOperation(value="更新")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomUserQuestions")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "updateFallback")
    public ResponseBean<JmWisdomUserQuestions> update(@RequestBody JmWisdomUserQuestions obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new ResponseBean<JmWisdomUserQuestions>(ResponseStatus.PARAMS_ERROR);
    	}
    	JmWisdomUserQuestions object = service.update(obj);
    	if(object != null) {
			return new ResponseBean<JmWisdomUserQuestions>(ResponseStatus.SUCCESS, object);
		} else {
			return new ResponseBean<JmWisdomUserQuestions>(ResponseStatus.FAIL);
		}
    }
	
	@ApiOperation(value="删除")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomUserQuestions")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "deleteFallback")
    public ResponseBean<JmWisdomUserQuestions> delete(@RequestBody JmWisdomUserQuestions obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new ResponseBean<JmWisdomUserQuestions>(ResponseStatus.PARAMS_ERROR);
    	}
    	if(service.delete(obj.getId()) > 0) {
    		return new ResponseBean<JmWisdomUserQuestions>();
    	} else {
    		return new ResponseBean<JmWisdomUserQuestions>(ResponseStatus.NO_DATA);
		}
    }
    
}
