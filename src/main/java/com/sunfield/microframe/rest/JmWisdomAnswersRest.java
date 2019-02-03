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

import com.sunfield.microframe.domain.JmWisdomAnswers;
import com.sunfield.microframe.fallback.JmWisdomAnswersFallback;
import com.sunfield.microframe.service.JmWisdomAnswersService;

/**
 * jm_wisdom_answers rest
 * @author sunfield coder
 */
@RestController
@RequestMapping(value = "/JmWisdomAnswers")
public class JmWisdomAnswersRest extends JmWisdomAnswersFallback{
	
	@Autowired
	private JmWisdomAnswersService service;
	
	@ApiOperation(value="查询列表")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomAnswers")
	@RequestMapping(value = "/findList", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "findListFallback")
    public ResponseBean<List<JmWisdomAnswers>> findList(@RequestBody JmWisdomAnswers obj) {
		List<JmWisdomAnswers> list = service.findList(obj);
		if(!list.isEmpty()) {
			return new ResponseBean<List<JmWisdomAnswers>>(ResponseStatus.SUCCESS, list);
		} else {
			return new ResponseBean<List<JmWisdomAnswers>>(ResponseStatus.NO_DATA);
		}
    }
	
	@ApiOperation(value="分页查询")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomAnswers")
	@RequestMapping(value = "/findPage", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "findPageFallback")
    public ResponseBean<Page<JmWisdomAnswers>> findPage(@RequestBody JmWisdomAnswers obj) {
    	return new ResponseBean<Page<JmWisdomAnswers>>(ResponseStatus.SUCCESS, service.findPage(obj));
    }
	
	@ApiOperation(value="根据主键查询")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomAnswers")
	@RequestMapping(value = "/findOne", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "findOneFallback")
    public ResponseBean<JmWisdomAnswers> findOne(@RequestBody JmWisdomAnswers obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new ResponseBean<JmWisdomAnswers>(ResponseStatus.PARAMS_ERROR);
    	}
    	JmWisdomAnswers object = service.findOne(obj.getId());
    	if(object != null) {
    		return new ResponseBean<JmWisdomAnswers>(ResponseStatus.SUCCESS, object);
    	} else {
    		return new ResponseBean<JmWisdomAnswers>(ResponseStatus.NO_DATA);
		}
    }
	
	@ApiOperation(value="新增")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomAnswers")
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "insertFallback")
    public ResponseBean<JmWisdomAnswers> insert(@RequestBody JmWisdomAnswers obj) {
		JmWisdomAnswers object = service.insert(obj);
		if(object != null) {
			return new ResponseBean<JmWisdomAnswers>(ResponseStatus.SUCCESS, object);
		} else {
			return new ResponseBean<JmWisdomAnswers>(ResponseStatus.FAIL);
		}
    }
	
	@ApiOperation(value="更新")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomAnswers")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "updateFallback")
    public ResponseBean<JmWisdomAnswers> update(@RequestBody JmWisdomAnswers obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new ResponseBean<JmWisdomAnswers>(ResponseStatus.PARAMS_ERROR);
    	}
    	JmWisdomAnswers object = service.update(obj);
    	if(object != null) {
			return new ResponseBean<JmWisdomAnswers>(ResponseStatus.SUCCESS, object);
		} else {
			return new ResponseBean<JmWisdomAnswers>(ResponseStatus.FAIL);
		}
    }
	
	@ApiOperation(value="删除")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomAnswers")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "deleteFallback")
    public ResponseBean<JmWisdomAnswers> delete(@RequestBody JmWisdomAnswers obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new ResponseBean<JmWisdomAnswers>(ResponseStatus.PARAMS_ERROR);
    	}
    	if(service.delete(obj.getId()) > 0) {
    		return new ResponseBean<JmWisdomAnswers>();
    	} else {
    		return new ResponseBean<JmWisdomAnswers>(ResponseStatus.NO_DATA);
		}
    }
    
}
