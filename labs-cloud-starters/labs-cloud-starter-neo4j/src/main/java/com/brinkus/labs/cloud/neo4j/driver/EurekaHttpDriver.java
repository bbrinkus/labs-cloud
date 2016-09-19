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

package com.brinkus.labs.cloud.neo4j.driver;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.neo4j.ogm.driver.AbstractConfigurableDriver;
import org.neo4j.ogm.drivers.http.request.HttpRequest;
import org.neo4j.ogm.drivers.http.transaction.HttpTransaction;
import org.neo4j.ogm.exception.ResultErrorsException;
import org.neo4j.ogm.exception.ResultProcessingException;
import org.neo4j.ogm.request.Request;
import org.neo4j.ogm.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerContext;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.net.URI;

public final class EurekaHttpDriver extends AbstractConfigurableDriver {
public final class EurekaHttpDriver extends AbstractConfigurableDriver implements EurekaHttpClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(EurekaHttpDriver.class);

    private final SpringClientFactory clientFactory;

    private CloseableHttpClient httpClient;

    public EurekaHttpDriver(final SpringClientFactory clientFactory) {
        this.clientFactory = clientFactory;
    }
    //
    //    public EurekaHttpDriver() {
    //        // nothing
    //    }
    //
    //    public EurekaHttpDriver(CloseableHttpClient httpClient) {
    //        this.httpClient = httpClient;
    //    }

    @Override
    public synchronized void close() {
        try {
            LOGGER.info("Shutting down Http driver {} ", this);
            if (httpClient != null) {
                httpClient.close();
            }
        } catch (Exception e) {
            LOGGER.warn("Unexpected Exception when closing http client httpClient: ", e);
        }
    }

    @Override
    public Request request() {
        String url = requestUrl();
        return new HttpRequest(httpClient(), url, driverConfig.getCredentials());
    }

    @Override
    public Transaction newTransaction() {
        String url = newTransactionUrl();
        return new EurekaHttpTransaction(transactionManager, this, url);
    }

    public CloseableHttpResponse executeHttpRequest(HttpRequestBase request) {
        try {
            try (CloseableHttpResponse response = HttpRequest.execute(httpClient(), request, driverConfig.getCredentials())) {
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    String responseText = EntityUtils.toString(responseEntity);
                    LOGGER.debug("Thread {}: {}", Thread.currentThread().getId(), responseText);
                    EntityUtils.consume(responseEntity);
                    if (responseText.contains("\"errors\":[{") || responseText.contains("\"errors\": [{")) {
                        throw new ResultErrorsException(responseText);
                    }
                }
                return response;
            }
        } catch (HttpResponseException hre) {
            if (hre.getStatusCode() == 404) {
                Transaction tx = transactionManager.getCurrentTransaction();
                if (tx != null) {
                    transactionManager.rollback(tx);
                }
            }
            throw new ResultProcessingException("HttpResponse exception: Not Found", hre);
        } catch (Exception e) {
            throw new ResultProcessingException("Failed to execute request: ", e);
        } finally {
            request.releaseConnection();
            LOGGER.debug("Thread {}: Connection released", Thread.currentThread().getId());
        }
    }

    private String newTransactionUrl() {
        String url = transactionEndpoint(getDiscoveryUri());
        LOGGER.debug("Thread {}: POST {}", Thread.currentThread().getId(), url);

        try (CloseableHttpResponse response = executeHttpRequest(new HttpPost(url))) {
            Header location = response.getHeaders("Location")[0];
            response.close();
            return location.getValue();
        } catch (Exception e) {
            throw new ResultProcessingException("Could not obtain new Transaction: ", e);
        }
    }

    private String autoCommitUrl() {
        return transactionEndpoint(getDiscoveryUri()).concat("/commit");
    }

    private String transactionEndpoint(String server) {
        if (server == null) {
            return null;
        }
        String url = server;

        if (!server.endsWith("/")) {
            url += "/";
        }
        return url + "db/data/transaction";
    }

    private String requestUrl() {
        if (transactionManager != null) {
            Transaction tx = transactionManager.getCurrentTransaction();
            if (tx != null) {
                LOGGER.debug("Thread {}: request url {}", Thread.currentThread().getId(), ((HttpTransaction) tx).url());
                return ((HttpTransaction) tx).url();
            } else {
                LOGGER.debug("Thread {}: No current transaction, using auto-commit", Thread.currentThread().getId());
            }
        } else {
            LOGGER.debug("Thread {}: No transaction manager available, using auto-commit", Thread.currentThread().getId());
        }
        String autoCommitUrl = autoCommitUrl();
        LOGGER.debug("Thread {}: request url {}", Thread.currentThread().getId(), autoCommitUrl);
        return autoCommitUrl;
    }

    private synchronized CloseableHttpClient httpClient() {
        if (httpClient == null) {   // most of the time this will be false, branch-prediction will be very fast and the lock released immediately

            try {
                SSLContext sslContext = SSLContext.getDefault();

                if (driverConfig.getTrustStrategy() != null && driverConfig.getTrustStrategy().equals("ACCEPT_UNSIGNED")) {
                    sslContext = new SSLContextBuilder().loadTrustMaterial(null, (arg0, arg1) -> true).build();
                    LOGGER.warn("Certificate validation has been disabled");
                }

                HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.getDefaultHostnameVerifier();

                SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
                Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", sslSocketFactory)
                        .build();

                // allows multi-threaded use
                PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
                connectionManager.setMaxTotal(driverConfig.getConnectionPoolSize());
                connectionManager.setDefaultMaxPerRoute(driverConfig.getConnectionPoolSize());

                httpClient = HttpClientBuilder.create()
                        .setSSLContext(sslContext)
                        .setConnectionManager(new PoolingHttpClientConnectionManager(socketFactoryRegistry))
                        .build();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return httpClient;
    }

    private String getDiscoveryUri() {
        try {
            String serviceId = driverConfig.getURI();

            Server server = getServer(serviceId);
            if (server == null) {
                throw new IllegalStateException(String.format("No instances available for %s", serviceId));
            }

            RibbonLoadBalancerContext context = getLoadBalancerContext(serviceId);
            URI uri = context.reconstructURIWithServer(server, new URI(driverConfig.getURI()));
            return uri.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ILoadBalancer getLoadBalancer(String serviceId) {
        return clientFactory.getLoadBalancer(serviceId);
    }

    private RibbonLoadBalancerContext getLoadBalancerContext(String serviceId) {
        return clientFactory.getLoadBalancerContext(serviceId);
    }

    private Server getServer(String serviceId) {
        return getServer(getLoadBalancer(serviceId));
    }

    private Server getServer(ILoadBalancer loadBalancer) {
        if (loadBalancer == null) {
            return null;
        }
        return loadBalancer.chooseServer("default");
    }

}
