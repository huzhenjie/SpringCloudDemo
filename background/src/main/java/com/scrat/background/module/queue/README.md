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

* Add user

```shell script
rabbitmqctl add_user username password
```

* Add permission

```shell script
rabbitmqctl set_user_tags username administrator
rabbitmqctl set_permissions -p / username ".*" ".*" ".*"
```

* [Read more](https://www.rabbitmq.com/cli.html#overview)

## Bind to all network interfaces

```shell script
vim /usr/local/etc/rabbitmq/rabbitmq-env.conf 
```

* set `NODE_IP_ADDRESS`

```
NODE_IP_ADDRESS=
```

## Enable delayed message

* [Read more](https://github.com/rabbitmq/rabbitmq-delayed-message-exchange)

```shell script
mv rabbitmq_delayed_message_exchange-3.8.9-0199d11c.ez /usr/local/opt/rabbitmq/plugins/
rabbitmq-plugins enable rabbitmq_delayed_message_exchange
```

## Spring Boot Messaging with RabbitMQ

* Add dependence in `build.gradle`

```groovy
dependencies {
    // ...
    implementation 'org.springframework.boot:spring-boot-starter-amqp'
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