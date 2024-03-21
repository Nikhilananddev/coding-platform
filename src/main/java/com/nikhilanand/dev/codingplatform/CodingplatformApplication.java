package com.nikhilanand.dev.codingplatform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.IndexInfo;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class CodingPlatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(CodingPlatformApplication.class, args);
    }

}
