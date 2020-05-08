FROM gradle:6.3-jdk8
ENV PROJECT_DIR=/home/notes/
RUN cd /home && git clone https://github.com/SergeyNecron/notes.git && cd notes/
WORKDIR ${PROJECT_DIR}
RUN apt-get update && apt-get install npm -y
RUN chown gradle:gradle -R ${PROJECT_DIR}
USER gradle
RUN gradle bootJar --no-daemon
RUN chmod +x entrypoint.sh
CMD sh entrypoint.sh -port $PORT