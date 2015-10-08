package com.beust.kobalt.plugin.linecount

import com.beust.kobalt.api.BasePlugin
import com.beust.kobalt.internal.TaskResult
import com.beust.kobalt.api.*
import com.beust.kobalt.api.annotation.Task
import com.beust.kobalt.misc.KobaltLogger

import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.*

fun main(argv: Array<String>) {
    com.beust.kobalt.main(argv)
}

public class Main : BasePlugin(), KobaltLogger {
    override val name = "kobalt-line-count"

    override fun apply(project: Project, context: KobaltContext) {
        println("*** Applying plugin ${name} with project ${project}")
    }

    @Task(name = "lineCount", description = "Count the lines", runBefore = arrayOf("compile"))
    fun lineCount(project: Project): TaskResult {

        var fileCount = 0
        var lineCount : Long = 0
        val matcher = FileSystems.getDefault().getPathMatcher("glob:**.kt")
        project.sourceDirectories.forEach {
            val path = Paths.get(it)
            if (path.toFile().exists()) {
                Files.walkFileTree(path, object : SimpleFileVisitor<Path>() {
                    override public fun visitFile(path: Path, attrs: BasicFileAttributes): FileVisitResult {
                        log(2, "File: ${path}")
                        if (matcher.matches(path)) {
                            fileCount++
                            lineCount += Files.lines(path).count()
                            log(2, "  MATCH")
                        }
                        return FileVisitResult.CONTINUE
                    }
                })
            }
        }
        log(1, "Found ${lineCount} lines in ${fileCount} files")
        return TaskResult()
    }
}
