package com.rds.iam.rds.configuration

import com.rds.iam.rds.model.DataSourceConfigurationProperties
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import javax.sql.DataSource

@Configuration
@EnableConfigurationProperties(DataSourceConfigurationProperties::class)
class DataSourceConfiguration(private val dataSourceConfigurationProperties: DataSourceConfigurationProperties) {
    @Bean
    @Primary
    fun dataSource(): DataSource {
        logger.info("Datasource is starting")
        return DataSourceBuilder.create()
            .type(CustomHikariConfiguration::class.java)
            .driverClassName(dataSourceConfigurationProperties.driverClassName)
            .url(dataSourceConfigurationProperties.url)
            .username(dataSourceConfigurationProperties.userName)
            .build()
    }

    companion object {
        private val logger: Logger by lazy { LoggerFactory.getLogger(DataSourceConfiguration::class.java) }
    }
}

