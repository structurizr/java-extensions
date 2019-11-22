# Building

[![Build Status](https://travis-ci.org/structurizr/java-extensions.svg?branch=master)](https://travis-ci.org/structurizr/java-extensions)

To build "Structurizr for Java extensions" from the sources (you'll need Java 8)...

```
git clone https://github.com/structurizr/java-extensions.git
cd java-extensions
./gradlew compileJava test
```

If necessary, after building, you can install "Structurizr for Java extensions" into your local Maven repo using:

```
./gradlew publishToMavenLocal
```