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

/**
 * Configuration for central cache management.
 */
@Configuration
@EnableCaching
public class CacheConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheConfig.class);

    /**
     * Create a new instance of {@link CacheConfig}.
     */
    public CacheConfig() {
        LOGGER.info("Initialize a new configuration instance {}", getClass().getSimpleName());
    }

    /**
     * Get the central cache manager from the external configuration information.
     *
     * @param config
     *         the cache config bean
     *
     * @return the {@link CacheManager} instance
     */
    @Bean
    public CacheManager cacheManager(final CacheConfigBean config) {
        validate(config);
        List<GuavaCache> guavaCaches = new ArrayList<>();

        for (CacheConfigBean.Cache cache : config.getCaches()) {
            CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();

            if (cache.hasMaximum()) {
                CacheConfigBean.Maximum maximum = cache.getMaximum();
                if (maximum.hasSize()) {
                    cacheBuilder.maximumSize(maximum.getSize());
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

    private void validate(final CacheConfigBean config) {
        if (config.getCaches().isEmpty()) {
            throw new InvalidCacheConfigurationException("The cache configuration must contain minimum one element!");
        }
        for (CacheConfigBean.Cache cache : config.getCaches()) {
            if (cache.getName() == null || cache.getName().isEmpty()) {
                throw new InvalidCacheConfigurationException("The cache must have a valid name!");
            }
            if (!cache.hasMaximum() && !cache.hasExpiration()) {
                throw new InvalidCacheConfigurationException("The cache must contain a maximum or an expiration value!");
            }
        }
    }

}