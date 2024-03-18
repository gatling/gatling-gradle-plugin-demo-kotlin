import java.io.File
import java.nio.file.Path

object IDEPathHelper {

  val gradleBinariesDirectory =
          System.getProperty("java.class.path").split(File.pathSeparator)
                  .stream()
                  .map { cpe ->  Path.of(cpe) }
                  .filter { cpe -> cpe.endsWith("gatling") }
                  .findFirst()
                  .get()

  private val gradleBuildDirectory = gradleBinariesDirectory.parent.parent.parent
  private val projectRootDir = gradleBuildDirectory.parent
  private val gradleSrcDirectory = projectRootDir.resolve("src").resolve("gatling")

  val gradleSourcesDirectory: Path = gradleSrcDirectory.resolve("kotlin")
  val gradleResourcesDirectory: Path = gradleSrcDirectory.resolve("resources")
  val resultsDirectory: Path = gradleBuildDirectory.resolve("reports").resolve("gatling")
  val recorderConfigFile: Path = gradleResourcesDirectory.resolve("recorder.conf")
}
