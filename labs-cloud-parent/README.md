# Labs Cloud Parent Pom

Parent pom for Spring Cloud projects

## Plugin Build Configurations

### Single Assembly

Create and executable jar with the required dependencies.

```
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <configuration>
        <layout>JAR</layout>
    </configuration>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>repackage</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

### Docker 

Create docker image.

```
 <plugin>
    <groupId>com.spotify</groupId>
    <artifactId>docker-maven-plugin</artifactId>
    <configuration>
        <dockerDirectory>${project.basedir}</dockerDirectory>
        <serverId>docker-hub</serverId>
        <resources>
            <resource>
                <targetPath>/</targetPath>
                <directory>${project.build.directory}</directory>
                <include>${project.build.finalName}.jar</include>
            </resource>
        </resources>
    </configuration>
    <executions>
        <execution>
            <id>docker-image-build</id>
            <phase>install</phase>
            <goals>
                <goal>build</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

#### Settings.xml content

Password encryption: https://maven.apache.org/guides/mini/guide-encryption.html

```
<settings xmlns="http://maven.apache.org/POM/4.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
...
    <servers>
        <server>
            <id>docker-hub</id>
            <username>{docker user}</username>
            <password>{docker mvn encrypted password}</password>
            <configuration>
                <email>{docker email address</email>
            </configuration>
        </server>
    </servers>
...
</settings>

```

### Replacer

Replace a file content with a new value (e.g.: build version in the banner).

```
<properties>
    <maven.build.timestamp.format>yyyyMMdd-HHmm</maven.build.timestamp.format>
    <build.version>${maven.build.timestamp}</build.version>
</properties>
 
<plugin>
    <groupId>com.google.code.maven-replacer-plugin</groupId>
    <artifactId>replacer</artifactId>
    <configuration>
        <file>${project.build.outputDirectory}/banner.txt</file>
        <replacements>
            <replacement>
                <token>version</token>
                <value>${build.version}</value>
            </replacement>
        </replacements>
    </configuration>
    <executions>
        <execution>
            <phase>process-resources</phase>
            <goals>
                <goal>replace</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```
