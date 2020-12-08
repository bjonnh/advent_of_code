package y2020.day08

import helpers.linesFile
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import java.io.File

@State(Scope.Thread)
open class Day8Bench {
    lateinit var lines: List<String>

    @Setup
    fun prepare() {
        lines = linesFile("/home/bjo/Software/Mine/advent_of_code/data/2020/08_input.txt")
    }


    @Benchmark
    fun naive(): Pair<Int, Int> {
        val o = run(lines)
        System.gc()
        return o
    }

}
