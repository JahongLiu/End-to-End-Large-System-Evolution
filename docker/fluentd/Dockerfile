# Set the base image to ubuntu
FROM fluentd:v1.14-1

# File Author / Maintainer
MAINTAINER Jahong Liu

USER root

# Workaround to fix elasticsearch bug in elasticsearch version 8
RUN ["gem", "install", "elasticsearch", "--no-document", "--version", "< 8"]

RUN ["gem", "install", "fluent-plugin-elasticsearch", "--no-document", "--version", "5.0.3"]
RUN ["gem", "install", "fluent-plugin-detect-exceptions"]
RUN ["gem", "install", "fluent-plugin-concat"]
RUN ["gem", "install", "fluent-plugin-rewrite-tag-filter"]
