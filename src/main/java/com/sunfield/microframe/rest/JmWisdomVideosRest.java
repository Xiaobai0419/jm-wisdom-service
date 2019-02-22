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

import com.sunfield.microframe.domain.JmWisdomVideos;
import com.sunfield.microframe.fallback.JmWisdomVideosFallback;
import com.sunfield.microframe.service.JmWisdomVideosService;

/**
 * jm_wisdom_videos rest
 * @author sunfield coder
 */
@Api(tags = "jm-wisdom-videos")
@RestController
@RequestMapping(value = "/JmWisdomVideos")
public class JmWisdomVideosRest extends JmWisdomVideosFallback{
	
	@Autowired
	private JmWisdomVideosService service;
	
	@ApiOperation(value="查询列表：访谈对应视频列表，传递interviewId，其他不传")
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
	
	@ApiOperation(value="分页查询：访谈对应视频列表，传递interviewId，分页信息")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomVideos")
	@RequestMapping(value = "/findPage", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "findPageFallback")
    public ResponseBean<Page<JmWisdomVideos>> findPage(@RequestBody JmWisdomVideos obj) {
    	return new ResponseBean<Page<JmWisdomVideos>>(ResponseStatus.SUCCESS, service.findPage(obj));
    }
	
	@ApiOperation(value="根据主键查询：传id，visitUserId（未登录访问可不传）字段,其他不传")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomVideos")
	@RequestMapping(value = "/findOne", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "findOneFallback")
    public ResponseBean<JmWisdomVideos> findOne(@RequestBody JmWisdomVideos obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new ResponseBean<JmWisdomVideos>(ResponseStatus.PARAMS_ERROR);
    	}
    	JmWisdomVideos object = service.findOne(obj);
    	if(object != null) {
    		return new ResponseBean<JmWisdomVideos>(ResponseStatus.SUCCESS, object);
    	} else {
    		return new ResponseBean<JmWisdomVideos>(ResponseStatus.NO_DATA);
		}
    }
	
	@ApiOperation(value="新增：后台功能，传title，interviewId，coverUrl，videoUrl，" +
			"allowComments（1 允许评论 2不允许评论），leaguerOnly（1 会员专属 2 非会员专属），" +
			"freeDuration（会员专属时才有值，可为空，空代表不限制，数值代表免费时长）")
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
	
	@ApiOperation(value="更新：前台禁用！" +
			"后台功能，传id，title，coverUrl，videoUrl，" +
			"allowComments（1 允许评论 2不允许评论），leaguerOnly（1 会员专属 2 非会员专属），" +
			"freeDuration（会员专属时才有值，可为空，空代表不限制，数值代表免费时长）")
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
	
	@ApiOperation(value="删除：传递id,其他不传")
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
