package com.rds.iam.rds.model

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "spring.datasource")
data class DataSourceConfigurationProperties(
    val url: String,
    val driverClassName: String,
    val userName: String,
)