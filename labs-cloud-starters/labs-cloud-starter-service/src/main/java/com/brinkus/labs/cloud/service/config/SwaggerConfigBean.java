package com.brinkus.labs.cloud.service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Swagger configuration bean to load external configuration.
 * <p>
 * External configuration file:
 * <pre><code>
 *  labs:
 *    swagger:
 *      enabled: true
 *      title: Labs Cloud Config
 *      description: API Documentation
 *      version: 1.0.0
 *      termsOfService:
 *        url: ToS;DR
 *      contact:
 *        name: Balazs Brinkus
 *        url: http://www.brinkus.com
 *        email: balazs@brinkus.com
 *      license:
 *        name: GPLv3
 *        url: http://www.gnu.org/licenses/
 * </code></pre>
 */
@Component
@ConfigurationProperties(prefix = "labs.swagger")
public class SwaggerConfigBean {

    /**
     * Swagger contact information.
     */
    public static class Contact {

        private String name;

        private String url;

        private String email;

        /**
         * Get the name of the contact person/organization.
         *
         * @return the contact name
         */
        public String getName() {
            return name;
        }

        /**
         * Set the name of the contact person/organization.
         *
         * @param name
         *         the contact name
         */
        public void setName(final String name) {
            this.name = name;
        }

        /**
         * Get the url of the contact person/organization.
         *
         * @return the contact url
         */
        public String getUrl() {
            return url;
        }

        /**
         * Set the url of the contact person/organization.
         *
         * @param url
         *         the contact url
         */
        public void setUrl(final String url) {
            this.url = url;
        }

        /**
         * Get the email address of the contact person/organization.
         *
         * @return the contact email address
         */
        public String getEmail() {
            return email;
        }

        /**
         * Set the email address of the contact person/organization.
         *
         * @param email
         *         the contact email address
         */
        public void setEmail(final String email) {
            this.email = email;
        }
    }

    /**
     * Swagger license information.
     */
    public static class License {

        private String name;

        private String url;

        /**
         * Get the name of the API license.
         *
         * @return the API license name
         */
        public String getName() {
            return name;
        }

        /**
         * Set the name of the API license.
         *
         * @param name
         *         the API license name
         */
        public void setName(final String name) {
            this.name = name;
        }

        /**
         * Get the url of the API license.
         *
         * @return the API license url
         */
        public String getUrl() {
            return url;
        }

        /**
         * Set the url of the API license.
         *
         * @param url
         *         the API license url
         */
        public void setUrl(final String url) {
            this.url = url;
        }
    }

    /**
     * Swagger terms of service information.
     */
    public static class TermsOfService {

        private String url;

        /**
         * Get the url of the terms of service.
         *
         * @return the terms of service license url
         */
        public String getUrl() {
            return url;
        }

        /**
         * Set the url of the terms of service.
         *
         * @return the terms of service license url
         */
        public void setUrl(final String url) {
            this.url = url;
        }
    }

    private boolean enabled;

    private String title;

    private String description;

    private String version;

    private TermsOfService termsOfService;

    private Contact contact;

    private License license;

    /**
     * Flag to indicate that the swagger is enabled or disabled state.
     *
     * @return the flag value (default false)
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Set the swagger enabled or disabled state.
     *
     * @param enabled
     *         the cache state
     */
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Get the title of API.
     *
     * @return the API title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the title of API.
     *
     * @param title
     *         the API title
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * Get a detailed description of the API.
     *
     * @return the API description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set a detailed description of the API.
     *
     * @param description
     *         the API description
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Get the version of the API.
     *
     * @return the API version
     */
    public String getVersion() {
        return version;
    }

    /**
     * Set the version of the API.
     *
     * @param version
     *         the API version
     */
    public void setVersion(final String version) {
        this.version = version;
    }

    /**
     * Get the terms of service of the API.
     *
     * @return the term of service
     */
    public TermsOfService getTermsOfService() {
        return termsOfService;
    }

    /**
     * Flag to indicate that the terms of service information is available.
     *
     * @return the flag value (default false)
     */
    public boolean hasTermsOfService() {
        return termsOfService != null;
    }

    /**
     * Set the terms of service of the API.
     *
     * @param termsOfService
     *         the term of service
     */
    public void setTermsOfService(final TermsOfService termsOfService) {
        this.termsOfService = termsOfService;
    }

    /**
     * Get the contact information of the API.
     *
     * @return the contact information
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * Flag to indicate that the contact information is available.
     *
     * @return the flag value (default false)
     */
    public boolean hasContact() {
        return contact != null;
    }

    /**
     * Set the contact information of the API.
     *
     * @param contact
     *         the contact information
     */
    public void setContact(final Contact contact) {
        this.contact = contact;
    }

    /**
     * Get the license information of the API.
     *
     * @return the license information
     */
    public License getLicense() {
        return license;
    }

    /**
     * Flag to indicate that the license information is available.
     *
     * @return the flag value (default false)
     */
    public boolean hasLicense() {
        return license != null;
    }

    /**
     * Set the license information of the API.
     *
     * @param license
     *         the license information
     */
    public void setLicense(final License license) {
        this.license = license;
    }
}
