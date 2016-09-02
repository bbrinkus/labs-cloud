package com.brinkus.labs.cloud.service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Configuration for caching.
 * <p>
 * Example configuration file:
 * <pre><code>
 *  labs:
 *    cache:
 *      caches:
 *        -
 *          name: CacheMaximumSize
 *          maximum:
 *            size: 1
 *        -
 *          name: CacheAccessExpiration
 *          expirations:
 *            afterAccess:
 *              duration: 1
 *              unit: hours
 * </code></pre>
 */
@Component
@ConfigurationProperties(prefix = "labs.cache")
public class CacheConfigBean {

    /**
     * Cache expiration properties. Default time unit is hours.
     */
    public static class Expiration {

        private Long duration;

        private TimeUnit unit = TimeUnit.HOURS;

        /**
         * Flag to indicate that the expiration is available.
         *
         * @return the flag value (default false)
         */
        public boolean isValid() {
            return duration != null && unit != null;
        }

        /**
         * Get the expiration duration length for the given {@link TimeUnit}.
         *
         * @return the duration length
         */
        public Long getDuration() {
            return duration;
        }

        /**
         * Set the expiration duration length for the given {@link TimeUnit}.
         *
         * @param duration
         *         the duration length
         */
        public void setDuration(final Long duration) {
            this.duration = duration;
        }

        /**
         * Get the expiration's time unit.
         *
         * @return the time unit
         */
        public TimeUnit getUnit() {
            return unit;
        }

        /**
         * Set the expiration's time unit.
         *
         * @param unit
         *         the time unit
         */
        public void setUnit(final TimeUnit unit) {
            this.unit = unit;
        }

        /**
         * Set the expiration's time unit.
         *
         * @param unit
         *         the time unit in string representation
         */
        public void setUnit(final String unit) {
            switch (unit) {
                case "seconds":
                    this.unit = TimeUnit.SECONDS;
                    break;
                case "minutes":
                    this.unit = TimeUnit.MINUTES;
                    break;
                case "days":
                    this.unit = TimeUnit.DAYS;
                    break;
                default:
                    this.unit = TimeUnit.HOURS;
                    break;
            }
        }
    }

    /**
     * The different cache expiration options as after write or after access.
     */
    public static class Expirations {

        private Expiration afterAccess;

        private Expiration afterWrite;

        /**
         * Flag to indicate that the expiration after access is available.
         *
         * @return the flag value (default false)
         */
        public boolean hasAfterAccess() {
            return afterAccess != null && afterAccess.isValid();
        }

        /**
         * Get the expiration after access.
         *
         * @return the expiration
         */
        public Expiration getAfterAccess() {
            return afterAccess;
        }

        /**
         * Set the expiration after access.
         *
         * @param afterAccess
         *         the expiration
         */
        public void setAfterAccess(final Expiration afterAccess) {
            this.afterAccess = afterAccess;
        }

        /**
         * Flag to indicate that the expiration after write is available.
         *
         * @return the flag value (default false)
         */
        public boolean hasAfterWrite() {
            return afterWrite != null && afterWrite.isValid();
        }

        /**
         * Get the expiration after write.
         *
         * @return the expiration
         */
        public Expiration getAfterWrite() {
            return afterWrite;
        }

        /**
         * Set the expiration after write.
         *
         * @param afterWrite
         *         the expiration
         */
        public void setAfterWrite(final Expiration afterWrite) {
            this.afterWrite = afterWrite;
        }
    }

    /**
     * The cache maximum values.
     */
    public static class Maximum {

        private Long size;

        /**
         * Get the maximum size of the cache.
         *
         * @return the maximum size
         */
        public Long getSize() {
            return size;
        }

        /**
         * Flag to indicate that the cache has maximum size.
         *
         * @return the flag value (default false)
         */
        public boolean hasSize() {
            return size != null;
        }

        /**
         * Set the maximum size of the cache.
         *
         * @param size
         *         the maximum size
         */
        public void setSize(final Long size) {
            this.size = size;
        }
    }

    public static class Cache {

        private String name;

        private Maximum maximum;

        private Expirations expirations;

        /**
         * Get the name of the cache.
         *
         * @return the name of the cache
         */
        @NotNull
        public String getName() {
            return name;
        }

        /**
         * Set the name of the cache.
         *
         * @param name
         *         the name of the cache
         */
        public void setName(final String name) {
            this.name = name;
        }

        /**
         * Get the cache maximum values (size or weight).
         *
         * @return the maximum values
         */
        public Maximum getMaximum() {
            return maximum;
        }

        /**
         * Flag to indicate that the cache has maximum size or weight.
         *
         * @return the flag value (default false)
         */
        public boolean hasMaximum() {
            return maximum != null && maximum.hasSize();
        }

        /**
         * Set the cache maximum values (size or weight).
         *
         * @param maximum
         *         the maximum values
         */
        public void setMaximum(final Maximum maximum) {
            this.maximum = maximum;
        }

        /**
         * Get the cache expiration values.
         *
         * @return the expiration values
         */
        public Expirations getExpirations() {
            return expirations;
        }

        /**
         * Flag to indicate that the cache has expiration.
         *
         * @return the flag value (default false)
         */
        public boolean hasExpiration() {
            return expirations != null && (expirations.hasAfterAccess() || expirations.hasAfterWrite());
        }

        /**
         * Set the cache expiration values.
         *
         * @param expirations
         *         the expiration values
         */
        public void setExpirations(final Expirations expirations) {
            this.expirations = expirations;
        }
    }

    private boolean enabled;

    private List<Cache> caches;

    /**
     * Flag to indicate that the cache is enabled or disabled state.
     *
     * @return the flag value (default false)
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Set the cache enabled or disabled state.
     *
     * @param enabled
     *         the cache state
     */
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Get the cache configurations.
     *
     * @return the list of cache configurations
     */
    public List<Cache> getCaches() {
        return caches;
    }

    /**
     * Set the cache configurations.
     *
     * @param caches
     *         the list of cache configurations
     */
    public void setCaches(final List<Cache> caches) {
        this.caches = caches;
    }

}
