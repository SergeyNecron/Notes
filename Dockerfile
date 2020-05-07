FROM openjdk:8-jdk-alpine
ENV PROJECT_DIR=/home/notes/
WORKDIR ${PROJECT_DIR}
COPY . ${PROJECT_DIR}
RUN apk add npm && npm install

CMD java -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -Xmx256m -Xss512k -XX:MetaspaceSize=100m -jar ./build/libs/fine-note.jar
