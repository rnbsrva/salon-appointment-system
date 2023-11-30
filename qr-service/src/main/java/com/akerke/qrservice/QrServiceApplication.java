package com.akerke.qrservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.http.EurekaClientHttpRequestFactorySupplier;
import org.springframework.cloud.netflix.eureka.http.RestTemplateDiscoveryClientOptionalArgs;
import org.springframework.cloud.netflix.eureka.http.RestTemplateTransportClientFactories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class QrServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(QrServiceApplication.class, args);
    }
}
