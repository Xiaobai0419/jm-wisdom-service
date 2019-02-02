package com.sunfield.microframe.controller;

import com.sunfield.microframe.bean.TestJavaConfigBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apollo")
public class TestConfigFetchController {
    @Autowired
    private TestJavaConfigBean bean;

    @RequestMapping("/fetchConfig")
    public String fetchConfig() {
        System.out.println("Configs:" + bean.toString());
        return "OK";
    }
}
