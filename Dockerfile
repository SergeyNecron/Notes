FROM openjdk:8-jdk
LABEL maintainer="Sergey Muratkin <sergeymuratkin@yandex.ru>"
ENV PROJECT_DIR=/home/notes/
ENV JAVA_HOME=/usr/local/openjdk-8
ENV GRADLE_VERSION=6.3
ENV GRADLE_HOME=/opt/gradle
ENV PATH=${PATH}:${GRADLE_HOME}/gradle-${GRADLE_VERSION}/bin
# install gradle
RUN wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -P /opt
RUN unzip -d ${GRADLE_HOME} ${GRADLE_HOME}-${GRADLE_VERSION}-bin.zip
RUN rm /opt/*.zip

# Prepare by downloading dependencies
COPY build.gradle.kts ${PROJECT_DIR}
COPY settings.gradle.kts ${PROJECT_DIR}
COPY gradlew ${PROJECT_DIR}
# Adding source, compile and package into a fat jar
COPY src ${PROJECT_DIR}/src
WORKDIR ${PROJECT_DIR}
RUN gradle

RUN gradle build --no-daemon

CMD java -jar ${PROJECT_DIR}/build/libs/$(ls ${PROJECT_DIR}/build/libs)

EXPOSE 8080