package com.sunfield.microframe.rest;

import java.util.List;

import com.sunfield.microframe.domain.JmWisdomUserQuestions;
import com.sunfield.microframe.service.JmWisdomUserQuestionsService;
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

import com.sunfield.microframe.domain.JmWisdomInterviews;
import com.sunfield.microframe.service.JmWisdomInterviewsService;

/**
 * jm_wisdom_interviews rest
 * @author sunfield coder
 */
@Api(tags = "jm-wisdom-interviews")
@RestController
@RequestMapping(value = "/JmWisdomInterviews")
public class JmWisdomInterviewsRest {
	
	@Autowired
	private JmWisdomInterviewsService service;
	@Autowired
	private JmWisdomUserQuestionsService jmWisdomUserQuestionsService;

	@ApiOperation(value="查询列表：业务1：前台功能，访谈首页列表，按推荐排序，selectOrder字段传1，其他不传；" +
			"业务2：前台功能，视频首页访谈列表，按视频板块推荐排序，videoSelectOrder字段传1，其他不传；" +
			"业务3：前台功能，访谈更多列表页，按上传时间倒序，json不传任何字段" +
			"业务4：后台管理功能，按上传时间倒序，json不传任何字段")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomInterviews")
	@RequestMapping(value = "/findList", method = RequestMethod.POST)
    public ResponseBean<List<JmWisdomInterviews>> findList(@RequestBody JmWisdomInterviews obj) {
		List<JmWisdomInterviews> list = service.findList(obj);
		if(!list.isEmpty()) {
			return new ResponseBean<List<JmWisdomInterviews>>(ResponseStatus.SUCCESS, list);
		} else {
			return new ResponseBean<List<JmWisdomInterviews>>(ResponseStatus.NO_DATA);
		}
    }

	@ApiOperation(value="查询列表：个人收藏的访谈，userId：登录用户id；type：传固定值3，代表类型为访谈")
	@ApiImplicitParam(name = "jmWisdomUserQuestions", value = "", required = true, dataType = "JmWisdomUserQuestions")
	@RequestMapping(value = "/findOnesList", method = RequestMethod.POST)
	public ResponseBean<List<JmWisdomInterviews>> findOnesList(@RequestBody JmWisdomUserQuestions jmWisdomUserQuestions) {
		List<JmWisdomInterviews> list = jmWisdomUserQuestionsService.findOnesInterviewsList(jmWisdomUserQuestions);
		if(!list.isEmpty()) {
			return new ResponseBean<List<JmWisdomInterviews>>(ResponseStatus.SUCCESS, list);
		} else {
			return new ResponseBean<List<JmWisdomInterviews>>(ResponseStatus.NO_DATA);
		}
	}

	@ApiOperation(value="分页查询：后台功能，传递分页信息")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomInterviews")
	@RequestMapping(value = "/findPage", method = RequestMethod.POST)
    public ResponseBean<Page<JmWisdomInterviews>> findPage(@RequestBody JmWisdomInterviews obj) {
    	return new ResponseBean<Page<JmWisdomInterviews>>(ResponseStatus.SUCCESS, service.findPage(obj));
    }

	@ApiOperation(value="分页查询：个人收藏的访谈，userId：登录用户id；type：传固定值3，代表类型为访谈")
	@ApiImplicitParam(name = "jmWisdomUserQuestions", value = "", required = true, dataType = "JmWisdomUserQuestions")
	@RequestMapping(value = "/findOnesPage", method = RequestMethod.POST)
	public ResponseBean<Page<JmWisdomInterviews>> findOnesPage(@RequestBody JmWisdomUserQuestions jmWisdomUserQuestions) {
		return new ResponseBean<Page<JmWisdomInterviews>>(ResponseStatus.SUCCESS, jmWisdomUserQuestionsService.findOnesInterviewsPage(jmWisdomUserQuestions));
	}

	@ApiOperation(value="根据主键查询：传递id，visitUserId（未登录访问可不传）字段，其他不传")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomInterviews")
	@RequestMapping(value = "/findOne", method = RequestMethod.POST)
    public ResponseBean<JmWisdomInterviews> findOne(@RequestBody JmWisdomInterviews obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new ResponseBean<JmWisdomInterviews>(ResponseStatus.PARAMS_ERROR);
    	}
    	JmWisdomInterviews object = service.findOne(obj);
    	if(object != null) {
    		return new ResponseBean<JmWisdomInterviews>(ResponseStatus.SUCCESS, object);
    	} else {
    		return new ResponseBean<JmWisdomInterviews>(ResponseStatus.NO_DATA);
		}
    }
	
	@ApiOperation(value="新增：后台功能，传递title，content，coverUrl，selectOrder，videoSelectOrder，" +
			"支持插入时设置两种排序号，传空或不传代表不参与排序")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomInterviews")
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseBean<JmWisdomInterviews> insert(@RequestBody JmWisdomInterviews obj) {
		JmWisdomInterviews object = service.insert(obj);
		if(object != null) {
			return new ResponseBean<JmWisdomInterviews>(ResponseStatus.SUCCESS, object);
		} else {
			return new ResponseBean<JmWisdomInterviews>(ResponseStatus.FAIL);
		}
    }
	
	@ApiOperation(value="更新：前台禁用！" +
			"后台功能，传递id，title，content，coverUrl，selectOrder，videoSelectOrder，" +
			"支持编辑时设置两种排序号，传空或不传代表不参与排序")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomInterviews")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
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
	
	@ApiOperation(value="删除：传递id,其他不传，对应视频自动被屏蔽")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomInterviews")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
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
