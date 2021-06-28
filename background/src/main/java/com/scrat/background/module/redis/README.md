## Redis

* Redis config

```
requirepass 3y3IQezN
```

* Add dependence in `build.gradle`

```groovy
implementation 'org.springframework.boot:spring-boot-starter-data-redis'
```

* Configuration in Spring Boot `application.yml`

```yaml
spring:
  redis:
    database: 0
    host: 127.0.0.1
    port: 6379
    password: 3y3IQezN
    timeout: 6m
```