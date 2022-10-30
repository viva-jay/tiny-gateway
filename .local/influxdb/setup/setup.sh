#!/bin/bash
set -e
for var in jvm_micrometer k6
do
    influx bucket create --name $var \
        --org $INFLUX_ORG \
        -r 1h
done

influx apply \
    --org $INFLUX_ORG \
    --file /tmp/templates/micrometer.yml \
    --file /tmp/templates/docker.yml \
    --force yes \
    --quiet