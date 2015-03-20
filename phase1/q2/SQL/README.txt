要先修改database server的DNS address:

vim ~/cloud-computing-project/phase1/q2/jialic/frontEnd/SQL4/TwitterDAO.java


跑程序的命令：
cd cloud-computing-project/phase1/q2/jialic/frontEnd/


javac -cp 'lib/*:.' SQL4/*.java

sudo java -cp 'lib/*:.' SQL4.Test
