package com.sunfield.microframe.rest;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import com.sunfield.microframe.common.response.Page;
import com.sunfield.microframe.common.response.ResponseBean;
import com.sunfield.microframe.common.response.ResponseStatus;

import com.sunfield.microframe.domain.JmWisdomAnswers;
import com.sunfield.microframe.service.JmWisdomAnswersService;

/**
 * jm_wisdom_answers rest
 * @author sunfield coder
 */
@Api(tags = "jm-wisdom-answers")
@RestController
@RequestMapping(value = "/JmWisdomAnswers")
public class JmWisdomAnswersRest {
	
	@Autowired
	private JmWisdomAnswersService service;
	
	@ApiOperation(value="查询列表：前台功能，传questionId，visitUserId（未登录访问可不传），问题对应回答列表")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomAnswers")
	@RequestMapping(value = "/findList", method = RequestMethod.POST)
    public ResponseBean<List<JmWisdomAnswers>> findList(@RequestBody JmWisdomAnswers obj) {
		List<JmWisdomAnswers> list = service.findList(obj);
		if(!list.isEmpty()) {
			return new ResponseBean<List<JmWisdomAnswers>>(ResponseStatus.SUCCESS, list);
		} else {
			return new ResponseBean<List<JmWisdomAnswers>>(ResponseStatus.NO_DATA);
		}
    }
	
	@ApiOperation(value="分页查询：前台功能，传questionId，visitUserId（未登录访问可不传），传递分页参数，问题对应回答列表；" +
			"后台管理，传递分页参数，角马问答板块所有评论列表")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomAnswers")
	@RequestMapping(value = "/findPage", method = RequestMethod.POST)
    public ResponseBean<Page<JmWisdomAnswers>> findPage(@RequestBody JmWisdomAnswers obj) {
    	return new ResponseBean<Page<JmWisdomAnswers>>(ResponseStatus.SUCCESS, service.findPage(obj));
    }

	@ApiOperation(value="根据主键查询：传id,visitUserId（未登录访问可不传），其他不传")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomAnswers")
	@RequestMapping(value = "/findOne", method = RequestMethod.POST)
    public ResponseBean<JmWisdomAnswers> findOne(@RequestBody JmWisdomAnswers obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new ResponseBean<JmWisdomAnswers>(ResponseStatus.PARAMS_ERROR);
    	}
    	JmWisdomAnswers object = service.findOne(obj);
    	if(object != null) {
    		return new ResponseBean<JmWisdomAnswers>(ResponseStatus.SUCCESS, object);
    	} else {
    		return new ResponseBean<JmWisdomAnswers>(ResponseStatus.NO_DATA);
		}
    }

	@ApiOperation(value="根据问题id数组查询第一条回答集合")
	@ApiImplicitParam(name = "questionIds", value = "", required = true, dataType = "String", allowMultiple = true)
	@RequestMapping(value = "/findFirstAnswers", method = RequestMethod.POST)
	public ResponseBean<Map<String,JmWisdomAnswers>> findFirstAnswers(String[] questionIds) {
		if(questionIds == null || questionIds.length == 0) {
			return new ResponseBean<Map<String,JmWisdomAnswers>>(ResponseStatus.PARAMS_ERROR);
		}
		Map<String,JmWisdomAnswers> answersMap = service.findFirstAnswers(questionIds);
		if(answersMap != null && answersMap.size() > 0) {
			return new ResponseBean<Map<String,JmWisdomAnswers>>(ResponseStatus.SUCCESS, answersMap);
		} else {
			return new ResponseBean<Map<String,JmWisdomAnswers>>(ResponseStatus.NO_DATA);
		}
	}

	@ApiOperation(value="新增：传递content，questionId，userId，title，其他不传")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomAnswers")
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseBean<JmWisdomAnswers> insert(@RequestBody JmWisdomAnswers obj) {
		JmWisdomAnswers object = service.insert(obj);
		if(object != null) {
			return new ResponseBean<JmWisdomAnswers>(ResponseStatus.SUCCESS, object);
		} else {
			return new ResponseBean<JmWisdomAnswers>(ResponseStatus.FAIL);
		}
    }
	
//	@ApiOperation(value="更新：业务1，前台用户点赞回答，传id,ayes字段传1，其他不传；" +
//			"业务2，前台用户踩回答，传id,antis字段传1，其他不传")
//	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomAnswers")
//	@RequestMapping(value = "/update", method = RequestMethod.POST)
//    public ResponseBean<JmWisdomAnswers> update(@RequestBody JmWisdomAnswers obj) {
//    	if(StringUtils.isBlank(obj.getId())) {
//			return new ResponseBean<JmWisdomAnswers>(ResponseStatus.PARAMS_ERROR);
//    	}
//    	JmWisdomAnswers object = service.update(obj);
//    	if(object != null) {
//			return new ResponseBean<JmWisdomAnswers>(ResponseStatus.SUCCESS, object);
//		} else {
//			return new ResponseBean<JmWisdomAnswers>(ResponseStatus.FAIL);
//		}
//    }
	
	@ApiOperation(value="删除：后台管理，传id,其他不传")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomAnswers")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
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
