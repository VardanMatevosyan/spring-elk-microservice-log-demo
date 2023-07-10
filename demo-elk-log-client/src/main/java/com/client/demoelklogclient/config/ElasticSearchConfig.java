package com.client.demoelklogclient.config;


import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.client.demoelklogclient.repository.elastic")
@ComponentScan(basePackages = "com.client.demoelklogclient")
public class ElasticSearchConfig {

  @Value("${elasticsearch.url}")
  public String elasticsearchUrl;

  @Bean
  ClientConfiguration clientConfiguration() {
    return ClientConfiguration.builder()
        .connectedTo(elasticsearchUrl)
        .withConnectTimeout(Duration.ofSeconds(5))
        .withSocketTimeout(Duration.ofSeconds(3))
        .build();
  }

}

