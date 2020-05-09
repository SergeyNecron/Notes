#!/bin/bash
FILE=./build/libs/fine-note.jar
if [ -f $FILE ];
then
  java -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -Xmx256m -Xss512k -XX:MetaspaceSize=100m -jar $FILE
  echo "File '$FILE' exist"
else
  echo "File '$FILE' not exist. File will build"
  gradle bootRun