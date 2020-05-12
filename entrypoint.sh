#!/bin/bash
java -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -Xmx126m -Xss512k -XX:MetaspaceSize=100m -jar ./build/libs/fine-note.jar
