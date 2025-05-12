# Set the base image to nginx
FROM nginx:1.23

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

COPY ./image/docker-entrypoint.sh /usr/bin/docker-entrypoint.sh

EXPOSE 80

# Set default container command
ENTRYPOINT ["/usr/bin/docker-entrypoint.sh"]
