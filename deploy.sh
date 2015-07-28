#!/bin/sh
set -e
echo "mavenUser=$MAVEN_USER\nmavenPassword=$MAVEN_PASSWORD" >> gradle.properties
gradle uploadArchives