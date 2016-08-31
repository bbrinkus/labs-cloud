package com.brinkus.labs.cloud.service.config;

import org.junit.Test;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.bind.PropertiesConfigurationFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class SwaggerConfigBeanTest {

    @Test
    public void readFullConfiguration() throws Exception {
        Properties properties = getProperties("swaggerConfigBean-full.yaml");
        SwaggerConfigBean configBean = getConfigBean(properties);

        assertThat(configBean.getTitle(), is("Labs Cloud Config"));
        assertThat(configBean.getDescription(), is("API Documentation"));
        assertThat(configBean.getVersion(), is("1.0.0"));
        assertThat(configBean.getTermsOfService(), notNullValue());
        assertThat(configBean.getTermsOfService().getUrl(), is("ToS;DR"));
        assertThat(configBean.getContact(), notNullValue());
        assertThat(configBean.getContact().getName(), is("Balazs Brinkus"));
        assertThat(configBean.getContact().getUrl(), is("http://www.brinkus.com"));
        assertThat(configBean.getContact().getEmail(), is("balazs@brinkus.com"));
        assertThat(configBean.getLicense(), notNullValue());
        assertThat(configBean.getLicense().getName(), is("GPLv3"));
        assertThat(configBean.getLicense().getUrl(), is("http://www.gnu.org/licenses/"));
    }

    @Test
    public void readMiscConfiguration() throws Exception {
        Properties properties = getProperties("swaggerConfigBean-misc.yaml");
        SwaggerConfigBean configBean = getConfigBean(properties);

        assertThat(configBean.getTitle(), is("Labs Cloud Config"));
        assertThat(configBean.getDescription(), is("API Documentation"));
        assertThat(configBean.getVersion(), is("1.0.0"));
        assertThat(configBean.getTermsOfService(), nullValue());
        assertThat(configBean.getContact(), notNullValue());
        assertThat(configBean.getContact().getName(), is("Balazs Brinkus"));
        assertThat(configBean.getContact().getUrl(), nullValue());
        assertThat(configBean.getContact().getEmail(), is("balazs@brinkus.com"));
        assertThat(configBean.getLicense(), notNullValue());
        assertThat(configBean.getLicense().getName(), is("GPLv3"));
        assertThat(configBean.getLicense().getUrl(), nullValue());
    }

    @Test
    public void readMinimalConfiguration() throws Exception {
        Properties properties = getProperties("swaggerConfigBean-minimal.yaml");
        SwaggerConfigBean configBean = getConfigBean(properties);

        assertThat(configBean.getTitle(), is("Labs Cloud Config"));
        assertThat(configBean.getDescription(), is("API Documentation"));
        assertThat(configBean.getVersion(), nullValue());
        assertThat(configBean.getTermsOfService(), nullValue());
        assertThat(configBean.getContact(), nullValue());
        assertThat(configBean.getLicense(), nullValue());
    }

    @Test
    public void readEmptyConfiguration() throws Exception {
        Properties properties = getProperties("swaggerConfigBean-empty.yaml");
        SwaggerConfigBean configBean = getConfigBean(properties);

        assertThat(configBean.getTitle(), nullValue());
        assertThat(configBean.getDescription(), nullValue());
        assertThat(configBean.getVersion(), nullValue());
        assertThat(configBean.getTermsOfService(), nullValue());
        assertThat(configBean.getContact(), nullValue());
        assertThat(configBean.getLicense(), nullValue());
    }

    private Properties getProperties(final String propertyFile) throws IOException {
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource(propertyFile));
        return yaml.getObject();
    }

    private SwaggerConfigBean getConfigBean(final Properties properties) throws Exception {
        PropertiesConfigurationFactory<SwaggerConfigBean> factory = new PropertiesConfigurationFactory<>(SwaggerConfigBean.class);
        factory.setProperties(properties);
        factory.setTargetName("labs.swagger");
        factory.bindPropertiesToTarget();
        return factory.getObject();
    }

}
