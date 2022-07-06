import io.gatling.recorder.GatlingRecorder
import io.gatling.recorder.config.RecorderPropertiesBuilder
import scala.Option

object Recorder {
  @JvmStatic
  fun main(args: Array<String>) {
    val props = RecorderPropertiesBuilder()
      .simulationsFolder(IDEPathHelper.gradleSourcesDirectory.toString())
      .resourcesFolder(IDEPathHelper.gradleResourcesDirectory.toString())
      .simulationPackage("computerdatabase")
      .simulationFormatKotlin()

    GatlingRecorder.fromMap(props.build(), Option.apply(IDEPathHelper.recorderConfigFile))
  }
}
