package com.sunfield.microframe.rest;

import java.util.List;

import com.sunfield.microframe.common.response.*;
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

import com.sunfield.microframe.domain.JmWisdomWebcast;
import com.sunfield.microframe.fallback.JmWisdomWebcastFallback;
import com.sunfield.microframe.service.JmWisdomWebcastService;

/**
 * jm_wisdom_webcast rest
 * @author sunfield coder
 */
@Api(tags = "角马问答-访谈直播接口")
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
	
	@ApiOperation(value="分页查询：后台功能，传递分页信息")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomWebcast")
	@RequestMapping(value = "/findPage", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "findPageFallback")
    public ResponseBean<Page<JmWisdomWebcast>> findPage(@RequestBody JmWisdomWebcast obj) {
    	return new ResponseBean<Page<JmWisdomWebcast>>(ResponseStatus.SUCCESS, service.findPage(obj));
    }
	
	@ApiOperation(value="根据主键查询：传递id,其他不传")
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

	@ApiOperation(value="前台功能：查询当前直播，json中不传任何参数")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomWebcast")
	@RequestMapping(value = "/findCurrent", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "findCurrentFallback")
	public WebcastResponseBean<JmWisdomWebcast> findCurrent(@RequestBody JmWisdomWebcast obj) {
		JmWisdomWebcast object = service.findCurrent();
		if(object != null) {
			return new WebcastResponseBean<JmWisdomWebcast>(WebcastResponseStatus.SUCCESS, object);
		} else {
			return new WebcastResponseBean<JmWisdomWebcast>(WebcastResponseStatus.NO_WEBCAST);
		}
	}

	@ApiOperation(value="新增：传递title，coverUrl，webcastLink，beginTime，endTime，status1（0 启用 2禁用），开始时间不早于结束时间失败，开始时间与当前直播冲突失败")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomWebcast")
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "insertFallback")
    public WebcastResponseBean<JmWisdomWebcast> insert(@RequestBody JmWisdomWebcast obj) {
		return service.insert(obj);
    }
	
	@ApiOperation(value="更新：传递id，title，coverUrl，webcastLink，beginTime，endTime，status1（0 启用 2禁用），开始时间不早于结束时间失败，开始时间与当前直播冲突失败")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomWebcast")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "updateFallback")
    public WebcastResponseBean<JmWisdomWebcast> update(@RequestBody JmWisdomWebcast obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new WebcastResponseBean<JmWisdomWebcast>(WebcastResponseStatus.ID_NULL);
    	}
    	return service.update(obj);
    }
	
	@ApiOperation(value="删除：传递id,其他不传")
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
