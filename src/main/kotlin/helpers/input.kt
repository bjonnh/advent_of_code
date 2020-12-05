package helpers

import java.io.File

fun textFile(name: String) = File(name).readText()
fun linesFile(name: String) = File(name).readLines()
