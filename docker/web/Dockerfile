# Set the base image to tomcat
FROM tomcat:9.0.58-jdk17

# File Author / Maintainer
MAINTAINER Jahong Liu

# Install utility tools
RUN apt-get update && \
    apt-get install -y net-tools && \
    apt-get install -y iputils-ping && \
    apt-get install -y dnsutils && \
    apt-get install -y curl
RUN apt-get clean

# Install pythong, django and gunicorn
RUN apt-get update && \
    apt-get install -y python3-pip && \
    pip3 install "django>=3.2,<4" && \
    pip3 install gunicorn

RUN pip3 install requests && \
    pip3 install python-json-logger && \
    pip3 install opentracing jaeger-client

RUN pip3 install django-prometheus

# Copy Python UI code
RUN mkdir /usr/src/PyUI
WORKDIR /usr/src/PyUI
COPY ./image/PyUI.tar.gz /usr/src/PyUI
RUN tar -zxf PyUI.tar.gz && \
    rm -f PyUI.tar.gz

# Create mount point for log files
VOLUME ["/var/log/oms"]

# Change current directory
WORKDIR /usr/src/PyUI

# Expose the default port
EXPOSE 8000

CMD ["/usr/local/bin/gunicorn", "pyui.wsgi:application", "--bind", "0.0.0.0:8000", "--workers", "3"]
