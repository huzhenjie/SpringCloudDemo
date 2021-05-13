## RabbitMQ

[RabbitMQ Tutorials](https://www.rabbitmq.com/getstarted.html)

* Install

```
$ brew update
$ brew install rabbitmq
```

* Start server

```
$ rabbitmq-server
```

* Management UI access

> The management UI can be accessed using a Web browser at `http://{node-hostname}:15672/`.  
> For example [http://localhost:15672](http://localhost:15672)  
> Default username: `guest`  
> Default password: `guest`  

* Default tcp port

> 5672

## Command line tools

* List all users

```
$ rabbitmqctl list_users
```

* Show the permission info

```
$ rabbitmqctl list_user_permissions guest
```

* [Read more](https://www.rabbitmq.com/cli.html#overview)

## Spring Boot Messaging with RabbitMQ

* Add dependence in `build.gradle`

```groovy
dependencies {
    // ...
    testImplementation 'org.springframework.amqp:spring-rabbit-test'
}
```

* Configuration in Spring Boot `application.yml`

```yaml
spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
    listener:
      simple:
        prefetch: 1
```