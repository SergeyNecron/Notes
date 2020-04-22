FROM gradle:latest
ENV PROJECT_DIR=/home/notes/
COPY . ${PROJECT_DIR}
WORKDIR ${PROJECT_DIR}
RUN chown gradle:gradle -R ${PROJECT_DIR}
USER gradle
CMD gradle bootRun

EXPOSE 8080