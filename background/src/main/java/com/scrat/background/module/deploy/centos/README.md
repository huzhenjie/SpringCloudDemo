

```shell script
yum update

yum install redis

yum install nodejs
npm install pm2 -g

yum install nginx
sudo systemctl enable nginx
sudo systemctl start nginx

yum install java-latest-openjdk
java --version

yum install git

wget http://repo.mysql.com/mysql80-community-release-el8.rpm
rpm -ivh mysql80-community-release-el8.rpm
yum update
sudo yum install mysql-server
sudo systemctl enable mysqld
sudo systemctl start mysqld
```

```shell script
vim vim /etc/ssh/ssh_config

Host *
    ServerAliveInterval 60
```