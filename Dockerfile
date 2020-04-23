FROM gradle:6.3-jdk11
ENV PROJECT_DIR=/home/notes/
COPY . ${PROJECT_DIR}
WORKDIR ${PROJECT_DIR}
RUN chown gradle:gradle -R ${PROJECT_DIR}
USER gradle
RUN gradle bootJar --no-daemon
CMD java -Xms50m -Xmx200m -jar ${PROJECT_DIR}/build/libs/*.jar
EXPOSE 8080