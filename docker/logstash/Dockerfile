# Start with the official Logstash image
FROM docker.elastic.co/logstash/logstash:8.3.3

# Install the JDBC plugin
RUN logstash-plugin install logstash-integration-jdbc

RUN mkdir -p /usr/share/logstash/logstash-core/lib/jars/

RUN curl -Lo /usr/share/logstash/logstash-core/lib/jars/postgresql-jdbc.jar https://jdbc.postgresql.org/download/postgresql-42.5.1.jar
