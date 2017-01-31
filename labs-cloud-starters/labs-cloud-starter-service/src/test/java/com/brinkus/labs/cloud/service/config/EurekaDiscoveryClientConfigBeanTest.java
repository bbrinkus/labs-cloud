/*
 * Labs Cloud Starter Service
 * Copyright (C) 2016  Balazs Brinkus
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.brinkus.labs.cloud.service.config;

import org.junit.Test;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.bind.PropertiesConfigurationFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EurekaDiscoveryClientConfigBeanTest {

    @Test
    public void readFullConfiguration() throws Exception {
        Properties properties = getProperties("eurekaConfigBean-full.yaml");
        EurekaDiscoveryClientConfigBean configBean = getConfigBean(properties);

        assertThat(configBean.isEnabled(), is(true));
    }

    @Test
    public void readEmptyConfiguration() throws Exception {
        Properties properties = getProperties("swaggerConfigBean-empty.yaml");
        EurekaDiscoveryClientConfigBean configBean = getConfigBean(properties);

        assertThat(configBean.isEnabled(), is(false));
    }

    private Properties getProperties(final String propertyFile) throws IOException {
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource(propertyFile));
        return yaml.getObject();
    }

    private EurekaDiscoveryClientConfigBean getConfigBean(final Properties properties) throws Exception {
        PropertiesConfigurationFactory<EurekaDiscoveryClientConfigBean> factory = new PropertiesConfigurationFactory<>(EurekaDiscoveryClientConfigBean.class);
        factory.setProperties(properties);
        factory.setTargetName("labs.eureka");
        factory.bindPropertiesToTarget();
        return factory.getObject();
    }

}
