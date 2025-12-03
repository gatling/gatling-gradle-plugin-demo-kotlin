package com.thriveglobal.perf

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.michaelbull.logging.InlineLogger
import io.gatling.javaapi.core.CoreDsl.*
import io.gatling.javaapi.core.Simulation
import io.gatling.javaapi.http.HttpDsl.*
import io.gatling.javaapi.http.HttpProtocolBuilder

class DailyTaskListSimulation : Simulation() {

  private val logger = InlineLogger()

  private val mapper = ObjectMapper()

  // Fetch token ONCE
  private val bearerToken: String = AuthService(mapper).fetchAccessToken()

  private val httpProtocol: HttpProtocolBuilder =
      http
          .baseUrl("https://graph.stag.thriveglobal.com")
          .contentTypeHeader("application/json")
          .acceptHeader("application/json")
          .header("Authorization", "Bearer $bearerToken")

  private val userFeeder = csv("users.csv").queue()

  private val scn =
      scenario("DailyTaskListCallsOncePerUser")
          .feed(userFeeder)
          .exec(
              http("FetchDailyTaskList")
                  .post("/graphql")
                  .body(
                      StringBody { session ->
                        val userId = session.getString("userId")

                        val payload =
                            mapOf(
                                "query" to Queries.fetchDailyTaskListByUser,
                                "variables" to mapOf("userId" to userId),
                            )

                        mapper.writeValueAsString(payload)
                      }
                  )
                  .check(
                      status().`in`(200),
                      substring("error").notExists(),
                      substring("GraphQlMicrostep").count().`is`(3),
                      substring("ArticleV2").count().`is`(2),
                      substring("ThriveReset").exists(),
                      substring("DailyCheckIn").count().`is`(1), // as no fallbacks - check this
                      bodyString().saveAs("respBody"),
                  )
          )
          .exec { session ->
            if (session.isFailed) {
              logger.error {
                "❌ CHECK FAILED — user=${session.getString("userId")}. Response body:\n${
                  session.getString(
                      "respBody"
                  )
              }"
              }
            }
            session
          }

  init {
    setUp(
            scn.injectOpen(
                                atOnceUsers(10)
//                constantUsersPerSec(5.0)
//                    .during(20) // 10 users/second for 10 seconds = 100 users total
            )
        )
        .protocols(httpProtocol)
  }
}
