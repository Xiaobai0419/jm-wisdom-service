package com.sunfield.microframe.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import com.sunfield.microframe.common.response.Page;
import com.sunfield.microframe.common.response.ResponseBean;
import com.sunfield.microframe.common.response.ResponseStatus;

import com.sunfield.microframe.domain.JmWisdomQuestions;
import com.sunfield.microframe.service.JmWisdomQuestionsService;

/**
 * jm_wisdom_questions rest
 * @author sunfield coder
 * 所有业务代码写在Service层，Controller层只保留对业务的调用
 */
@Slf4j
@Api(tags = "jm-wisdom-questions")
@RestController
@RequestMapping(value = "/JmWisdomQuestions")
public class JmWisdomQuestionsRest {
	
	@Autowired
	private JmWisdomQuestionsService service;

	@ApiOperation(value="查询列表：精品查询，selectOrder字段传1，其他不传")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomQuestions")
	@RequestMapping(value = "/findList", method = RequestMethod.POST)
    public ResponseBean<List<JmWisdomQuestions>> findList(@RequestBody JmWisdomQuestions obj) {
		List<JmWisdomQuestions> list = service.findList(obj);
		if(!list.isEmpty()) {
			return new ResponseBean<List<JmWisdomQuestions>>(ResponseStatus.SUCCESS, list);
		} else {
			return new ResponseBean<List<JmWisdomQuestions>>(ResponseStatus.NO_DATA);
		}
    }

	@ApiOperation(value="分页查询：前台：按行业显示，industryId字段传行业id，visitUserId（未登录访问可不传），传递分页信息，其他不传" +
			"后台：全行业分页列表显示，传递分页信息，其他不传；")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomQuestions")
	@RequestMapping(value = "/findPage", method = RequestMethod.POST)
    public ResponseBean<Page<JmWisdomQuestions>> findPage(@RequestBody JmWisdomQuestions obj) {
		return new ResponseBean<Page<JmWisdomQuestions>>(ResponseStatus.SUCCESS, service.findPage(obj));
    }

	@ApiOperation(value="分页查询：后台：全行业分页列表搜索显示，传递搜索关键字、分页信息，其他不传")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userIds", value = "", required = false, dataType = "String", allowMultiple = true),
			@ApiImplicitParam(name = "dateStart", value = "", required = false, dataType = "Date"),
			@ApiImplicitParam(name = "dateEnd", value = "", required = false, dataType = "Date"),
			@ApiImplicitParam(name = "industryId", value = "", required = false, dataType = "String"),
			@ApiImplicitParam(name = "selectOrder", value = "", required = false, dataType = "int"),
			@ApiImplicitParam(name = "pageNumber", value = "", required = true, dataType = "int"),
			@ApiImplicitParam(name = "pageSize", value = "", required = true, dataType = "int")
	})
	@RequestMapping(value = "/findSearchedPage", method = RequestMethod.POST)
	public ResponseBean<Page<JmWisdomQuestions>> findPage(String[] userIds,Date dateStart,Date dateEnd,
														  String industryId,Integer selectOrder,
														  int pageNumber, int pageSize) {
		return new ResponseBean<Page<JmWisdomQuestions>>(ResponseStatus.SUCCESS,service
				.findByUserIdsPage(userIds, dateStart, dateEnd,industryId,selectOrder,
						pageNumber, pageSize));
	}

	@ApiOperation(value="根据主键查询：传递id，visitUserId（未登录访问可不传）,其他不传")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomQuestions")
	@RequestMapping(value = "/findOne", method = RequestMethod.POST)
    public ResponseBean<JmWisdomQuestions> findOne(@RequestBody JmWisdomQuestions obj) {
    	if(StringUtils.isBlank(obj.getId())) {
			return new ResponseBean<JmWisdomQuestions>(ResponseStatus.PARAMS_ERROR);
    	}
    	JmWisdomQuestions jmWisdomQuestions = service.findOne(obj);
    	if(jmWisdomQuestions != null) {
    		return new ResponseBean<JmWisdomQuestions>(ResponseStatus.SUCCESS, jmWisdomQuestions);
    	} else {
    		return new ResponseBean<JmWisdomQuestions>(ResponseStatus.NO_DATA);
		}
    }
	
	@ApiOperation(value="新增：传递content，industryId，userId，mediaType（1图片 2视频），ossUrls，title，其他不传")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomQuestions")
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseBean<JmWisdomQuestions> insert(@RequestBody JmWisdomQuestions obj) {
		JmWisdomQuestions object = service.insert(obj);
		if(object != null) {
			ResponseBean<JmWisdomQuestions> responseBean = new ResponseBean<JmWisdomQuestions>(ResponseStatus.SUCCESS, object);
			responseBean.setMsg("发布成功");
			return responseBean;
		} else {
			ResponseBean<JmWisdomQuestions> responseBean = new ResponseBean<JmWisdomQuestions>(ResponseStatus.FAIL);
			responseBean.setMsg("发布失败");
			return responseBean;
		}
    }
	
	@ApiOperation(value="更新：设置精品排序，后台管理功能，传id，" +
			"selectOrder字段传需要设置的序号，其他不传；不允许其他功能使用！")
	@ApiImplicitParam(name = "obj", value = "", required = true, dataType = "JmWisdomQuestions")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
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
