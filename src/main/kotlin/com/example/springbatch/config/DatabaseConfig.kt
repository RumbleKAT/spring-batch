package com.example.springbatch.config

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import javax.sql.DataSource

@Configuration
open class DatabaseConfig {
    private val logger = LoggerFactory.getLogger(DatabaseConfig::class.java)
    @Bean
    open fun dataSource(): DataSource {
        logger.info("Initializing H2 in-memory database...")

        val dataSource = EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript("classpath:schema.sql")
            .addScript("classpath:data.sql")
            .build()

        logger.info("H2 in-memory database initialized successfully!")

        return dataSource
    }
}