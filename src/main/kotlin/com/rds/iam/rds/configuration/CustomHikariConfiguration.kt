package com.rds.iam.rds.configuration

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.rds.auth.GetIamAuthTokenRequest
import com.amazonaws.services.rds.auth.RdsIamAuthTokenGenerator
import com.zaxxer.hikari.HikariDataSource
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration

@Configuration
class CustomHikariConfiguration : HikariDataSource() {
    override fun getPassword(): String {
        return getToken();
    }

    private fun getToken(): String {
        val region = Region.getRegion(Regions.US_EAST_1)
        val hostnamePort = getHostnamePort()
        val generator: RdsIamAuthTokenGenerator = RdsIamAuthTokenGenerator.builder()
            .credentials(DefaultAWSCredentialsProviderChain())
            .region(region)
            .build()
        val request: GetIamAuthTokenRequest = GetIamAuthTokenRequest.builder()
            .hostname(hostnamePort.first)
            .port(hostnamePort.second)
            .userName(username)
            .build()
        val authToken = generator.getAuthToken(request)
        logger.info("Generated token :: $authToken")
        return authToken
    }

    private fun getHostnamePort(): Pair<String, Int> {
        val slashing = jdbcUrl.indexOf("//") + 2
        val sub = jdbcUrl.substring(slashing, jdbcUrl.indexOf("/", slashing))
        val splitted = sub.split(":").toTypedArray()
        return Pair(splitted[0], splitted[1].toInt())
    }

    companion object {
        private val logger: Logger by lazy { LoggerFactory.getLogger(DataSourceConfiguration::class.java) }
    }
}