# Set the base image to postgres
FROM postgres:15.2

# File Author / Maintainer
MAINTAINER Jahong Liu

# Install utility tools
RUN apt-get update
RUN apt-get install -y net-tools && \
    apt-get install -y iputils-ping && \
    apt-get install -y dnsutils && \
    apt-get install -y curl

COPY ./image/create-schema.sql /docker-entrypoint-initdb.d/
COPY ./image/check-readiness.sh /check-readiness.sh
