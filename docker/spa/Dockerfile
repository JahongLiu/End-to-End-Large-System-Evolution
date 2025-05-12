# Set the base image to node
FROM node:16

# File Author / Maintainer
MAINTAINER Jahong Liu

# Install utility tools
RUN apt-get update
RUN apt-get install -y net-tools && \
    apt-get install -y iputils-ping && \
    apt-get install -y curl
RUN apt-get clean


# Create mount point for log files
VOLUME ["/var/log/oms"]

# Copy nging conf
COPY ./image/nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

# Copy Python UI code
RUN mkdir /usr/src/reactapp
COPY ./image/reactapp.tar.gz /usr/src/reactapp

WORKDIR /usr/src/reactapp
RUN tar -zxf reactapp.tar.gz && \
    rm -f reactapp.tar.gz    
