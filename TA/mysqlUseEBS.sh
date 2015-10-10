#!/bin/bash
main(){
sudo service mysql stop
echo -e "/dev/xvdf\t/mnt\text4\tdefaults,nofail 0\t2" | sudo tee -a /etc/fstab
echo -e "/mnt/etc/mysql\t/etc/mysql\tnone\tbind" | sudo tee -a /etc/fstab
echo -e "/mnt/lib/mysql\t/var/lib/mysql\tnone\tbind" | sudo tee -a /etc/fstab
echo -e "/mnt/log/mysql\t/var/log/mysql\tnone\tbind" | sudo tee -a /etc/fstab
sudo mount -a
sudo service mysql start
}
main
