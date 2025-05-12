# Set the base image to ubuntu
FROM ubuntu:22.04

# File Author / Maintainer
MAINTAINER Jahong Liu

# Install utility tools
RUN apt-get update && \
    apt-get install -y net-tools && \
    apt-get install -y iputils-ping && \
    apt-get install -y dnsutils && \
    apt-get install -y curl

# Install open jdk 11
RUN apt-get install -y software-properties-common \
&& add-apt-repository ppa:openjdk-r/ppa \
&& apt-get update \
&& apt install -y openjdk-11-jdk

RUN apt-get clean

COPY ./image/discovery.jar /usr/war/

COPY ./image/docker-entrypoint.sh /usr/bin/docker-entrypoint.sh

# Create mount point for log files
VOLUME ["/var/log/oms"]

EXPOSE 8761

ENTRYPOINT ["/usr/bin/docker-entrypoint.sh"]
