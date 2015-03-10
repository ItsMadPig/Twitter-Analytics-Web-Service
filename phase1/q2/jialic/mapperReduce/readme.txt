cd src/
javac -cp '*' *.java
jar -cvf MapReducer.jar *.class *.txt
cat /Users/Scarlett/Desktop/eBusiness/15619/619Project/Phrase1/data | java -classpath 'gson-2.3.1.jar:commons-lang3-3.3.2.jar:MapReducer.jar:.' ETLMapper | sort | java -classpath 'gson-2.3.1.jar:commons-lang3-3.3.2.jar:MapReducer.jar:.' ETLReducer >../db
