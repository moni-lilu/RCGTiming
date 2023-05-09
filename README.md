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
docker service  create ^
--name jenkins-chrome ^
--network bridge ^
--env DOCKER_HOST=tcp://docker:2376 ^
--env DOCKER_CERT_PATH=/certs/client ^
--env DOCKER_TLS_VERIFY=1 ^
--publish 8080:8080 ^
--publish 50000:50000 ^
--mount type=volume,source=jenkins-data,destination=/var/jenkins_home ^
--mount type=volume,source=jenkings-docker-certs,destination=/certs/client:ro ^
--secret rcgt_config ^
myjenkins-blueocean:2.387.2-1
```
* Go to jenkins-docker terminal
* Execute a command
```bash
cat /var/jenkins_home/secrets/initialAdminPassword
```
* Get the password like 
```bash
06fa6035e6ca477aadd197a2d8912889
```
* Go to link http://localhost:8080/ This screen will open
![screenshot-1](https://github.com/moni-lilu/RCGTiming/blob/main/screenshots/screenshot-1.png)
* Enter the password obtained in the previous step in the field "Administrator password"
* Select "Install suggested plugins"
![screenshot-2](https://github.com/moni-lilu/RCGTiming/blob/main/screenshots/screenshot-2.png)
* Fill in the form and press "Save and Continue"
![screenshot-3](https://github.com/moni-lilu/RCGTiming/blob/main/screenshots/screenshot-3.png)
* Press "Save and Finish" in the next screen
![screenshot-4](https://github.com/moni-lilu/RCGTiming/blob/main/screenshots/screenshot-4.png)
* Press "Start using Jenkins"
![screenshot-5](https://github.com/moni-lilu/RCGTiming/blob/main/screenshots/screenshot-5.png)
* Press "Configure Jenkins" and select "Manage Plugins"
![screenshot-16](https://github.com/moni-lilu/RCGTiming/blob/main/screenshots/screenshot-16.png)
* Find and install the Allure plugin
![screenshot-17](https://github.com/moni-lilu/RCGTiming/blob/main/screenshots/screenshot-17.png)
![screenshot-18](https://github.com/moni-lilu/RCGTiming/blob/main/screenshots/screenshot-18.png)
![screenshot-19](https://github.com/moni-lilu/RCGTiming/blob/main/screenshots/screenshot-19.png)
![screenshot-20](https://github.com/moni-lilu/RCGTiming/blob/main/screenshots/screenshot-20.png)
* Choose «Конфигурация глобальных инструментов»
![screenshot-6](https://github.com/moni-lilu/RCGTiming/blob/main/screenshots/screenshot-6.png)
* Find the "Add Maven" option. Click on it and fill in the "Name" field. Press Save.
![screenshot-7](https://github.com/moni-lilu/RCGTiming/blob/main/screenshots/screenshot-7.png)
* Press «Add Allure Commandline»
![screenshot-21](https://github.com/moni-lilu/RCGTiming/blob/main/screenshots/screenshot-21.png)
* Enter a name, select a version and save the configuration
![screenshot-22](https://github.com/moni-lilu/RCGTiming/blob/main/screenshots/screenshot-22.png)
* Press "Create Item"
![screenshot-8](https://github.com/moni-lilu/RCGTiming/blob/main/screenshots/screenshot-8.png)
* Enter a task name, select "Create a task with free configuration" and press OK
![screenshot-9](https://github.com/moni-lilu/RCGTiming/blob/main/screenshots/screenshot-9.png)
* Fill in the Repository URL field in the Source Code Management section
![screenshot-10](https://github.com/moni-lilu/RCGTiming/blob/main/screenshots/screenshot-10.png)
* In the Credentials block, click Add → Jenkins. Enter GitHub username and password and press Add
![screenshot-11](https://github.com/moni-lilu/RCGTiming/blob/main/screenshots/screenshot-11.png)
* Choose username and password from the dropdown list
![screenshot-12](https://github.com/moni-lilu/RCGTiming/blob/main/screenshots/screenshot-12.png)
* Press "Add Build Step", select "Invoke top-level Maven targets"
![screenshot-13](https://github.com/moni-lilu/RCGTiming/blob/main/screenshots/screenshot-13.png)
* Select a name in the "Maven version" field. Write "clean test" in the "Goals" field
![screenshot-14](https://github.com/moni-lilu/RCGTiming/blob/main/screenshots/screenshot-14.png)
* Click "Add Post Assembly Step" and select "Allure Report" in "Post Assembly" section
![screenshot-23](https://github.com/moni-lilu/RCGTiming/blob/main/screenshots/screenshot-23.png)
* Enter "target/allure-results" in the Path field
![screenshot-24](https://github.com/moni-lilu/RCGTiming/blob/main/screenshots/screenshot-24.png)
* Press "Save"
* Press "Collect now"
![screenshot-15](https://github.com/moni-lilu/RCGTiming/blob/main/screenshots/screenshot-15.png)
