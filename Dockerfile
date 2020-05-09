FROM openjdk:8-jdk-alpine
LABEL maintainer="Sergey Muratkin <sergeymuratkin@yandex.ru>"
ENV PROJECT_DIR=/home/notes/
RUN apk update && apk add git
RUN cd /home && git clone https://github.com/SergeyNecron/notes.git && cd notes/
ENV GRADLE_VERSION=6.3
ENV GRADLE_HOME=/opt/gradle
ENV PATH=${PATH}:${GRADLE_HOME}/gradle-${GRADLE_VERSION}/bin
# install gradle
RUN wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -P /opt
RUN mkdir ${GRADLE_HOME} && unzip -d ${GRADLE_HOME} ${GRADLE_HOME}-${GRADLE_VERSION}-bin.zip
RUN rm /opt/*.zip

# Prepare by downloading dependencies
COPY build.gradle.kts ${PROJECT_DIR}
COPY settings.gradle.kts ${PROJECT_DIR}
COPY gradlew ${PROJECT_DIR}

WORKDIR ${PROJECT_DIR}
RUN apk add npm
RUN adduser -D --home ${GRADLE_HOME} --shell /bin/bash gradle
RUN chown gradle:gradle -R ${PROJECT_DIR}
USER gradle
RUN chmod +x entrypoint.sh
CMD sh entrypoint.sh -port $PORT
#CMD top -b