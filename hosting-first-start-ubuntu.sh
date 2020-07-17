#!/bin/bash
sudo apt update && \
sudo apt upgrade -y && \
sudo apt install htop && \
sudo apt install -y docker && \
sudo apt install -y docker-compose && \
sudo apt install -y git && \
cd ~ && \
git clone https://github.com/SergeyNecron/notes.git && \
cd notes && \
sudo docker-compose up
