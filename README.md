# DB

Simple database engine written in Java.

Built against Java 8u40 and Apache Ant 1.9.4.

## Compile

From root directory:

`ant compile`

or

`javac -d bin src/db/*.java`

## Run Tests

From root directory:

`ant test`

or run this for each class name in the package:

`java -classpath bin -ea db.<ClassName>`

## To Produce Docs

From source directory:

`ant doc`

or

`javadoc -d doc -sourcepath src -package db`

