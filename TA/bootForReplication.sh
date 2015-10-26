#!/bin/bash
main(){
sudo service mysql stop
sudo mount -a
sudo mount /var/log/mysql
sudo mount /etc/mysql
sudo mount /var/lib/mysql
sudo service mysql start
cd /home/ubuntu/Twitter-Analytics-Web-Service/TA/project
javac -cp "lib/*:." *.java
sudo java -cp "lib/*:." Test
}
main
