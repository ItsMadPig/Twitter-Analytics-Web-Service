javac -cp 'gson-2.3.1.jar:commons-lang3-3.3.2.jar:MapReducer.jar' *.java
jar -cvf MapReducer.jar *.class *.txt
cat input_uncompressed | java -cp 'gson-2.3.1.jar:commons-lang3-3.3.2.jar:MapReducer.jar:.' ETLMapper | sort | java -cp 'gson-2.3.1.jar:commons-lang3-3.3.2.jar:MapReducer.jar:.' ETLReducer > output
