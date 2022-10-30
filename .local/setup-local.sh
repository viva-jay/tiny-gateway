#!/bin/bash
echo "--------------------------------------------------------------------------------------"
echo "Set up the environment for load testing."
echo "--------------------------------------------------------------------------------------"
# chmod +x setup/setup.sh
# docker-compose up -d influxdb telegraf
docker-compose up -d influxdb

echo "--------------------------------------------------------------------------------------"
echo "Start Mock servers http://localhost:8081/ \
                         http://localhost:8082/"
echo "--------------------------------------------------------------------------------------"
docker-compose up -d mock-server-a mock-server-b