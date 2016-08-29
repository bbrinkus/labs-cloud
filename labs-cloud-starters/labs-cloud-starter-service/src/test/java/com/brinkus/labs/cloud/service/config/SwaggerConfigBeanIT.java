package com.brinkus.labs.cloud.service.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
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
        assertThat(configBean.getTermsOfServiceUrl(), is("ToS;DR"));
        assertThat(configBean.getContactName(), is("Balazs Brinkus"));
        assertThat(configBean.getContactUrl(), is("http://www.brinkus.com"));
        assertThat(configBean.getContactEmail(), is("balazs@brinkus.com"));
        assertThat(configBean.getLicense(), is("GPLv3"));
        assertThat(configBean.getLicenseUrl(), is("http://www.gnu.org/licenses/"));
    }

}
