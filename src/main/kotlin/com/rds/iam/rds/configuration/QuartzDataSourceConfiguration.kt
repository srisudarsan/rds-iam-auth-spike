package com.rds.iam.rds.configuration

import com.rds.iam.rds.model.QuartzDataSourceConfigurationProperties
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
@EnableConfigurationProperties(QuartzDataSourceConfigurationProperties::class)
class QuartzDataSourceConfiguration(
    private val dataSourceConfigurationProperties: QuartzDataSourceConfigurationProperties
) {
    @Bean
    @QuartzDataSource
    fun quartzDataSource(): DataSource {
        logger.info("Quartz Datasource is starting")
        return DataSourceBuilder.create()
            .type(CustomHikariConfiguration::class.java)
            .driverClassName(dataSourceConfigurationProperties.driver)
            .url(dataSourceConfigurationProperties.url)
            .username(dataSourceConfigurationProperties.user)
            .build()
    }

    companion object {
        private val logger: Logger by lazy { LoggerFactory.getLogger(QuartzDataSourceConfiguration::class.java) }
    }
}