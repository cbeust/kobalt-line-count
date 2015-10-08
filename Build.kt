import com.beust.kobalt.*
import com.beust.kobalt.plugin.packaging.*
import com.beust.kobalt.plugin.kotlin.*
import com.beust.kobalt.plugin.publish.*


val repos = repos("https://dl.bintray.com/cbeust/maven/")

val plugins = plugins(
    "com.beust.kobalt:kobalt-line-count:0.8"
//    file("/Users/beust/kotlin/kobalt-line-count/kobaltBuild/libs/kobalt-line-count-0.6.jar")
)

val project = kotlinProject {
    name = "kobalt-line-count"
    group = "com.beust.kobalt"
    artifactId = name
    version = "0.8"

    dependencies {
        compile("com.beust:kobalt:0.15")
    }
}

val packProject = assemble(project) {
    mavenJars {
        manifest {
            attributes("Kobalt-Plugin-Class", "com.beust.kobalt.plugin.linecount.Main")
        }
    }
}

val jc = jcenter(project) {
    publish = true
}

