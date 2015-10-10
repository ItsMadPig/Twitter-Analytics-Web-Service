#!/bin/bash
main(){
sudo service mysql stop
sudo mkdir /data
sudo mount /dev/xvdf /data
echo -e "/dev/xvdf\t/data\text4\tdefaults,nofail\t0\t2" | sudo tee -a /etc/fstab
sudo mount -a
echo -e "/data/etc/mysql\t/etc/mysql\tnone\tbind" | sudo tee -a /etc/fstab
echo -e "/data/lib/mysql\t/var/lib/mysql\tnone\tbind" | sudo tee -a /etc/fstab
echo -e "/data/log/mysql\t/var/log/mysql\tnone\tbind" | sudo tee -a /etc/fstab
sudo mount /var/log/mysql
sudo mount /etc/mysql
sudo mount /var/lib/mysql
sudo chmod 777 -R /data
sudo service mysql start
}
main
