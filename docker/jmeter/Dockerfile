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

ENV JAVA_HOME /usr/lib/jvm/java-11-openjdk-amd64

WORKDIR /usr

######## Install JMeter ##########
RUN cd /usr/share && \
    curl -LO https://dlcdn.apache.org//jmeter/binaries/apache-jmeter-5.5.tgz && \
    tar -zxf apache-jmeter-*.tgz && \
    rm -f apache-jmeter-*.tgz && \
    mv apache-jmeter-* jmeter && \
    ln -s /usr/share/jmeter/bin/jmeter /usr/bin/jmeter

RUN mkdir -p /usr/data/jmeter

COPY ./image/tests/test-plan.jmx \
     ./image/tests/create-data.sh \
     ./image/tests/users.csv \
     ./image/tests/products.csv \
     /usr/data/jmeter/
COPY ./image/log4j2.xml /usr/share/jmeter/bin/

COPY ./image/docker-entrypoint.sh /usr/bin/docker-entrypoint.sh

# Create mount point for log files
VOLUME ["/var/log/oms"]

ENTRYPOINT ["/usr/bin/docker-entrypoint.sh"]
