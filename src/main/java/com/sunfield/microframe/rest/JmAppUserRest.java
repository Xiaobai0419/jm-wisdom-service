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

import com.sunfield.microframe.domain.JmAppUser;
import com.sunfield.microframe.fallback.JmAppUserFallback;
import com.sunfield.microframe.service.JmAppUserService;

/**
 * jm_app_user rest
 * @author sunfield coder
 */
@RestController
@RequestMapping(value = "/JmAppUser")
public class JmAppUserRest extends JmAppUserFallback{
	
	@Autowired
	private JmAppUserService service;
	
	@ApiOperation(value="查询列表")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmAppUser")
	@RequestMapping(value = "/findList", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "findListFallback")
    public ResponseBean<List<JmAppUser>> findList(@RequestBody JmAppUser obj) {
		List<JmAppUser> list = service.findList(obj);
		if(!list.isEmpty()) {
			return new ResponseBean<List<JmAppUser>>(ResponseStatus.SUCCESS, list);
		} else {
			return new ResponseBean<List<JmAppUser>>(ResponseStatus.NO_DATA);
		}
    }
	
	@ApiOperation(value="分页查询")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmAppUser")
	@RequestMapping(value = "/findPage", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "findPageFallback")
    public ResponseBean<Page<JmAppUser>> findPage(@RequestBody JmAppUser obj) {
    	return new ResponseBean<Page<JmAppUser>>(ResponseStatus.SUCCESS, service.findPage(obj));
    }
	
	@ApiOperation(value="根据主键查询")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmAppUser")
	@RequestMapping(value = "/findOne", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "findOneFallback")
    public ResponseBean<JmAppUser> findOne(@RequestBody JmAppUser obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new ResponseBean<JmAppUser>(ResponseStatus.PARAMS_ERROR);
		}
    
    	JmAppUser object = service.findOne(obj.getId());
    	if(object != null) {
			return new ResponseBean<JmAppUser>(ResponseStatus.SUCCESS, object);
		} else {
			return new ResponseBean<JmAppUser>(ResponseStatus.NO_DATA);
		}
    }
	
	@ApiOperation(value="新增")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmAppUser")
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "insertFallback")
    public ResponseBean<JmAppUser> insert(@RequestBody JmAppUser obj) {
		JmAppUser object = service.insert(obj);
		if(object != null) {
			return new ResponseBean<JmAppUser>(ResponseStatus.SUCCESS, object);
		} else {
			return new ResponseBean<JmAppUser>(ResponseStatus.FAIL);
		}
    }
	
	@ApiOperation(value="更新")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmAppUser")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "updateFallback")
    public ResponseBean<JmAppUser> update(@RequestBody JmAppUser obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new ResponseBean<JmAppUser>(ResponseStatus.PARAMS_ERROR);
		}
    
    	JmAppUser object = service.update(obj);
    	if(object != null) {
			return new ResponseBean<JmAppUser>(ResponseStatus.SUCCESS, object);
		} else {
			return new ResponseBean<JmAppUser>(ResponseStatus.FAIL);
		}
    }
	
	@ApiOperation(value="删除")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmAppUser")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "deleteFallback")
    public ResponseBean<JmAppUser> delete(@RequestBody JmAppUser obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new ResponseBean<JmAppUser>(ResponseStatus.PARAMS_ERROR);
		}
    
    	if(service.delete(obj.getId()) > 0) {
			return new ResponseBean<JmAppUser>();
		} else {
			return new ResponseBean<JmAppUser>(ResponseStatus.NO_DATA);
		}
    }
    
}
