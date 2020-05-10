#!/bin/bash
file=./node_modules/@vaadin/flow-frontend/gridConnector.js
if [ -e "$file" ]; then
  java -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -Xmx256m -Xss512k -XX:MetaspaceSize=100m -jar $file
else
  gradle bootRun
fi
