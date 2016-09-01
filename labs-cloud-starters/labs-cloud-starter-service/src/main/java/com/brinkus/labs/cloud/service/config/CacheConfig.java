package com.brinkus.labs.cloud.service.config;

import com.brinkus.labs.cloud.service.exception.InvalidCacheConfigurationException;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableCaching
public class CacheConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheConfig.class);

    public CacheConfig() {
        LOGGER.info("Initialize a new configuration instance {}", getClass().getSimpleName());
    }

    @Bean
    public CacheManager cacheManager(final CacheConfigBean config) {
        List<GuavaCache> guavaCaches = new ArrayList<>();

        for (CacheConfigBean.Cache cache : config.getCaches()) {
            CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();

            if (cache.hasMaximum()) {
                CacheConfigBean.Maximum maximum = cache.getMaximum();
                if (maximum.hasSize()) {
                    cacheBuilder.maximumSize(maximum.getSize());
                }
                if (maximum.hasWeight()) {
                    cacheBuilder.maximumWeight(maximum.getWeight());
                }
            }

            if (cache.hasExpiration()) {
                CacheConfigBean.Expirations expirations = cache.getExpirations();
                if (expirations.hasAfterAccess()) {
                    CacheConfigBean.Expiration afterAccess = expirations.getAfterAccess();
                    cacheBuilder.expireAfterAccess(afterAccess.getDuration(), afterAccess.getUnit());
                }
                if (expirations.hasAfterWrite()) {
                    CacheConfigBean.Expiration afterWrite = expirations.getAfterWrite();
                    cacheBuilder.expireAfterWrite(afterWrite.getDuration(), afterWrite.getUnit());
                }
            }

            GuavaCache guavaCache = new GuavaCache(cache.getName(), cacheBuilder.build());
            guavaCaches.add(guavaCache);
        }

        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(guavaCaches);
        return cacheManager;
    }

}