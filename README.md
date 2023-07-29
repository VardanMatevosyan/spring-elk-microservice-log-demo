# spring-elk-microservice-log-demo
Demo spring with ELK microservices logging with using unique trace id 
Project contains two services (gradle modules):
1. ### Service [demo-elk-log](demo-elk-log) that calls the demo-elk-log-client service and logging dummy information about it. 

2. ### Service  [demo-elk-log-client](demo-elk-log-client) - that do some dummy logging and has users search endpoints that uses dynamic elastic search native query builder.
Service allows to use:
 - full test search for the root fields and for the nested object fields, 
 - filtering by range data, 
 - sorting by root fields as well as for the nested object fields, 
 - pageable feature using page and size fields.

3. ### Docker compose file with ELK stack services - [docker-compose.yml](elk%2Fdocker-compose.yml).
It starts Elasticsearch, Logstash, Kibana services in the local environment and uses configuration:
- Main configuration -  [logstash.yml](elk%2Flogstash%2Fconfig%2Flogstash.yml)
Describes where is pipeline configuration located inside the container, http logstash host, elasticsearch host monitoring and other.
- Pipeline configuration - [logstash.conf](elk%2Flogstash%2Fpipeline%2Flogstash.conf)
Describes how to collect and where to send data to the elasticsearch to store the received logs from the services.

When open localhost:5601 in the browser it opens kibana console
where kibana allows to create a index pattern to see all the logs from the all service that configured to send the logs by specific index name,
and observe, filter, sort the logs using kibana ui console.

When open localhost:9200 in the browser elasticsearch sends back metadata information by the specified path. 