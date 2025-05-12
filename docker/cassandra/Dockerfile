# Set the base image to cassandra
FROM cassandra:4.0.1

# File Author / Maintainer
MAINTAINER Jahong Liu

# Install utility tools
RUN apt-get update && \
    apt-get install -y net-tools && \
    apt-get install -y iputils-ping && \
    apt-get install -y dnsutils && \
    apt-get install -y curl

COPY ./image/docker-entrypoint-second.sh /docker-entrypoint-second.sh
COPY ./image/insert-data.cql /insert-data.cql
COPY ./image/create-schema.cql /create-schema.cql
COPY ./image/check-readiness.sh /check-readiness.sh
COPY ./image/jmx_prometheus_javaagent.jar /opt/cassandra/lib
COPY ./image/jmx-prometheus.yml /etc/cassandra

RUN echo 'JVM_OPTS="$JVM_OPTS -javaagent:/opt/cassandra/lib/jmx_prometheus_javaagent.jar=7070:/etc/cassandra/jmx-prometheus.yml"' >> /etc/cassandra/cassandra-env.sh

ENTRYPOINT ["/docker-entrypoint-second.sh"]
CMD ["cassandra", "-f"]
