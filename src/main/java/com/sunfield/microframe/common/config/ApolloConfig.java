package com.sunfield.microframe.common.config;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.sunfield.microframe.bean.TestJavaConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableApolloConfig(value = {"TEST1.sunfield"}, order = 1)
public class ApolloConfig {
    @Bean
    public TestJavaConfigBean javaConfigBean() {
        return new TestJavaConfigBean();
    }
}
