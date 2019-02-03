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

import com.sunfield.microframe.domain.JmIndustries;
import com.sunfield.microframe.fallback.JmIndustriesFallback;
import com.sunfield.microframe.service.JmIndustriesService;

/**
 * jm_industries rest
 * @author sunfield coder
 */
@RestController
@RequestMapping(value = "/JmIndustries")
public class JmIndustriesRest extends JmIndustriesFallback{
	
	@Autowired
	private JmIndustriesService service;
	
	@ApiOperation(value="查询列表")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmIndustries")
	@RequestMapping(value = "/findList", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "findListFallback")
    public ResponseBean<List<JmIndustries>> findList(@RequestBody JmIndustries obj) {
		List<JmIndustries> list = service.findList(obj);
		if(!list.isEmpty()) {
			return new ResponseBean<List<JmIndustries>>(ResponseStatus.SUCCESS, list);
		} else {
			return new ResponseBean<List<JmIndustries>>(ResponseStatus.NO_DATA);
		}
    }
	
	@ApiOperation(value="分页查询")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmIndustries")
	@RequestMapping(value = "/findPage", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "findPageFallback")
    public ResponseBean<Page<JmIndustries>> findPage(@RequestBody JmIndustries obj) {
    	return new ResponseBean<Page<JmIndustries>>(ResponseStatus.SUCCESS, service.findPage(obj));
    }
	
	@ApiOperation(value="根据主键查询")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmIndustries")
	@RequestMapping(value = "/findOne", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "findOneFallback")
    public ResponseBean<JmIndustries> findOne(@RequestBody JmIndustries obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new ResponseBean<JmIndustries>(ResponseStatus.PARAMS_ERROR);
    	}
    	JmIndustries object = service.findOne(obj.getId());
    	if(object != null) {
    		return new ResponseBean<JmIndustries>(ResponseStatus.SUCCESS, object);
    	} else {
    		return new ResponseBean<JmIndustries>(ResponseStatus.NO_DATA);
		}
    }
	
	@ApiOperation(value="新增")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmIndustries")
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "insertFallback")
    public ResponseBean<JmIndustries> insert(@RequestBody JmIndustries obj) {
		JmIndustries object = service.insert(obj);
		if(object != null) {
			return new ResponseBean<JmIndustries>(ResponseStatus.SUCCESS, object);
		} else {
			return new ResponseBean<JmIndustries>(ResponseStatus.FAIL);
		}
    }
	
	@ApiOperation(value="更新")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmIndustries")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "updateFallback")
    public ResponseBean<JmIndustries> update(@RequestBody JmIndustries obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new ResponseBean<JmIndustries>(ResponseStatus.PARAMS_ERROR);
    	}
    	JmIndustries object = service.update(obj);
    	if(object != null) {
			return new ResponseBean<JmIndustries>(ResponseStatus.SUCCESS, object);
		} else {
			return new ResponseBean<JmIndustries>(ResponseStatus.FAIL);
		}
    }
	
	@ApiOperation(value="删除")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmIndustries")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "deleteFallback")
    public ResponseBean<JmIndustries> delete(@RequestBody JmIndustries obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new ResponseBean<JmIndustries>(ResponseStatus.PARAMS_ERROR);
    	}
    	if(service.delete(obj.getId()) > 0) {
    		return new ResponseBean<JmIndustries>();
    	} else {
    		return new ResponseBean<JmIndustries>(ResponseStatus.NO_DATA);
		}
    }
    
}
