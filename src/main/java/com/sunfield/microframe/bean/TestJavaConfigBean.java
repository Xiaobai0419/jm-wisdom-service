package com.sunfield.microframe.bean;


import org.springframework.beans.factory.annotation.Value;

public class TestJavaConfigBean {
    @Value("${jm_project:100}")
    private String jm_project;
    private String sunfield_dev;
    private String testx;
    @Value("${testInt:60}")
    private int testInt;
    @Value("${spring.datasource.url:680}")
    private String ano;

    @Override
    public String toString() {
        return "TestJavaConfigBean{" +
                "jm_project='" + jm_project + '\'' +
                ", sunfield_dev='" + sunfield_dev + '\'' +
                ", testx='" + testx + '\'' +
                ", testInt=" + testInt +
                ", ano='" + ano + '\'' +
                '}';
    }

    @Value("${ccx01:x}")
    public void setTestx(String testx) {
        this.testx = testx;
    }

    @Value("${testE:200}")
    public void setSunfield_dev(String sunfield_dev) {
        this.sunfield_dev = sunfield_dev;
    }

}
