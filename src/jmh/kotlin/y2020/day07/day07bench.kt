package y2020.day07

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
import java.io.File
/*
@State(Scope.Thread)
open class Day7Bench {
    private lateinit var values: Map<String, Bag>

    @Setup
    fun prepare() {
        values = getData(File("/home/bjo/Software/Mine/advent_of_code"))
    }

    @Benchmark
    fun cacheOpt(): Int = values.count { (k, v) ->
        v.containsCached(
            "shiny gold",
            mutableMapOf("shiny gold" to true)
        )
    }

    @Benchmark
    fun naive(): Int = values.count { (k, v) -> v.contains("shiny gold") }

}
*/