package com.rds.iam.rds.configuration

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.rds.auth.GetIamAuthTokenRequest
import com.amazonaws.services.rds.auth.RdsIamAuthTokenGenerator
import org.postgresql.plugin.AuthenticationPlugin
import org.postgresql.plugin.AuthenticationRequestType
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class PsqlAuthenticationPlugin : AuthenticationPlugin {
    override fun getPassword(type: AuthenticationRequestType): CharArray {
        return getToken().toCharArray()
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
            .userName("app_user")
            .build()
        val authToken = generator.getAuthToken(request)
        logger.info("Generating token :: $authToken")
        return authToken
    }

    private fun getHostnamePort(): Pair<String, Int> {
        val jdbcUrl =
            "jdbc:postgresql://rds-iam-auth-spike.cvfe9s0wvuzt.us-east-1.rds.amazonaws.com:5432/rds_iam_auth_spike"
        val slashing = jdbcUrl.indexOf("//") + 2
        val sub = jdbcUrl.substring(slashing, jdbcUrl.indexOf("/", slashing))
        val splitted = sub.split(":").toTypedArray()
        return Pair(splitted[0], splitted[1].toInt())
    }

    companion object {
        private val logger: Logger by lazy { LoggerFactory.getLogger(PsqlAuthenticationPlugin::class.java) }
    }
}