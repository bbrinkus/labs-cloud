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

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { CloudStarterTestConfig.class }, initializers = ConfigFileApplicationContextInitializer.class)
@ActiveProfiles("it")
@WebAppConfiguration
public class CacheConfigBeanIT {

    @Autowired
    CacheConfigBean configBean;

    @Test
    public void configurationSuccess() {
        assertThat(configBean, Matchers.notNullValue());
    }

    @Test
    public void testConfigurationLoaded() {
        assertThat(configBean.isEnabled(), is(true));
        assertThat(configBean.getCaches(), notNullValue());
        assertThat(configBean.getCaches().size(), is(2));

        CacheConfigBean.Cache cache = configBean.getCaches().get(0);
        assertThat(cache.getName(), is("CacheMaximum"));
        assertThat(cache.hasMaximum(), is(true));
        assertThat(cache.getMaximum(), notNullValue());
        assertThat(cache.getMaximum().getSize(), is(10L));
        assertThat(cache.hasExpiration(), is(false));
        assertThat(cache.getExpirations(), nullValue());

        cache = configBean.getCaches().get(1);
        assertThat(cache.getName(), is("CacheExpiration"));
        assertThat(cache.hasMaximum(), is(false));
        assertThat(cache.getMaximum(), nullValue());
        assertThat(cache.hasExpiration(), is(true));
        assertThat(cache.getExpirations(), notNullValue());
        assertThat(cache.getExpirations().hasAfterAccess(), is(true));
        assertThat(cache.getExpirations().getAfterAccess(), notNullValue());
        assertThat(cache.getExpirations().getAfterAccess().getDuration(), is(5L));
        assertThat(cache.getExpirations().getAfterAccess().getUnit(), is(TimeUnit.HOURS));
        assertThat(cache.getExpirations().hasAfterWrite(), is(false));
        assertThat(cache.getExpirations().getAfterWrite(), nullValue());
    }

}
