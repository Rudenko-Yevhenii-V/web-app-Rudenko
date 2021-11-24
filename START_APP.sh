#!/bin/sh
mvn clean install
cd english-lessons-web-app/
docker-compose -f docker-compose.yml up -d
