import java.nio.file.Path
import java.nio.file.Paths

object IDEPathHelper {

  private val projectRootDir = Paths.get(IDEPathHelper::class.java.classLoader.getResource("gatling.conf").toURI()).parent.parent.parent.parent
  private val gradleBuildDirectory = projectRootDir.resolve("build")
  private val gradleSrcDirectory = projectRootDir.resolve("src").resolve("gatling")

  val gradleSourcesDirectory: Path = gradleSrcDirectory.resolve("kotlin")
  val gradleResourcesDirectory: Path = gradleSrcDirectory.resolve("resources")
  val gradleBinariesDirectory: Path = gradleBuildDirectory.resolve("classes").resolve("kotlin").resolve("gatling")
  val resultsDirectory: Path = gradleBuildDirectory.resolve("reports").resolve("gatling")
  val recorderConfigFile: Path = gradleResourcesDirectory.resolve("recorder.conf")
}
