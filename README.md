# DB

Simple database engine written in Java, designed to act as a package providing database operations for other Java applications. This is a personal project and has not been designed for use in production. For more information read the [Overview](http://ap0c.github.io/db), or the documentation found in the `docs` directory (start with `index.html`).

Built against Java 8u40 and Apache Ant 1.9.4.

## Compile

From root directory:

`ant compile`

or

`javac -d bin src/db/*.java`

## Demo

To run the demo program:

`ant demo`

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

