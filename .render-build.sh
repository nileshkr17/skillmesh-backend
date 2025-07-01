#!/usr/bin/env bash
# Custom build script for Render

set -o errexit
set -o xtrace

if [ -f mvnw ]; then
  mvnw clean package -DskipTests
else
  mvn clean package -DskipTests
fi
