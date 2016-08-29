package com.brinkus.labs.cloud.service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "labs.swagger")
public class SwaggerConfigBean {

    private String title;

    private String description;

    private String version;

    private String termsOfServiceUrl;

    private String contactName;

    private String contactUrl;

    private String contactEmail;

    private String license;

    private String licenseUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    public String getTermsOfServiceUrl() {
        return termsOfServiceUrl;
    }

    public void setTermsOfServiceUrl(final String termsOfServiceUrl) {
        this.termsOfServiceUrl = termsOfServiceUrl;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(final String contactName) {
        this.contactName = contactName;
    }

    public String getContactUrl() {
        return contactUrl;
    }

    public void setContactUrl(final String contactUrl) {
        this.contactUrl = contactUrl;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(final String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(final String license) {
        this.license = license;
    }

    public String getLicenseUrl() {
        return licenseUrl;
    }

    public void setLicenseUrl(final String licenseUrl) {
        this.licenseUrl = licenseUrl;
    }
}
