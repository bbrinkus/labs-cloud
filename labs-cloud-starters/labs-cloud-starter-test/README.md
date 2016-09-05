# Labs Cloud Starter for Testing

Contains the required dependencies to make unit and integration tests.

## Versions

* 1.0.0 
    * Baseline
    
## Content

* com.jayway.jsonpath:json-path
* com.jayway.restassured:rest-assured
* org.hamcrest:hamcrest-library
* org.mockito:mockito-all
* org.springframework.boot:spring-boot-starter-test

## Usage

```
<dependencies>
    <dependency>
        <groupId>com.brinkus.labs</groupId>
        <artifactId>labs-cloud-starter-test</artifactId>
        <version>${labs.cloud.version}</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```
