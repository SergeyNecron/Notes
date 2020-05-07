FROM openjdk:8-jdk-alpine
ENV PROJECT_DIR=/home/notes/
WORKDIR ${PROJECT_DIR}
COPY . ${PROJECT_DIR}
RUN apk add npm && npm install
RUN chmod +x entrypoint.sh
CMD sh entrypoint.sh
