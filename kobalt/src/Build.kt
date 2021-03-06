import com.beust.kobalt.*
import com.beust.kobalt.plugin.packaging.*
import com.beust.kobalt.plugin.kotlin.*
import com.beust.kobalt.plugin.publish.*
//import com.beust.kobalt.plugin.linecount.lineCount

//val plugins = plugins(
//    "com.beust.kobalt:kobalt-line-count:0.14"
//    file(homeDir("kotlin/kobalt-line-count/kobaltBuild/libs/kobalt-line-count-0.14.jar"))
//)

//val lc = lineCount {
//    suffix = "**Plugin.kt"
//}

val project = project {
    name = "kobalt-line-count"
    group = "com.beust.kobalt"
    artifactId = name
    version = "0.23"

    dependencies {
        compile("com.beust:kobalt-plugin-api:0.939")
    }

    assemble {
        mavenJars {
        }
    }

    bintray {
        publish = true
    }

}

