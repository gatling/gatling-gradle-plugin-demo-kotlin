# core-experience-performance-tests

Project for measuring API performance and experimenting with load. Uses Gatling.
Intended to be run locally - against our staging environment.
When running anymore than a couple of hundred requests, consider notifying down stream teams.
===============================================

## Includes:

* Gradle Wrapper, so you don't need to install Gradle (a JDK must be installed and $JAVA_HOME configured)
* minimal `build.gradle.kts` leveraging Gradle wrapper
* latest version of `io.gatling.gradle` plugin applied
* proper source file layout

## Requirements:

* Java 21
* AI_COACH_CLIENT_SECRET env var set
  from <https://vault.internal.thriveglobal.engineering/ui/vault/secrets/services/kv/staging%2Fai-coach%2Fai-coach%2Fsecrets/details?version=9>

### Data Source:

```bash
SELECT DISTINCT user_id
FROM user_intentions
WHERE intention IS NOT NULL
  AND TRIM(intention) <> ''
  AND TRIM(intention) <> '<redacted>'
LIMIT 100;
```

, and put into the users.csv file

### Data Source Cleanup:

```bash
DELETE
FROM daily_task_list
WHERE thrive_user_id IN (...)
```

## Running simulations

```bash
./gradlew gatlingRun --simulation com.thriveglobal.perf.DailyTaskListSimulation
```