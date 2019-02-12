package com.sunfield.microframe.feign;

import com.sunfield.microframe.common.response.ResponseBean;
import com.sunfield.microframe.domain.JmAppUser;
import com.sunfield.microframe.domain.JmIndustries;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "jm-industry-service",url = "http://192.168.4.152:18088")//轮询算法和以主机方式注册造成的别人机器的服务调用失败，这里暂时强制指定了自己机器，上线时应全部ip方式注册，修改轮询算法和超时、断路参数避免调用失败，可迅速转移到可调通的机器上，而不是直接失败
public interface JmIndustriesFeignService {

    //视自身业务需要调用其他服务的一些接口，而不是全部罗列，智库服务仅需要行业列表（无分页）信息，和根据行业id显示行业名称（智库问答后台管理功能）
    @RequestMapping(value = "/JmIndustries/findOne", method = RequestMethod.POST)
    ResponseBean<JmIndustries> findOne(@RequestBody JmIndustries obj);//封装的Bean如ResponseBean、JmAppUser需要与user微服务中所使用的完全一致，否则容易出现序列化错误或注入失败，建议引入统一sdk

    @RequestMapping(value = "/JmIndustries/findList", method = RequestMethod.POST)
    ResponseBean<List<JmIndustries>> findList(@RequestBody JmIndustries obj);
}
