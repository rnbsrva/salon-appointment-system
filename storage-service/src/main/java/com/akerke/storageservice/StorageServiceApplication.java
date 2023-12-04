package com.akerke.storageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Arrays;
import java.util.Comparator;

@SpringBootApplication
@EnableFeignClients
public class StorageServiceApplication {

    public static void main(String[] args) {
        System.out.println("hello world +++++");
        SpringApplication.run(StorageServiceApplication.class, args);
    }

}
