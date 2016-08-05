package com.beust.kobalt.plugin.linecount

import com.beust.kobalt.TaskResult
import com.beust.kobalt.api.*
import com.beust.kobalt.api.annotation.Directive
import com.beust.kobalt.api.annotation.Task
import com.beust.kobalt.misc.log
import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes

//fun main(argv: Array<String>) {
//    com.beust.kobalt.main(argv)
//}

public class LineCountPlugin : BasePlugin(), ITaskContributor {
    // ITaskContributor
    override fun tasksFor(project: Project, context: KobaltContext) = listOf(
            DynamicTask(this,
                    "dynamicTask",
                    "Dynamic task",
                    "other",
                    project,
                    runBefore = listOf("compile"),
                    closure = {
                        TaskResult()
                    }
            )
    )

    companion object {
        const val NAME : String = "kobalt-line-count"
    }
    override val name = NAME

    var info: LineCountInfo = LineCountInfo()

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
