#!/bin/bash
main(){
sudo service mysql stop
sudo umount /mnt
sudo mkdir /mnt/etc /mnt/lib /mnt/log
sudo mv /etc/mysql     /mnt/etc/
sudo mv /var/lib/mysql /mnt/lib/
sudo mv /var/log/mysql /mnt/log/
sudo mkdir /etc/mysql
sudo mkdir /var/lib/mysql
sudo mkdir /var/log/mysql
echo -e "/mnt/etc/mysql\t/etc/mysql\tnone\tbind" | sudo tee -a /etc/fstab
sudo mount /etc/mysql
echo -e "/mnt/lib/mysql\t/var/lib/mysql\tnone\tbind" | sudo tee -a /etc/fstab
sudo mount /var/lib/mysql
echo -e "/mnt/log/mysql\t/var/log/mysql\tnone\tbind" | sudo tee -a /etc/fstab
sudo mount /var/log/mysql
sudo service mysql start
}
main
