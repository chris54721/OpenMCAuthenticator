#!/bin/sh
set -e
if [ "$TRAVIS_PULL_REQUEST" = "false" ] && [ -n "$TRAVIS_TAG" ] && [ "$TRAVIS_JDK_VERSION" = "openjdk6" ]; then
    mvn org.apache.maven.plugins:maven-dependency-plugin:2.8:get -Dartifact=org.apache.maven.wagon:wagon-ssh:2.9
    echo "mavenUser=$MAVEN_USER\nmavenPassword=$MAVEN_PASSWORD" >> gradle.properties
    gradle publish
fi
