# Labs Cloud Starter for Services

Contains the configurations:
* to connect to the Eureka Discovery Service
* to use caching
* to use Swagger Documentation API

## Versions

* 1.1.0 
    * Baseline
    
## Use in AWS

To enable the AWS specific information in the Discovery Service registration add the `aws` profile to the startup.

```
-Dspring.profiles.active=aws
```

It will override the default DataCenterInfo `MyOwn` to `Amazon` also it will change the following settings:
* **instanceId**: 
    * `String.format("%s:%s:%d", instanceId, applicationName, port).toLowerCase()`
    * i-19bce96913a8a1f47:registry:8761
* **hostname**:
    * if the instance has a valid non-default hostname it will try to use it. Othwerwise it will use the AWS 
* **port**
    * Only the non-secure port is enabled at this moment

### Example registry information

```
<instance>
    <instanceId>i-19bce96913a8a1f47:registry:8761</instanceId>
    <hostName>ec2-52-48-98-132.eu-central-1.compute.amazonaws.com</hostName>
    ...
    <dataCenterInfo class="com.netflix.appinfo.AmazonInfo">
        <name>Amazon</name>
        <metadata>
            <public-ipv4>52.48.98.132</public-ipv4>
            <accountId>028623594481</accountId>
            <local-hostname>ip-10-0-1-10.eu-central-1.compute.internal</local-hostname>
            <public-hostname>ec2-52-48-98-132.eu-central-1.compute.amazonaws.com</public-hostname>
            <instance-id>i-19bce96913a8a1f47</instance-id>
            <local-ipv4>10.0.1.10</local-ipv4>
            <instance-type>t2.nano</instance-type>
            <vpc-id>vpc-deadf3a5</vpc-id>
            <ami-id>ami-fa24de81</ami-id>
            <mac>16:1d:c1:4a:e5:d8</mac>
            <availability-zone>eu-central-1b</availability-zone>
        </metadata>
    </dataCenterInfo>
    ...
</instance>
```