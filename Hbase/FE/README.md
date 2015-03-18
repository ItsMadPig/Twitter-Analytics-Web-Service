Version(Mar. 17)

#没有javac的时候要download:
sudo yum install java-devel
然后就可以compile:
javac -cp '../lib/*:.' *.java
And run:
sudo java -cp '../lib/*:.' Test