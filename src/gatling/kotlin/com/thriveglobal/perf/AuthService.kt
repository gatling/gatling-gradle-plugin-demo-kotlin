package com.thriveglobal.perf

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.michaelbull.logging.InlineLogger
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class AuthService(private val mapper: ObjectMapper) {

  private val logger = InlineLogger()

  private val tokenUrl =
      "https://identity-auth.stag.thriveglobal.com/realms/ThriveGlobal/protocol/openid-connect/token"

  private val clientId: String = "service-client-ai-coach"

  private val clientSecret: String =
      System.getProperty("aiCoachclientSecret")
          ?: System.getenv("AI_COACH_CLIENT_SECRET")
          ?: error(
              "AI_COACH_CLIENT_SECRET env var or -DclientSecretaiCoachclientSecret must be set"
          )

  fun fetchAccessToken(): String {
    logger.info { "Fetching access token from $tokenUrl for clientId=$clientId" }

    val client = HttpClient.newHttpClient()
    val body = "grant_type=client_credentials&client_id=$clientId&client_secret=$clientSecret"

    val request =
        HttpRequest.newBuilder()
            .uri(URI.create(tokenUrl))
            .header("Content-Type", "application/x-www-form-urlencoded")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build()

    val response = client.send(request, HttpResponse.BodyHandlers.ofString())

    if (response.statusCode() != 200) {
      error("Failed to fetch token: HTTP ${response.statusCode()} - ${response.body()}")
    }

    val json = response.body()
    val node = mapper.readTree(json)
    val token =
        node.get("access_token")?.asText() ?: error("access_token not found in token response")

    logger.info { "Got access token (length=${token.length})" }
    return token
  }
}
