import androidx.room.gradle.RoomExtension
import com.cevichepicante.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidRoomConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "androidx.room")
            apply(plugin = "com.google.devtools.ksp")

            extensions.configure<RoomExtension> {
                schemaDirectory("$projectDir/schemas")
            }

            dependencies {
                "ksp"(libs.findLibrary("androidx-room-compiler").get())
                "implementation"(libs.findLibrary("androidx-room").get())
                "implementation"(libs.findLibrary("androidx-room-ktx").get())
            }
        }
    }
}