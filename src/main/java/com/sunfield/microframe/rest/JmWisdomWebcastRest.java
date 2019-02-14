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

import com.sunfield.microframe.domain.JmWisdomWebcast;
import com.sunfield.microframe.fallback.JmWisdomWebcastFallback;
import com.sunfield.microframe.service.JmWisdomWebcastService;

/**
 * jm_wisdom_webcast rest
 * @author sunfield coder
 */
@RestController
@RequestMapping(value = "/JmWisdomWebcast")
public class JmWisdomWebcastRest extends JmWisdomWebcastFallback{
	
	@Autowired
	private JmWisdomWebcastService service;
	
	@ApiOperation(value="查询列表")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomWebcast")
	@RequestMapping(value = "/findList", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "findListFallback")
    public ResponseBean<List<JmWisdomWebcast>> findList(@RequestBody JmWisdomWebcast obj) {
		List<JmWisdomWebcast> list = service.findList(obj);
		if(!list.isEmpty()) {
			return new ResponseBean<List<JmWisdomWebcast>>(ResponseStatus.SUCCESS, list);
		} else {
			return new ResponseBean<List<JmWisdomWebcast>>(ResponseStatus.NO_DATA);
		}
    }
	
	@ApiOperation(value="分页查询")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomWebcast")
	@RequestMapping(value = "/findPage", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "findPageFallback")
    public ResponseBean<Page<JmWisdomWebcast>> findPage(@RequestBody JmWisdomWebcast obj) {
    	return new ResponseBean<Page<JmWisdomWebcast>>(ResponseStatus.SUCCESS, service.findPage(obj));
    }
	
	@ApiOperation(value="根据主键查询")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomWebcast")
	@RequestMapping(value = "/findOne", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "findOneFallback")
    public ResponseBean<JmWisdomWebcast> findOne(@RequestBody JmWisdomWebcast obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new ResponseBean<JmWisdomWebcast>(ResponseStatus.PARAMS_ERROR);
    	}
    	JmWisdomWebcast object = service.findOne(obj.getId());
    	if(object != null) {
    		return new ResponseBean<JmWisdomWebcast>(ResponseStatus.SUCCESS, object);
    	} else {
    		return new ResponseBean<JmWisdomWebcast>(ResponseStatus.NO_DATA);
		}
    }
	
	@ApiOperation(value="新增")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomWebcast")
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "insertFallback")
    public ResponseBean<JmWisdomWebcast> insert(@RequestBody JmWisdomWebcast obj) {
		JmWisdomWebcast object = service.insert(obj);
		if(object != null) {
			return new ResponseBean<JmWisdomWebcast>(ResponseStatus.SUCCESS, object);
		} else {
			return new ResponseBean<JmWisdomWebcast>(ResponseStatus.FAIL);
		}
    }
	
	@ApiOperation(value="更新")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomWebcast")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "updateFallback")
    public ResponseBean<JmWisdomWebcast> update(@RequestBody JmWisdomWebcast obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new ResponseBean<JmWisdomWebcast>(ResponseStatus.PARAMS_ERROR);
    	}
    	JmWisdomWebcast object = service.update(obj);
    	if(object != null) {
			return new ResponseBean<JmWisdomWebcast>(ResponseStatus.SUCCESS, object);
		} else {
			return new ResponseBean<JmWisdomWebcast>(ResponseStatus.FAIL);
		}
    }
	
	@ApiOperation(value="删除")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomWebcast")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "deleteFallback")
    public ResponseBean<JmWisdomWebcast> delete(@RequestBody JmWisdomWebcast obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new ResponseBean<JmWisdomWebcast>(ResponseStatus.PARAMS_ERROR);
    	}
    	if(service.delete(obj.getId()) > 0) {
    		return new ResponseBean<JmWisdomWebcast>();
    	} else {
    		return new ResponseBean<JmWisdomWebcast>(ResponseStatus.NO_DATA);
		}
    }
    
}
