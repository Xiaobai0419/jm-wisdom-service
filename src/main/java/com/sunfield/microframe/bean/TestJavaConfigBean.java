package com.sunfield.microframe.bean;


import org.springframework.beans.factory.annotation.Value;

public class TestJavaConfigBean {
    @Value("${jm_project:100}")
    private String jm_project;
    private String sunfield_dev;
    private String testx;
    @Value("${testInt:60}")
    private int testInt;

    @Value("${testx:x}")
    public void setTestx(String testx) {
        this.testx = testx;
    }

    @Value("${sunfield_dev:200}")
    public void setSunfield_dev(String sunfield_dev) {
        this.sunfield_dev = sunfield_dev;
    }

    @Override
    public String toString() {
        return "TestJavaConfigBean{" +
                "jm_project='" + jm_project + '\'' +
                ", sunfield_dev='" + sunfield_dev + '\'' +
                ", testx='" + testx + '\'' +
                ", testInt=" + testInt +
                '}';
    }
}
