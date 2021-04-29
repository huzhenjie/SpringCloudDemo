## Getting started

[RabbitMQ Tutorials](https://www.rabbitmq.com/getstarted.html)

### Install

```
$ brew update
$ brew install rabbitmq
```

### Start RabbitMQ server

```
$ rabbitmq-server
```

### Visit the manager index

[RabbitMQ Management](http://localhost:15672)

### Default account

> username: guest  
> password: guest

### Default RabbitMQ port

> 5672

## RabbitMQ Cli

### List all users

```
$ rabbitmqctl list_users
```

### Get the permission info by username `guest`

```
$ rabbitmqctl list_user_permissions guest
```