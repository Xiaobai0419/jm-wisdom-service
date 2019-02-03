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

import com.sunfield.microframe.domain.JmWisdomQuestions;
import com.sunfield.microframe.fallback.JmWisdomQuestionsFallback;
import com.sunfield.microframe.service.JmWisdomQuestionsService;

/**
 * jm_wisdom_questions rest
 * @author sunfield coder
 */
@RestController
@RequestMapping(value = "/JmWisdomQuestions")
public class JmWisdomQuestionsRest extends JmWisdomQuestionsFallback{
	
	@Autowired
	private JmWisdomQuestionsService service;
	
	@ApiOperation(value="查询列表")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomQuestions")
	@RequestMapping(value = "/findList", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "findListFallback")
    public ResponseBean<List<JmWisdomQuestions>> findList(@RequestBody JmWisdomQuestions obj) {
		List<JmWisdomQuestions> list = service.findList(obj);
		if(!list.isEmpty()) {
			return new ResponseBean<List<JmWisdomQuestions>>(ResponseStatus.SUCCESS, list);
		} else {
			return new ResponseBean<List<JmWisdomQuestions>>(ResponseStatus.NO_DATA);
		}
    }
	
	@ApiOperation(value="分页查询")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomQuestions")
	@RequestMapping(value = "/findPage", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "findPageFallback")
    public ResponseBean<Page<JmWisdomQuestions>> findPage(@RequestBody JmWisdomQuestions obj) {
    	return new ResponseBean<Page<JmWisdomQuestions>>(ResponseStatus.SUCCESS, service.findPage(obj));
    }
	
	@ApiOperation(value="根据主键查询")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomQuestions")
	@RequestMapping(value = "/findOne", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "findOneFallback")
    public ResponseBean<JmWisdomQuestions> findOne(@RequestBody JmWisdomQuestions obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new ResponseBean<JmWisdomQuestions>(ResponseStatus.PARAMS_ERROR);
    	}
    	JmWisdomQuestions object = service.findOne(obj.getId());
    	if(object != null) {
    		return new ResponseBean<JmWisdomQuestions>(ResponseStatus.SUCCESS, object);
    	} else {
    		return new ResponseBean<JmWisdomQuestions>(ResponseStatus.NO_DATA);
		}
    }
	
	@ApiOperation(value="新增")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomQuestions")
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "insertFallback")
    public ResponseBean<JmWisdomQuestions> insert(@RequestBody JmWisdomQuestions obj) {
		JmWisdomQuestions object = service.insert(obj);
		if(object != null) {
			return new ResponseBean<JmWisdomQuestions>(ResponseStatus.SUCCESS, object);
		} else {
			return new ResponseBean<JmWisdomQuestions>(ResponseStatus.FAIL);
		}
    }
	
	@ApiOperation(value="更新")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomQuestions")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "updateFallback")
    public ResponseBean<JmWisdomQuestions> update(@RequestBody JmWisdomQuestions obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new ResponseBean<JmWisdomQuestions>(ResponseStatus.PARAMS_ERROR);
    	}
    	JmWisdomQuestions object = service.update(obj);
    	if(object != null) {
			return new ResponseBean<JmWisdomQuestions>(ResponseStatus.SUCCESS, object);
		} else {
			return new ResponseBean<JmWisdomQuestions>(ResponseStatus.FAIL);
		}
    }
	
	@ApiOperation(value="删除")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomQuestions")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "deleteFallback")
    public ResponseBean<JmWisdomQuestions> delete(@RequestBody JmWisdomQuestions obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new ResponseBean<JmWisdomQuestions>(ResponseStatus.PARAMS_ERROR);
    	}
    	if(service.delete(obj.getId()) > 0) {
    		return new ResponseBean<JmWisdomQuestions>();
    	} else {
    		return new ResponseBean<JmWisdomQuestions>(ResponseStatus.NO_DATA);
		}
    }
    
}
