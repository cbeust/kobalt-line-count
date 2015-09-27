import com.beust.kobalt.*
import com.beust.kobalt.plugin.packaging.packaging
import com.beust.kobalt.plugin.kotlin.*

val repos = repos("https://dl.bintray.com/cbeust/maven/")

val project = kotlinProject {
    name = "kobalt-line-count"
    group = "com.beust.kobalt"
    artifactId = name
    version = "0.2"
    directory = "/Users/beust/kotlin/kobalt-line-count"

    sourceDirectories {
        path("src/main/kotlin")
    }

    sourceDirectoriesTest {
        path()
    }

    dependencies {
        compile("com.beust:kobalt:0.71")
    }

//    dependenciesTest {
//        compile("org.testng:testng:6.9.5")
//    }
}

val packProject = packaging(project) {
    jar {
        manifest {
            attributes("Kobalt-Plugin-Class", "com.beust.kobalt.plugin.linecount.Main")
        }
    }
}
