# Labs Cloud

## Versions

* 1.6.0
    * Update 3rd party dependencies
        * Spring Boot 1.5.2.RELEASE
        * Spring Cloud Dalston.SR1
        * Neo4j OGM 2.1.3
* 1.5.1
    * JSON ignore Neo4jEntityBase id property
* 1.5.0
    * Update 3rd party dependencies
        * Spring Boot 1.5.1.RELEASE
        * Spring Cloud Camden.SR5
        * Neo4j OGM 2.1.1
    * Update classes to use the new dependencies
    * Neo4j internal package automatically added to the package list
* 1.4.0
    * Rename discovery client configuration from EurekaClient(.*) to EurekaDiscoveryClient(.*)
    * Introduce new config bean for the discovery client
* 1.3.0
    * Update to labs-parent 1.2.0
* 1.2.2
    * Add Eureka Client configuration condition  
* 1.2.1
    * Fix invalid Neo4j DAO name 
* 1.2.0
    * Add new starter for Neo4j with discovery service support (HTTP driver only)
* 1.1.0 
    * Baseline
    
## Projects

Labs cloud projects. More details about the projects in the linked documentation files.

Project                    | Documentation
-------------------------- | ----------------------------------------
Labs Cloud Parent          | [README.md](labs-cloud-parent/README.md)
Labs Cloud Starter Test    | [README.md](labs-cloud-starters/labs-cloud-starter-test/README.md)
Labs Cloud Starter Service | [README.md](labs-cloud-starters/labs-cloud-starter-service/README.md)