#!/bin/sh
set -e
if [ "$TRAVIS_PULL_REQUEST" = "false" ] && [ -n "$TRAVIS_TAG" ] && [ "$TRAVIS_JDK_VERSION" = "openjdk6" ]; then
    echo "mavenUser=$MAVEN_USER\nmavenPassword=$MAVEN_PASSWORD" >> gradle.properties
    gradle publish
fi
