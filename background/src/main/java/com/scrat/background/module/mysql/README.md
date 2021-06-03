## JDBC

* Add dependence in `build.gradle`

```groovy
dependencies {
    // ...
    implementation 'org.springframework.boot:spring-boot-starter-jdbc'
    implementation 'mysql:mysql-connector-java:8.0.25'
}
```

* Create table in your database

```sql
CREATE DATABASE test;
USE test;

CREATE TABLE book (
    book_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    book_name VARCHAR(64) NOT NULL DEFAULT '' COMMENT 'Book name',
    price INT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Book price',
    PRIMARY KEY (book_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='Book table';

INSERT IGNORE INTO book SET book_id=1,book_name='Clean Code',price=123;
```

* Configuration in Spring Boot `application.yml`

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/test?characterEncoding=utf8&useSSL=false
    username: your_database_user
    password: your_database_password

logging:
  level:
    org:
      springframework:
        jdbc:
          core: TRACE
```