import com.beust.kobalt.*
import com.beust.kobalt.plugin.packaging.*
import com.beust.kobalt.plugin.kotlin.*
import com.beust.kobalt.plugin.publish.*
import com.beust.kobalt.plugin.linecount.lineCount

val plugins = plugins(
//    "com.beust.kobalt:kobalt-line-count:0.8"
//    file(homeDir("kotlin/kobalt-line-count/kobaltBuild/libs/kobalt-line-count-0.8.jar"))
    file(homeDir("kotlin/kobalt-line-count/kobaltBuild/libs/kobalt-line-count-0.12.jar"))
)

val lc = lineCount {
    suffix = "**.md"
}
val project = kotlinProject {
    name = "kobalt-line-count"
    group = "com.beust.kobalt"
    artifactId = name
    version = "0.12"

    dependencies {
//        compile("file:" + homeDir("kotlin/kobalt/kobaltBuild/libs/kobalt-0.168.jar"))
        compile("com.beust:kobalt:0.171")
    }
}

val packProject = assemble(project) {
    mavenJars {
        manifest {
            attributes("Kobalt-Plugin-Class", "com.beust.kobalt.plugin.linecount.LineCountPlugin")
        }
    }
}

val jc = jcenter(project) {
    publish = true
}
