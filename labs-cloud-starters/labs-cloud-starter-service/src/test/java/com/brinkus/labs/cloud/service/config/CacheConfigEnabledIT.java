package com.brinkus.labs.cloud.service.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { CloudStarterTestConfig.class }, initializers = ConfigFileApplicationContextInitializer.class)
@ActiveProfiles("cache-enabled")
public class CacheConfigEnabledIT {

    @Autowired
    ApplicationContext context;

    @Test
    public void configurationSuccess() {
        assertThat(context, notNullValue());
    }

    @Test
    public void cacheManagerNotAvailable() {
        CacheManager cacheManager = context.getBean(CacheManager.class);
        assertThat(cacheManager, notNullValue());
        assertThat(cacheManager.getCacheNames().size(), is(2));
    }

}
