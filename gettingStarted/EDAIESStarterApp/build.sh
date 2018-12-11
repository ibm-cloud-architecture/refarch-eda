#!/bin/bash
# Exit when failures occur (including unset variables)
set -o errexit
set -o nounset
set -o pipefail

command -v mvn > /dev/null 2>&1 || { echo "Apache Maven pre-req is missing, see README.md for details."; exit 1; }
(mvn -v | grep "Java version: 1.8") > /dev/null 2>&1 || { echo "Apache Maven is not using Java version 8, see README.md for details."; exit 1; }
command -v docker > /dev/null 2>&1 || { echo "Docker pre-req is missing, see README.md for details."; exit 1; }
mvn install
docker build -t edaiesstarterapp:v1.0.0 .