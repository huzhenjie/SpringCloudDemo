```groovy
dependencies {
    // ...
    implementation 'org.springframework.boot:spring-boot-starter-actuator'
}
```

```yaml
info:
  app:
    name: ${spring.application.name}
    port: ${server.port}
    active: ${spring.profiles.active}
    java: ${java.version}
```

http://127.0.0.1:18001/actuator/info