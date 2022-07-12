package com.rds.iam.rds.model

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "spring.quartz.properties.org.quartz.data-source.quartz-data-source")
data class QuartzDataSourceConfigurationProperties(
    val url: String,
    val driver: String,
    val user: String,
)