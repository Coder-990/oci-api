package com.ociapi.OCIAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OciapiApplication {

    public static void main(String[] args) {
//        var envFileCreatorService = new EnvFileCreatorServiceImpl();
//        envFileCreatorService.createEnvFileIfNotExists();
        SpringApplication.run(OciapiApplication.class, args);
    }

}
