FROM openjdk:8-jdk-alpine
LABEL maintainer="Sergey Muratkin <sergeymuratkin@yandex.ru>"
ENV GRADLE_VERSION=6.3
ENV PROJECT_DIR=/home/notes
ENV GRADLE_HOME=/opt/gradle
ENV PATH=${PATH}:${GRADLE_HOME}/gradle-${GRADLE_VERSION}/bin
ENV DATASOURCE_MYSQL_URL=jdbc:mysql://ms:3306
ENV DATASOURCE_MYSQL_USERNAME=root
ENV DATASOURCE_MYSQL_PASSWORD=password
ENV VAADIN_LIB=/node_modules/@vaadin/flow-frontend/
WORKDIR ${PROJECT_DIR}
RUN apk update && apk add git && apk add npm
# install gradle
RUN wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -P /opt && \
mkdir ${GRADLE_HOME} && \
unzip -d ${GRADLE_HOME} ${GRADLE_HOME}-${GRADLE_VERSION}-bin.zip && \
rm /opt/*.zip
# add user gradle
RUN adduser -D --home ${GRADLE_HOME} --shell /bin/bash gradle && \
chown gradle:gradle -R ${PROJECT_DIR}
USER gradle
# copy project
RUN cd /home && \
git clone https://github.com/SergeyNecron/notes.git && \
cd ${PROJECT_DIR} && \
git checkout mysql
#COPY ./ ${PROJECT_DIR}/notes
# build project
RUN gradle build && \
npm install && \
# fix error frontend
mkdir .${VAADIN_LIB}  && \
mv ./frontend/* .${VAADIN_LIB}
# run
RUN chmod +x entrypoint.sh
CMD sh entrypoint.sh -port $PORT
#CMD top -b