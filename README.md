*Educational purpose. Service for inputting auto parts purchases*

## Deps

1. jdk-17
2. maven2
3. docker-compose

## Starting

Build an application and then run docker-compose
```
mvn clean package -P build
```
```
docker-compose up
```

## API
http://localhost:8080/sadbe/api/swagger-ui/index.html