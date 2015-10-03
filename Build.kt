import com.beust.kobalt.*
import com.beust.kobalt.plugin.packaging.*
import com.beust.kobalt.plugin.kotlin.*

val repos = repos("https://dl.bintray.com/cbeust/maven/")

val project = kotlinProject {
    name = "kobalt-line-count"
    group = "com.beust.kobalt"
    artifactId = name
    version = "0.2"

    dependencies {
        compile("com.beust:kobalt:0.139")
    }
}

val packProject = assemble(project) {
    jar {
        manifest {
            attributes("Kobalt-Plugin-Class", "com.beust.kobalt.plugin.linecount.Main")
        }
    }
}
