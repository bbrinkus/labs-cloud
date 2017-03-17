package com.brinkus.labs.cloud.service.config;

import org.junit.Test;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.bind.PropertiesConfigurationFactory;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class CacheConfigBeanTest {

    @Test
    public void readFullConfiguration() throws Exception {
        Properties properties = getProperties("cacheConfigBean-full.yaml");
        CacheConfigBean configBean = getConfigBean(properties);

        assertThat(configBean.getCaches(), notNullValue());
        assertThat(configBean.getCaches().size(), is(6));

        verifyCacheSizeMaximum(configBean.getCaches().get(0));
        verifyCacheAccessExpirationHour(configBean.getCaches().get(1));
        verifyCacheAccessExpiration(configBean.getCaches().get(2));
        verifyCacheWriteExpirationMinutes(configBean.getCaches().get(3));
        verifyCacheWriteExpirationSeconds(configBean.getCaches().get(4));
        verifyCacheFull(configBean.getCaches().get(5));
    }

    @Test
    public void readEmptyConfiguration() throws Exception {
        Properties properties = getProperties("empty.yaml");
        CacheConfigBean configBean = getConfigBean(properties);

        assertThat(configBean.isEnabled(), is(false));
    }

    private void verifyCacheSizeMaximum(final CacheConfigBean.Cache cache) {
        assertThat(cache.getName(), is("CacheSizeMaximum"));
        assertThat(cache.getMaximum().getSize(), is(1L));
        assertThat(cache.getExpirations(), nullValue());
    }

    private void verifyCacheAccessExpirationHour(final CacheConfigBean.Cache cache) {
        assertThat(cache.getName(), is("CacheAccessExpirationHour"));
        assertThat(cache.getMaximum(), nullValue());
        assertThat(cache.getExpirations(), notNullValue());
        assertThat(cache.getExpirations().hasAfterAccess(), is(true));
        assertThat(cache.getExpirations().getAfterAccess(), notNullValue());
        assertThat(cache.getExpirations().getAfterAccess().getDuration(), is(1L));
        assertThat(cache.getExpirations().getAfterAccess().getUnit(), is(TimeUnit.HOURS));
        assertThat(cache.getExpirations().hasAfterWrite(), is(false));
        assertThat(cache.getExpirations().getAfterWrite(), nullValue());
    }

    private void verifyCacheAccessExpiration(final CacheConfigBean.Cache cache) {
        assertThat(cache.getName(), is("CacheAccessExpiration"));
        assertThat(cache.getMaximum(), nullValue());
        assertThat(cache.getExpirations(), notNullValue());
        assertThat(cache.getExpirations().hasAfterAccess(), is(true));
        assertThat(cache.getExpirations().getAfterAccess(), notNullValue());
        assertThat(cache.getExpirations().getAfterAccess().getDuration(), is(1L));
        assertThat(cache.getExpirations().getAfterAccess().getUnit(), is(TimeUnit.HOURS));
        assertThat(cache.getExpirations().hasAfterWrite(), is(false));
        assertThat(cache.getExpirations().getAfterWrite(), nullValue());
    }

    private void verifyCacheWriteExpirationMinutes(final CacheConfigBean.Cache cache) {
        assertThat(cache.getName(), is("CacheWriteExpirationMinutes"));
        assertThat(cache.getMaximum(), nullValue());
        assertThat(cache.getExpirations(), notNullValue());
        assertThat(cache.getExpirations().hasAfterAccess(), is(false));
        assertThat(cache.getExpirations().getAfterAccess(), nullValue());
        assertThat(cache.getExpirations().hasAfterWrite(), is(true));
        assertThat(cache.getExpirations().getAfterWrite(), notNullValue());
        assertThat(cache.getExpirations().getAfterWrite().getDuration(), is(5L));
        assertThat(cache.getExpirations().getAfterWrite().getUnit(), is(TimeUnit.MINUTES));
    }

    private void verifyCacheWriteExpirationSeconds(final CacheConfigBean.Cache cache) {
        assertThat(cache.getName(), is("CacheWriteExpirationSeconds"));
        assertThat(cache.getMaximum(), nullValue());
        assertThat(cache.getExpirations(), notNullValue());
        assertThat(cache.getExpirations().hasAfterAccess(), is(false));
        assertThat(cache.getExpirations().getAfterAccess(), nullValue());
        assertThat(cache.getExpirations().hasAfterWrite(), is(true));
        assertThat(cache.getExpirations().getAfterWrite(), notNullValue());
        assertThat(cache.getExpirations().getAfterWrite().getDuration(), is(5L));
        assertThat(cache.getExpirations().getAfterWrite().getUnit(), is(TimeUnit.SECONDS));
    }

    private void verifyCacheFull(final CacheConfigBean.Cache cache) {
        assertThat(cache.getName(), is("CacheFull"));
        assertThat(cache.getMaximum(), notNullValue());
        assertThat(cache.getMaximum().getSize(), is(1L));
        assertThat(cache.getExpirations(), notNullValue());
        assertThat(cache.getExpirations().hasAfterAccess(), is(true));
        assertThat(cache.getExpirations().getAfterAccess(), notNullValue());
        assertThat(cache.getExpirations().getAfterAccess().getDuration(), is(1L));
        assertThat(cache.getExpirations().getAfterAccess().getUnit(), is(TimeUnit.HOURS));
        assertThat(cache.getExpirations().hasAfterWrite(), is(true));
        assertThat(cache.getExpirations().getAfterWrite(), notNullValue());
        assertThat(cache.getExpirations().getAfterWrite().getDuration(), is(5L));
        assertThat(cache.getExpirations().getAfterWrite().getUnit(), is(TimeUnit.HOURS));
    }

    private Properties getProperties(final String propertyFile) throws IOException {
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource(propertyFile));
        return yaml.getObject();
    }

    private CacheConfigBean getConfigBean(final Properties properties) throws Exception {
        MutablePropertySources propertySources = new MutablePropertySources();
        propertySources.addFirst(new PropertiesPropertySource("cache", properties));

        PropertiesConfigurationFactory<CacheConfigBean> factory = new PropertiesConfigurationFactory<>(CacheConfigBean.class);
        factory.setPropertySources(propertySources);
        factory.setTargetName("labs.cache");
        factory.bindPropertiesToTarget();
        return factory.getObject();
    }

}
