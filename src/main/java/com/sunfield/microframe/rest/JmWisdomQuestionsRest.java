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

import com.sunfield.microframe.domain.JmWisdomQuestions;
import com.sunfield.microframe.fallback.JmWisdomQuestionsFallback;
import com.sunfield.microframe.service.JmWisdomQuestionsService;

/**
 * jm_wisdom_questions rest
 * @author sunfield coder
 * 所有业务代码写在Service层，Controller层只保留对业务的调用
 */
@Api(tags = "jm-wisdom-questions")
@RestController
@RequestMapping(value = "/JmWisdomQuestions")
public class JmWisdomQuestionsRest extends JmWisdomQuestionsFallback{
	
	@Autowired
	private JmWisdomQuestionsService service;

	@ApiOperation(value="查询列表：业务1：精品查询，selectOrder字段传1，其他不传；" +
			"业务2：前台按行业显示，industryId字段传行业id，其他不传")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomQuestions")
	@RequestMapping(value = "/findList", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "findListFallback")
    public ResponseBean<List<JmWisdomQuestions>> findList(@RequestBody JmWisdomQuestions obj) {
		List<JmWisdomQuestions> list = service.findList(obj);
		if(!list.isEmpty()) {
			return new ResponseBean<List<JmWisdomQuestions>>(ResponseStatus.SUCCESS, list);
		} else {
			return new ResponseBean<List<JmWisdomQuestions>>(ResponseStatus.NO_DATA);
		}
    }
	
	@ApiOperation(value="分页查询：后台功能，全行业分页列表显示，传递分页信息，其他不传；" +
			"如果前台列表业务也需要分页，也可传递对应字段调用该分页服务")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomQuestions")
	@RequestMapping(value = "/findPage", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "findPageFallback")
    public ResponseBean<Page<JmWisdomQuestions>> findPage(@RequestBody JmWisdomQuestions obj) {
		return new ResponseBean<Page<JmWisdomQuestions>>(ResponseStatus.SUCCESS, service.findPage(obj));
    }
	
	@ApiOperation(value="根据主键查询：传递id,其他不传")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomQuestions")
	@RequestMapping(value = "/findOne", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "findOneFallback")
    public ResponseBean<JmWisdomQuestions> findOne(@RequestBody JmWisdomQuestions obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new ResponseBean<JmWisdomQuestions>(ResponseStatus.PARAMS_ERROR);
    	}
    	JmWisdomQuestions jmWisdomQuestions = service.findOne(obj.getId());
    	if(jmWisdomQuestions != null) {
    		return new ResponseBean<JmWisdomQuestions>(ResponseStatus.SUCCESS, jmWisdomQuestions);
    	} else {
    		return new ResponseBean<JmWisdomQuestions>(ResponseStatus.NO_DATA);
		}
    }
	
	@ApiOperation(value="新增：传递content，industryId，userId，mediaType（1图片 2视频），ossUrls，title，其他不传")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomQuestions")
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "insertFallback")
    public ResponseBean<JmWisdomQuestions> insert(@RequestBody JmWisdomQuestions obj) {
		JmWisdomQuestions object = service.insert(obj);
		if(object != null) {
			return new ResponseBean<JmWisdomQuestions>(ResponseStatus.SUCCESS, object);
		} else {
			return new ResponseBean<JmWisdomQuestions>(ResponseStatus.FAIL);
		}
    }
	
	@ApiOperation(value="更新：业务1：用户点赞，传id，ayes字段传1，其他不传；业务2：用户踩，传id，antis字段传1，" +
			"其他不传；业务3：用户新回答，传id，answers字段传1，其他不传；业务4：设置精品排序，后台管理功能，传id，" +
			"selectOrder字段传需要设置的序号，其他不传")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomQuestions")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "updateFallback")
    public ResponseBean<JmWisdomQuestions> update(@RequestBody JmWisdomQuestions obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new ResponseBean<JmWisdomQuestions>(ResponseStatus.PARAMS_ERROR);
    	}
    	JmWisdomQuestions object = service.update(obj);
    	if(object != null) {
			return new ResponseBean<JmWisdomQuestions>(ResponseStatus.SUCCESS, object);
		} else {
			return new ResponseBean<JmWisdomQuestions>(ResponseStatus.FAIL);
		}
    }
	
	@ApiOperation(value="删除：传递id,其他不传")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomQuestions")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@HystrixCommand(fallbackMethod = "deleteFallback")
    public ResponseBean<JmWisdomQuestions> delete(@RequestBody JmWisdomQuestions obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new ResponseBean<JmWisdomQuestions>(ResponseStatus.PARAMS_ERROR);
    	}
    	if(service.delete(obj.getId()) > 0) {
    		return new ResponseBean<JmWisdomQuestions>();
    	} else {
    		return new ResponseBean<JmWisdomQuestions>(ResponseStatus.NO_DATA);
		}
    }
    
}
