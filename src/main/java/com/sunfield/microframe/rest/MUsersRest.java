package com.sunfield.microframe.rest;

import java.util.*;

import com.sunfield.microframe.common.redis.RedisBaseTemplate;
import com.sunfield.microframe.common.redis.RedisHashTemplate;
import com.sunfield.microframe.common.redis.RedisListTemplate;
import com.sunfield.microframe.common.redis.RedisSetTemplate;
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

import com.sunfield.microframe.domain.MUsers;
import com.sunfield.microframe.fallback.MUsersFallback;
import com.sunfield.microframe.service.MUsersService;

/**
 * m_users rest
 * @author sunfield coder
 */
@RestController
@RequestMapping(value = "/MUsers")
public class MUsersRest extends MUsersFallback{
	
	@Autowired
	private MUsersService service;
	
	@ApiOperation(value="查询列表")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "MUsers")
	@RequestMapping(value = "/findList", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "findListFallback")
    public ResponseBean<List<MUsers>> findList(@RequestBody MUsers obj) {
		List<MUsers> list = service.findList(obj);
		if(!list.isEmpty()) {
			return new ResponseBean<List<MUsers>>(ResponseStatus.SUCCESS, list);
		} else {
			return new ResponseBean<List<MUsers>>(ResponseStatus.NO_DATA);
		}
    }
	
	@ApiOperation(value="分页查询")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "MUsers")
	@RequestMapping(value = "/findPage", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "findPageFallback")
    public ResponseBean<Page<MUsers>> findPage(@RequestBody MUsers obj) {
    	return new ResponseBean<Page<MUsers>>(ResponseStatus.SUCCESS, service.findPage(obj));
    }

	@ApiOperation(value="根据主键查询")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "MUsers")
	@RequestMapping(value = "/findOne", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "findOneFallback")
    public ResponseBean<MUsers> findOne(@RequestBody MUsers obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new ResponseBean<MUsers>(ResponseStatus.PARAMS_ERROR);
		}

    	MUsers obj_ = service.findOne(obj.getId());
    	if(obj_ != null) {
			return new ResponseBean<MUsers>(ResponseStatus.SUCCESS, obj_);
		} else {
			return new ResponseBean<MUsers>(ResponseStatus.NO_DATA);
		}
    }

	@ApiOperation(value="新增")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "MUsers")
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "insertFallback")
    public ResponseBean<MUsers> insert(@RequestBody MUsers obj) {
		MUsers obj_ = service.insert(obj);
		if(obj_ != null) {
			return new ResponseBean<MUsers>(ResponseStatus.SUCCESS, obj_);
		} else {
			return new ResponseBean<MUsers>(ResponseStatus.FAIL);
		}
    }
	
	@ApiOperation(value="更新")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "MUsers")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "updateFallback")
    public ResponseBean<MUsers> update(@RequestBody MUsers obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new ResponseBean<MUsers>(ResponseStatus.PARAMS_ERROR);
		}
    
    	MUsers obj_ = service.update(obj);
    	if(obj_ != null) {
			return new ResponseBean<MUsers>(ResponseStatus.SUCCESS, obj_);
		} else {
			return new ResponseBean<MUsers>(ResponseStatus.FAIL);
		}
    }
	
	@ApiOperation(value="删除")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "MUsers")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "deleteFallback")
    public ResponseBean<MUsers> delete(@RequestBody MUsers obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new ResponseBean<MUsers>(ResponseStatus.PARAMS_ERROR);
		}
    
    	if(service.delete(obj.getId()) > 0) {
			return new ResponseBean<MUsers>();
		} else {
			return new ResponseBean<MUsers>(ResponseStatus.NO_DATA);
		}
    }

    @Autowired
    private RedisBaseTemplate redisBaseTemplate;

	@Autowired
	private RedisHashTemplate redisHashTemplate;

	@Autowired
	private RedisSetTemplate redisSetTemplate;

	@Autowired
	private RedisListTemplate redisListTemplate;

	@RequestMapping(value = "/redis", method = RequestMethod.POST)
    public void redis(@RequestBody MUsers obj){
//		redisBaseTemplate.set("testkey", obj);
//		System.out.println(redisBaseTemplate.hasKey("testkey"));
//		redisBaseTemplate.expire("testkey", 30);
//		System.out.println(redisBaseTemplate.getExpire("testkey"));
//		MUsers cache = (MUsers) redisBaseTemplate.get("testkey");
//		System.out.println(cache.getId());
//		redisBaseTemplate.del("testkey");
//		Map<String, Object> map = new HashMap<>();
//		map.put("userkey", obj);
//		redisHashTemplate.set("o1", map);
//		MUsers c1 = (MUsers)redisHashTemplate.get("o1", "userkey");
//		System.out.println(c1.getId());
//
//		redisHashTemplate.set("o1", "newkey", "iamnewobjet");
//		String c2 = (String)redisHashTemplate.get("o1", "newkey");
//		System.out.println(c2);
//
//		System.out.println(redisHashTemplate.hasKey("o1", "newkey"));
//		redisHashTemplate.del("o1", "newkey");
//		System.out.println(redisHashTemplate.hasKey("o1", "newkey"));
//
//		redisHashTemplate.set("o2", map, 50);
//		System.out.println(redisBaseTemplate.getExpire("o2"));
//
//		redisSetTemplate.setWithTime("testkey",50,obj, "objstring");
//		System.out.println(redisBaseTemplate.getExpire("testkey"));
//		System.out.println(redisSetTemplate.hasKey("testkey", "objstring"));
//		System.out.println(redisSetTemplate.getSize("testkey"));
//		redisSetTemplate.del("testkey", "objstring");
//		System.out.println(redisSetTemplate.hasKey("testkey", "objstring"));
//		System.out.println(redisSetTemplate.getSize("testkey"));
//		Set<Object> s1 = redisSetTemplate.get("testkey");
//		System.out.println(s1.contains(obj));

		MUsers o1 = new MUsers();
		o1.setId("id1");
		o1.setMobile("m12345");
		MUsers o2 = new MUsers();
		o2.setId("id2");
		o2.setMobile("m54321");

		List<Object> l = new ArrayList<>();
		l.add(o1);
		l.add(o2);

		redisListTemplate.set("listkey", l, 50);
		System.out.println(redisBaseTemplate.getExpire("listkey"));
		System.out.println(redisListTemplate.getSize("listkey"));
		List<Object> c1 = redisListTemplate.get("listkey");
		System.out.println(((MUsers)c1.get(0)).getId());

	}
}
