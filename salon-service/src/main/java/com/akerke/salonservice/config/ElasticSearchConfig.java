package com.akerke.salonservice.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {

    @Value("${spring.elasticsearch.schema}")
    private String schema;

    @Value("${spring.elasticsearch.port}")
    private Integer port;

    @Value("${spring.elasticsearch.host}")
    private String host;

    @Bean
    public RestHighLevelClient client() {
        var httpPost = new HttpHost(host, port, schema);
        var restClient = RestClient.builder(httpPost);
        return new RestHighLevelClient(restClient);
    }

}
