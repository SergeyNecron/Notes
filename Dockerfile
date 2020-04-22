FROM gradle:latest
ENV PROJECT_DIR=/home/notes/
COPY build.gradle.kts ${PROJECT_DIR}
COPY settings.gradle.kts ${PROJECT_DIR}
COPY gradlew ${PROJECT_DIR}
COPY src ${PROJECT_DIR}/src
WORKDIR ${PROJECT_DIR}
RUN chown gradle:gradle -R /home/notes
USER gradle
RUN gradle build --no-daemon
CMD gradle bootRun

EXPOSE 8080