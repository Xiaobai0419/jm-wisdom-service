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

import com.sunfield.microframe.domain.JmWisdomInterviews;
import com.sunfield.microframe.fallback.JmWisdomInterviewsFallback;
import com.sunfield.microframe.service.JmWisdomInterviewsService;

/**
 * jm_wisdom_interviews rest
 * @author sunfield coder
 */
@RestController
@RequestMapping(value = "/JmWisdomInterviews")
public class JmWisdomInterviewsRest extends JmWisdomInterviewsFallback{
	
	@Autowired
	private JmWisdomInterviewsService service;
	
	@ApiOperation(value="查询列表")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomInterviews")
	@RequestMapping(value = "/findList", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "findListFallback")
    public ResponseBean<List<JmWisdomInterviews>> findList(@RequestBody JmWisdomInterviews obj) {
		List<JmWisdomInterviews> list = service.findList(obj);
		if(!list.isEmpty()) {
			return new ResponseBean<List<JmWisdomInterviews>>(ResponseStatus.SUCCESS, list);
		} else {
			return new ResponseBean<List<JmWisdomInterviews>>(ResponseStatus.NO_DATA);
		}
    }
	
	@ApiOperation(value="分页查询")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomInterviews")
	@RequestMapping(value = "/findPage", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "findPageFallback")
    public ResponseBean<Page<JmWisdomInterviews>> findPage(@RequestBody JmWisdomInterviews obj) {
    	return new ResponseBean<Page<JmWisdomInterviews>>(ResponseStatus.SUCCESS, service.findPage(obj));
    }
	
	@ApiOperation(value="根据主键查询")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomInterviews")
	@RequestMapping(value = "/findOne", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "findOneFallback")
    public ResponseBean<JmWisdomInterviews> findOne(@RequestBody JmWisdomInterviews obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new ResponseBean<JmWisdomInterviews>(ResponseStatus.PARAMS_ERROR);
    	}
    	JmWisdomInterviews object = service.findOne(obj.getId());
    	if(object != null) {
    		return new ResponseBean<JmWisdomInterviews>(ResponseStatus.SUCCESS, object);
    	} else {
    		return new ResponseBean<JmWisdomInterviews>(ResponseStatus.NO_DATA);
		}
    }
	
	@ApiOperation(value="新增")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomInterviews")
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "insertFallback")
    public ResponseBean<JmWisdomInterviews> insert(@RequestBody JmWisdomInterviews obj) {
		JmWisdomInterviews object = service.insert(obj);
		if(object != null) {
			return new ResponseBean<JmWisdomInterviews>(ResponseStatus.SUCCESS, object);
		} else {
			return new ResponseBean<JmWisdomInterviews>(ResponseStatus.FAIL);
		}
    }
	
	@ApiOperation(value="更新")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomInterviews")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "updateFallback")
    public ResponseBean<JmWisdomInterviews> update(@RequestBody JmWisdomInterviews obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new ResponseBean<JmWisdomInterviews>(ResponseStatus.PARAMS_ERROR);
    	}
    	JmWisdomInterviews object = service.update(obj);
    	if(object != null) {
			return new ResponseBean<JmWisdomInterviews>(ResponseStatus.SUCCESS, object);
		} else {
			return new ResponseBean<JmWisdomInterviews>(ResponseStatus.FAIL);
		}
    }
	
	@ApiOperation(value="删除")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomInterviews")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "deleteFallback")
    public ResponseBean<JmWisdomInterviews> delete(@RequestBody JmWisdomInterviews obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new ResponseBean<JmWisdomInterviews>(ResponseStatus.PARAMS_ERROR);
    	}
    	if(service.delete(obj.getId()) > 0) {
    		return new ResponseBean<JmWisdomInterviews>();
    	} else {
    		return new ResponseBean<JmWisdomInterviews>(ResponseStatus.NO_DATA);
		}
    }
    
}