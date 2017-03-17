package com.brinkus.labs.cloud.service.config;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { CloudStarterTestConfig.class }, initializers = ConfigFileApplicationContextInitializer.class)
@ActiveProfiles("it")
@WebAppConfiguration
public class EurekaDiscoveryClientConfigBeanIT {

    @Autowired
    EurekaDiscoveryClientConfigBean configBean;

    @Test
    public void configurationSuccess() {
        assertThat(configBean, Matchers.notNullValue());
    }

    @Test
    public void testConfigurationLoaded() {
        assertThat(configBean.isEnabled(), is(false));
    }

}
