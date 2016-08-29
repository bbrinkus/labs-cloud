package com.brinkus.labs.cloud.service.config;

import com.google.common.io.Resources;
import org.junit.Test;
import org.springframework.boot.bind.PropertiesConfigurationFactory;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;
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
        assertThat(configBean.getTermsOfServiceUrl(), is("ToS;DR"));
        assertThat(configBean.getContactName(), is("Balazs Brinkus"));
        assertThat(configBean.getContactUrl(), is("http://www.brinkus.com"));
        assertThat(configBean.getContactEmail(), is("balazs@brinkus.com"));
        assertThat(configBean.getLicense(), is("GPLv3"));
        assertThat(configBean.getLicenseUrl(), is("http://www.gnu.org/licenses/"));
    }

    @Test
    public void readMinimalConfiguration() throws Exception {
        Properties properties = getProperties("swaggerConfigBean-minimal.yaml");
        SwaggerConfigBean configBean = getConfigBean(properties);

        assertThat(configBean.getTitle(), is("Labs Cloud Config"));
        assertThat(configBean.getDescription(), is("API Documentation"));
        assertThat(configBean.getVersion(), nullValue());
        assertThat(configBean.getTermsOfServiceUrl(), nullValue());
        assertThat(configBean.getContactName(), nullValue());
        assertThat(configBean.getContactUrl(), nullValue());
        assertThat(configBean.getContactEmail(), nullValue());
        assertThat(configBean.getLicense(), nullValue());
        assertThat(configBean.getLicenseUrl(), nullValue());
    }

    @Test
    public void readEmptyConfiguration() throws Exception {
        Properties properties = getProperties("swaggerConfigBean-empty.yaml");
        SwaggerConfigBean configBean = getConfigBean(properties);

        assertThat(configBean.getTitle(), nullValue());
        assertThat(configBean.getDescription(), nullValue());
        assertThat(configBean.getVersion(), nullValue());
        assertThat(configBean.getTermsOfServiceUrl(), nullValue());
        assertThat(configBean.getContactName(), nullValue());
        assertThat(configBean.getContactUrl(), nullValue());
        assertThat(configBean.getContactEmail(), nullValue());
        assertThat(configBean.getLicense(), nullValue());
        assertThat(configBean.getLicenseUrl(), nullValue());
    }

    private Properties getProperties(final String propertyFile) throws IOException {
        URL resource = Resources.getResource(propertyFile);
        Properties properties = new Properties();
        properties.load(new FileReader(resource.getFile()));
        return properties;
    }

    private SwaggerConfigBean getConfigBean(final Properties properties) throws Exception {
        PropertiesConfigurationFactory<SwaggerConfigBean> factory = new PropertiesConfigurationFactory<>(SwaggerConfigBean.class);
        factory.setProperties(properties);
        factory.bindPropertiesToTarget();
        return factory.getObject();
    }

}
