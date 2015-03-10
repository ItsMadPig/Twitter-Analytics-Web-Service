cd src/
javac -cp '*' *.java
jar -cvf MapReducer.jar *.class *.txt
cat data.txt | java -cp '../lib/*:.' ETLMapper | sort | java -cp '../lib/*:.' ETLReducer > output
