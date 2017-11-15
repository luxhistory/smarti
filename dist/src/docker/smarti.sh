#!/usr/bin/env bash

if [ $# -eq 0 -o "${1:0:1}" = '-' ]; then
    JVM_ARGS="-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -Djava.security.egd=file:/dev/./urandom -Dloader.path=BOOT-INF/lib,/opt/ext"

    exec /usr/bin/java ${JVM_ARGS} -jar /opt/smarti.jar "$@"
else
    exec "$@"
fi
