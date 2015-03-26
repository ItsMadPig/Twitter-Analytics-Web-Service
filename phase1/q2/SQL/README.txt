要先修改database server的DNS address:

vim ~/cloud-computing-project/phase1/q2/SQL/TwitterDAO.java


跑程序的命令：
cd cloud-computing-project/phase1/q2/


javac -cp 'lib/*:.' SQL/*.java

sudo java -cp 'lib/*:.' SQL.Test
