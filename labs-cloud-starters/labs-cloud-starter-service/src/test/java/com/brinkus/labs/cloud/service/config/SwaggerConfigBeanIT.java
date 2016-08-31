package com.brinkus.labs.cloud.service.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { CloudStarterTestConfig.class }, initializers = ConfigFileApplicationContextInitializer.class)
@ActiveProfiles("it")
public class SwaggerConfigBeanIT {

    @Autowired
    SwaggerConfigBean configBean;

    @Test
    public void testConfigurationLoaded() {
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

}
