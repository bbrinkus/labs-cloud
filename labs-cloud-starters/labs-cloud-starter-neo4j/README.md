# Labs Cloud Starter for Neo4j

Contains the session handler for Neo4j that support Discovery Service.

## Supported drivers

* HTTP through Eureka
* Bolt

## Integration test

Start the discovery service:
```
docker run --rm --name labs-it-eureka -p 8761:8761 bbrinkus/eureka-discovery-service
```

Setup the constants:
```
# Bash
NEO4J_IT_HOME="$(pwd)/it/neo4j-test-data"
DEV_MACHINE_IP=$(ipconfig getifaddr en0)
# Fish
set NEO4J_IT_HOME (pwd)"/it/neo4j-test-data"
set DEV_MACHINE_IP (ipconfig getifaddr en0)
```

Start the Neo4j:
```
docker run --rm --name labs-it-neo4j \
    -p 7474:7474 -p 7687:7687 -p 5005:5005 \
    --add-host=eureka:$DEV_MACHINE_IP \
    -v $NEO4J_IT_HOME/conf:/conf \
    -v $NEO4J_IT_HOME/plugins:/plugins \
    -v $NEO4J_IT_HOME/logs:/logs \
    -v $NEO4J_IT_HOME/data:/data \
    neo4j:3.3.1
```

Access to the bash shell:
```
docker exec --interactive --tty (docker ps -a | grep labs-it-neo4j | awk '{print $1}') /bin/bash
```
