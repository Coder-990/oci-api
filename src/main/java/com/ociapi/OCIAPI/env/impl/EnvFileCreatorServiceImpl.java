package com.ociapi.OCIAPI.env.impl;

import com.ociapi.OCIAPI.env.EnvFileCreatorService;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Slf4j
public class EnvFileCreatorServiceImpl implements EnvFileCreatorService {

    @Override
    public void createEnvFileIfNotExists() {
        File envFile = new File(".env");

        if (!envFile.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(envFile))) {
                writer.write("H2_USERNAME=ifusionadf\n");
                writer.write("H2_PASSWORD=ifusionadf\n");
                writer.write("H2_DB_NAME=ociapi\n");
                writer.write("H2_DB_SCHEMA=ifusionadf\n");
                log.info(".env file created successfully.");
            } catch (IOException e) {
                log.info("Failed to create .env file: {}", e.getMessage());
            }
        } else {
            log.info(".env file already exists. Skipping creation.");
        }
    }
}
