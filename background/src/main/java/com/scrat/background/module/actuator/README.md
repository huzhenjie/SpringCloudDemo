## Actuator

> Actuator brings production-ready features to our application

* Add dependence in `build.gradle`

```groovy
springBoot {
    buildInfo()
}

dependencies {
    // ...
    implementation 'org.springframework.boot:spring-boot-starter-actuator'
}
```

* Configuration in Spring Boot `application.yml`

```yaml
info:
  app:
    name: ${spring.application.name}
    port: ${server.port}
    active: ${spring.profiles.active}
    eureka-zone: ${eureka.client.service-url.default-zone}
    java: ${java.version}
```

* Show the `yaml` information cross the API

[http://{node-hostname}:18001/actuator/info](http://127.0.0.1:18001/actuator/info)

[Response]

```json
{
    "app": {
        "name": "background-server",
        "port": "18001",
        "active": "dev",
        "eureka-zone": "http://localhost:18000/eureka/",
        "java": "1.8.0_181"
    },
    "build": {
        "version": "0.0.1-SNAPSHOT",
        "artifact": "background",
        "name": "background",
        "group": "com.scrat",
        "time": "2021-05-25T03:39:33.662Z"
    }
}
```

* Show the actuator links for the various endpoints

[http://{node-hostname}:18001/actuator](http://127.0.0.1:18001/actuator)

[Response]

```json
{
    "_links":{
        "self":{
            "href":"http://127.0.0.1:18001/actuator",
            "templated":false
        },
        "health":{
            "href":"http://127.0.0.1:18001/actuator/health",
            "templated":false
        },
        "health-path":{
            "href":"http://127.0.0.1:18001/actuator/health/{*path}",
            "templated":true
        },
        "info":{
            "href":"http://127.0.0.1:18001/actuator/info",
            "templated":false
        }
    }
}
```