package com.thriveglobal.perf

class Queries {
  companion object {
    val fetchDailyTaskListByUser: String =
        """
        query FetchDailyTaskListByUser(${'$'}userId: UUID!) {
          dailyExperience {
            dailyTaskList {
              admin {
                fetchDailyTaskListByUser(userID: ${'$'}userId) {
                  activeTimeOfDay
                  dailyTaskItems {
                    id
                    entity {
                      ... on ArticleV2 {
                        id
                        __typename
                      }
                      ... on DailyCheckIn {
                        status
                        __typename
                        response {
                          acuteCare {
                            title
                          }
                        }
                      }
                      ... on GraphQlMicrostep {
                        mid:id
                        __typename
                      }
                      ... on PersonalizedReset {
                        pid:id
                        __typename
                      }
                      ... on ThriveReset {
                        tid:id
                        __typename
                      }
                    }
                  }
                }
              }
            }
          }
        }
        """
            .trimIndent()
  }
}
