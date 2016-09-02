package com.brinkus.labs.cloud.service.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import springfox.documentation.service.ApiInfo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { CloudStarterTestConfig.class }, initializers = ConfigFileApplicationContextInitializer.class)
@ActiveProfiles("empty")
public class SwaggerConfigNotAvailableIT {

    @Autowired
    ApplicationContext context;

    @Test
    public void configurationSuccess() {
        assertThat(context, notNullValue());
    }

    @Test(expected = NoSuchBeanDefinitionException.class)
    public void apiInfoNotAvailable() {
        assertThat(context.getBean(ApiInfo.class), nullValue());
    }

}
