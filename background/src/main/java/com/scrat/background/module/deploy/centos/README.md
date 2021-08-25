

```shell script
yum update

yum install nodejs
npm install pm2 -g

yum install nginx
sudo systemctl enable nginx
sudo systemctl start nginx

yum install java-latest-openjdk
java --version

yum install git
```

```shell script
wget http://repo.mysql.com/mysql80-community-release-el8.rpm
rpm -ivh mysql80-community-release-el8.rpm
yum update
sudo yum install mysql-server
sudo systemctl enable mysqld
sudo systemctl start mysqld

mysql -uroot -p

CREATE user your_username@'%' IDENTIFIED BY 'your_password';
GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,ALTER ON your_database.* TO your_username@'%';
FLUSH PRIVILEGES;

vim /etc/nginx/nginx.conf

stream {
    upstream mysql {
      server 127.0.0.1:3306;
    }
    
    server {
        listen 53306;
        proxy_responses 1;
        proxy_connect_timeout 10s;
        proxy_pass mysql;
    }
}
```

```shell script
yum install redis
vim /etc/redis.conf

requirepass your_password

sudo systemctl enable redis
sudo systemctl start redis
```

```shell script
vim vim /etc/ssh/ssh_config

Host *
    ServerAliveInterval 60
```