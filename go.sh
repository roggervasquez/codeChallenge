#!/bin/sh

#Delete *.class
rm *.class

#Compile the Challenge.java that has all the logic, but uses the minimal-json-0.9.4 to parse Json objects
javac -cp ./minimal-json-0.9.4.jar:. Challenge.java

#Run the application, pass the 2 files as parameters, in this case pass a test smaller listings_2 file and the switch  runtests
 java -cp  ./minimal-json-0.9.4.jar:. Challenge products.txt listings_2.txt runtests

#Run the application with the real listings file without running tests
#java -cp  ./minimal-json-0.9.4.jar:. Challenge products.txt listings.txt
