# spotride
Spotride Core


## Project run info:
### Project build:
```
./gradlew build
```

### Build a new image:
```
docker build --tag=spotride:latest . 
```

### Run docker compose:
```
docker-compose up
```
or

```
./start.sh
./start.bat
```

## Actuator Info
### Actuator is run on port `8081`
```
* Shutdown application  curl -X POST localhost:8081/actuator/shutdown
* Healthcheck endpoint /actuator/health
* Info endpoint /actuator/info
* Liquibase info endpoint /actuator/liquibase
* Metrics endpoint /actuator/metrics
* Prometheus metrics endpoint /actuator/prometheus
```

## Open-Api info
```
* Api doc link /api-doc
* Swagger link /swagger-ui/index.html
```