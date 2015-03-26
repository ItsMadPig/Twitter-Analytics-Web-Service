#Version(Mar. 17)

#install javac
sudo yum install java-devel
#then this can be compiled
javac -cp '../lib/*:.' *.java
And run:
sudo java -cp '../lib/*:.' Test
