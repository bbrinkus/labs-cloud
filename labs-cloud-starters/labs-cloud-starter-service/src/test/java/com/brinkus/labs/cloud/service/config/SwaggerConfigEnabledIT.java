package com.brinkus.labs.cloud.service.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import springfox.documentation.service.ApiInfo;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { CloudStarterTestConfig.class }, initializers = ConfigFileApplicationContextInitializer.class)
@ActiveProfiles("swagger-enabled")
@WebAppConfiguration
public class SwaggerConfigEnabledIT {

    @Autowired
    ApplicationContext context;

    @Test
    public void configurationSuccess() {
        assertThat(context, notNullValue());
    }

    @Test
    public void apiInfoEnabled() {
        ApiInfo apiInfo = context.getBean(ApiInfo.class);
        assertThat(apiInfo, notNullValue());
        assertThat(apiInfo.getTitle(), is("Labs Cloud Config"));
        assertThat(apiInfo.getDescription(), is("API Documentation"));
    }

}
