## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)

## General info
These automation tests designed to test the main functionality of the project RCGTiming https://rcgtiming.com/
	
## Technologies
Project is created with:

## Setup

* Install Docker desktop https://docs.docker.com/get-docker/
* Open up a terminal window
* Create a bridge network in Docker
```bash
docker network create jenkins
```
* Run a docker:dind Docker image
```bash
docker run --name jenkins-docker --rm --detach ^
  --privileged --network jenkins --network-alias docker ^
  --env DOCKER_TLS_CERTDIR=/certs ^
  --volume jenkins-docker-certs:/certs/client ^
  --volume jenkins-data:/var/jenkins_home ^
  --publish 2376:2376 ^
  docker:dind
```
* Create Dockerfile with the following content:
```bash
FROM jenkins/jenkins:2.387.2
USER root
RUN apt-get update && apt-get install -y lsb-release
RUN curl -fsSLo /usr/share/keyrings/docker-archive-keyring.asc \
  https://download.docker.com/linux/debian/gpg

# Chrome instalation 
RUN curl -LO  https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
RUN apt-get install -y ./google-chrome-stable_current_amd64.deb
RUN rm google-chrome-stable_current_amd64.deb
# Check chrome version
RUN echo "Chrome: " && google-chrome --version

RUN echo "deb [arch=$(dpkg --print-architecture) \
  signed-by=/usr/share/keyrings/docker-archive-keyring.asc] \
  https://download.docker.com/linux/debian \
  $(lsb_release -cs) stable" > /etc/apt/sources.list.d/docker.list
RUN apt-get update && apt-get install -y docker-ce-cli
USER jenkins
RUN jenkins-plugin-cli --plugins "blueocean docker-workflow"
```
* Build a new docker image from this Dockerfile and assign the image a meaningful name, e.g. "myjenkins-blueocean:2.387.3-1":
```bash
docker build -t myjenkins-blueocean:2.387.3-1 .
```
* Initialize a swarm
```bash
docker swarm init
```
* Create docker_config.ini with the following content:
```bash
; for Docker
[accesses]
StageLogin=login
StagePassword=password
AdminEmail=admin@email.com
AdminPassword=password
TestEmail=test@email.com
TestPassword=password

[configuration]
DownloadsFolderPath=path
IPForConnaction=ip
AuthorisationToken=token
Headless=true
```
* Create a secret from the file docker_config.ini
```bash
docker secret create rcgt_config docker_config.ini
```
* Create a service 
```bash
docker service  create --name jenkins-chrome --network bridge --env DOCKER_HOST=tcp://docker:2376 --env DOCKER_CERT_PATH=/certs/client --env DOCKER_TLS_VERIFY=1 --publish 8080:8080 --publish 50000:50000 --mount type=volume,source=jenkins-data,destination=/var/jenkins_home --mount type=volume,source=jenkings-docker-certs,destination=/certs/client:ro --secret rcgt_config myjenkins-blueocean:2.387.2-1
```
* Go to link http://localhost:8080/
* This screen will open
```bash

```
```bash

```
```bash

```
```bash

```


* docker_config.ini
```bash
; for Docker
[accesses]
StageLogin=login
StagePassword=password
AdminEmail=admin@email.com
AdminPassword=password
TestEmail=test@email.com
TestPassword=password

[configuration]
DownloadsFolderPath=path
IPForConnaction=ip
AuthorisationToken=token
Headless=true
```

* To run tests, you need to run the emulator
