import com.beust.kobalt.*
import com.beust.kobalt.plugin.packaging.*
import com.beust.kobalt.plugin.kotlin.*
import com.beust.kobalt.plugin.publish.*
//import com.beust.kobalt.plugin.linecount.lineCount

//val plugins = plugins(
////    "com.beust.kobalt:kobalt-line-count:0.14"
//    file(homeDir("kotlin/kobalt-line-count/kobaltBuild/libs/kobalt-line-count-0.14.jar"))
//)

//val lc = lineCount {
//    suffix = "**Plugin.kt"
//}

val project = kotlinProject {
    name = "kobalt-line-count"
    group = "com.beust.kobalt"
    artifactId = name
    version = "0.16"

    dependencies {
//        compile("file:" + homeDir("kotlin/kobalt/kobaltBuild/libs/kobalt-0.168.jar"))
        compile("com.beust:kobalt:0.226")
    }

    assemble {
        mavenJars {
        }
    }

    jcenter {
        publish = true
    }

}

