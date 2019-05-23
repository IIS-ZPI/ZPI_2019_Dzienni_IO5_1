v#!/bin/bash
git checkout release
version = `git describe --tags`
versionName = "LATEST-SNAPSHOT"
mvn versions:set -DnewVersion=$versionName
mvn package
