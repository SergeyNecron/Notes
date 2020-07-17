FROM openjdk:8-jdk-alpine
LABEL maintainer="Sergey Muratkin <sergeymuratkin@yandex.ru>"

RUN apk update && apk add git && apk add npm

# install gradle
ENV GRADLE_VERSION=6.3
ENV GRADLE_HOME=/opt/gradle
ENV PATH=${PATH}:${GRADLE_HOME}/gradle-${GRADLE_VERSION}/bin
RUN wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -P /opt && \
mkdir ${GRADLE_HOME} && \
unzip -d ${GRADLE_HOME} ${GRADLE_HOME}-${GRADLE_VERSION}-bin.zip && \
rm /opt/*.zip

# create dir
ENV PROJECT_DIR=/home/notes
RUN mkdir ${PROJECT_DIR}
WORKDIR ${PROJECT_DIR}

# add user
RUN adduser -D --home ${PROJECT_DIR} --shell /bin/bash user

# copy project
COPY ./ ${PROJECT_DIR}
RUN chown user:user -R ${PROJECT_DIR}

USER user

# build project
RUN gradle build
RUN npm install

#run
RUN chmod +x entrypoint.sh
RUN chmod +x ./build/libs/fine-note.jar

CMD sh entrypoint.sh -port $PORT
#CMD top -b

ENV DATASOURCE_POSTGRES_URL=jdbc:postgresql://pg:5432
ENV DATASOURCE_POSTGRES_USERNAME=postgres
ENV DATASOURCE_POSTGRES_PASSWORD=postgres

# fix error frontend
ENV VAADIN_LIB=/node_modules/@vaadin/flow-frontend/
RUN mkdir .${VAADIN_LIB} && \
cp ./frontend/lib/* .${VAADIN_LIB}
