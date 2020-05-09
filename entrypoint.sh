#!/bin/bash
file=./build/libs/fine-note.jar
if [ -e "$file" ]
then
        java -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -Xmx256m -Xss512k -XX:MetaspaceSize=100m -jar $file
else
        gradle bootRun
fi