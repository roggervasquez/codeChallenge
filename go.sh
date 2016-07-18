#!/bin/sh

#Delete *.class
rm *.class

#Compile the Challenge.java that has all the logic, but uses the minimal-json-0.9.4 to parse Json objects
javac -cp ./minimal-json-0.9.4.jar:. Challenge.java

#Run the application, pass the 2 files as parameters, last parameter  runtests
java -cp  ./minimal-json-0.9.4.jar:. Challenge products.txt listings_2.txt runtests
#java -cp  ./minimal-json-0.9.4.jar:. Challenge products.txt listings.txt
