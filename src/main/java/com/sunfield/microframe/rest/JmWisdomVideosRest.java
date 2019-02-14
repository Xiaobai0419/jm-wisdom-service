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

import com.sunfield.microframe.domain.JmWisdomVideos;
import com.sunfield.microframe.fallback.JmWisdomVideosFallback;
import com.sunfield.microframe.service.JmWisdomVideosService;

/**
 * jm_wisdom_videos rest
 * @author sunfield coder
 */
@RestController
@RequestMapping(value = "/JmWisdomVideos")
public class JmWisdomVideosRest extends JmWisdomVideosFallback{
	
	@Autowired
	private JmWisdomVideosService service;
	
	@ApiOperation(value="查询列表")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomVideos")
	@RequestMapping(value = "/findList", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "findListFallback")
    public ResponseBean<List<JmWisdomVideos>> findList(@RequestBody JmWisdomVideos obj) {
		List<JmWisdomVideos> list = service.findList(obj);
		if(!list.isEmpty()) {
			return new ResponseBean<List<JmWisdomVideos>>(ResponseStatus.SUCCESS, list);
		} else {
			return new ResponseBean<List<JmWisdomVideos>>(ResponseStatus.NO_DATA);
		}
    }
	
	@ApiOperation(value="分页查询")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomVideos")
	@RequestMapping(value = "/findPage", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "findPageFallback")
    public ResponseBean<Page<JmWisdomVideos>> findPage(@RequestBody JmWisdomVideos obj) {
    	return new ResponseBean<Page<JmWisdomVideos>>(ResponseStatus.SUCCESS, service.findPage(obj));
    }
	
	@ApiOperation(value="根据主键查询")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomVideos")
	@RequestMapping(value = "/findOne", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "findOneFallback")
    public ResponseBean<JmWisdomVideos> findOne(@RequestBody JmWisdomVideos obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new ResponseBean<JmWisdomVideos>(ResponseStatus.PARAMS_ERROR);
    	}
    	JmWisdomVideos object = service.findOne(obj.getId());
    	if(object != null) {
    		return new ResponseBean<JmWisdomVideos>(ResponseStatus.SUCCESS, object);
    	} else {
    		return new ResponseBean<JmWisdomVideos>(ResponseStatus.NO_DATA);
		}
    }
	
	@ApiOperation(value="新增")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomVideos")
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "insertFallback")
    public ResponseBean<JmWisdomVideos> insert(@RequestBody JmWisdomVideos obj) {
		JmWisdomVideos object = service.insert(obj);
		if(object != null) {
			return new ResponseBean<JmWisdomVideos>(ResponseStatus.SUCCESS, object);
		} else {
			return new ResponseBean<JmWisdomVideos>(ResponseStatus.FAIL);
		}
    }
	
	@ApiOperation(value="更新")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomVideos")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "updateFallback")
    public ResponseBean<JmWisdomVideos> update(@RequestBody JmWisdomVideos obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new ResponseBean<JmWisdomVideos>(ResponseStatus.PARAMS_ERROR);
    	}
    	JmWisdomVideos object = service.update(obj);
    	if(object != null) {
			return new ResponseBean<JmWisdomVideos>(ResponseStatus.SUCCESS, object);
		} else {
			return new ResponseBean<JmWisdomVideos>(ResponseStatus.FAIL);
		}
    }
	
	@ApiOperation(value="删除")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomVideos")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "deleteFallback")
    public ResponseBean<JmWisdomVideos> delete(@RequestBody JmWisdomVideos obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new ResponseBean<JmWisdomVideos>(ResponseStatus.PARAMS_ERROR);
    	}
    	if(service.delete(obj.getId()) > 0) {
    		return new ResponseBean<JmWisdomVideos>();
    	} else {
    		return new ResponseBean<JmWisdomVideos>(ResponseStatus.NO_DATA);
		}
    }
    
}
