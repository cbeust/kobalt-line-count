package com.beust.kobalt.plugin.linecount

import com.beust.kobalt.api.BasePlugin
import com.beust.kobalt.internal.TaskResult
import com.beust.kobalt.api.*
import com.beust.kobalt.api.annotation.Directive
import com.beust.kobalt.api.annotation.Task
import com.beust.kobalt.misc.KobaltLogger

import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.*

fun main(argv: Array<String>) {
    com.beust.kobalt.main(argv)
}

public class LineCountPlugin : BasePlugin(), KobaltLogger {
    companion object {
        const val NAME : String = "kobalt-line-count"
    }
    override val name = NAME

    var info: LineCountInfo = LineCountInfo()

    override fun apply(project: Project, context: KobaltContext) {
        println("*** Applying plugin $name with project $project")
        println("*** Adding dynamic task")
        addTask(project, "dynamicTask", runBefore = listOf("compile")) {
            println("Dynamic task")
            TaskResult()
        }
    }

    @Task(name = "lineCount", description = "Count the lines", runBefore = arrayOf("compile"))
    fun lineCount(project: Project): TaskResult {
        var fileCount = 0
        var lineCount : Long = 0
        log(1, "Finding files that end in ${info.suffix}")
        val matcher = FileSystems.getDefault().getPathMatcher("glob:" + info.suffix)
        project.sourceDirectories.forEach {
            val path = Paths.get(it)
            if (path.toFile().exists()) {
                Files.walkFileTree(path, object : SimpleFileVisitor<Path>() {
                    override public fun visitFile(path: Path, attrs: BasicFileAttributes): FileVisitResult {
                        if (matcher.matches(path)) {
                            fileCount++
                            lineCount += Files.lines(path).count()
                            log(3, "  MATCH $path")
                        }
                        return FileVisitResult.CONTINUE
                    }
                })
            }
        }
        log(1, "Found $lineCount lines in $fileCount files")
        return TaskResult()
    }
}

data class LineCountInfo(var suffix: String = "**kt")

@Directive
public fun lineCount(init: LineCountInfo.() -> Unit): LineCountInfo {
    with(LineCountInfo()) {
        init()
        (Kobalt.findPlugin(LineCountPlugin.NAME) as LineCountPlugin).info = this
        return this
    }
}
