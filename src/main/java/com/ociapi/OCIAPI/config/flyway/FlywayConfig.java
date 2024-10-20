package com.ociapi.OCIAPI.config.flyway;

//import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

//@Configuration
//@PropertySource(value = "file:.env")
public class FlywayConfig {

    @Value("${H2_DB_NAME}")
    private String dbName;

    @Value("${H2_USERNAME}")
    private String username;

    @Value("${H2_PASSWORD}")
    private String password;

    @Value("${H2_DB_SCHEMA}")
    private String schema;

//    @Bean(initMethod = "migrate")
//    public Flyway flyway() {
//        return Flyway.configure()
//                .dataSource("jdbc:h2:file:./data/" + dbName + ";DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE", username, password)
//                .schemas(schema)
//                .placeholders(Map.of(
//                        "H2_DB_SCHEMA", schema,
//                        "H2_DB_NAME", dbName,
//                        "H2_USERNAME", username,
//                        "H2_PASSWORD", password))
//                .locations("classpath:db/migration")
//                .load();
//    }
}
