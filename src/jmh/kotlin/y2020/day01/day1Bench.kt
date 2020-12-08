package y2020.day01

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State
/*
@State(Scope.Thread)
open class Day1Bench {
    private lateinit var values: IntArray

    @Setup
    fun prepare() {
        values = makeArray()
    }

    @Benchmark
    fun crazyOpt(): Pair<Int?, Int?> = crazyOpt(values)

    @Benchmark
    fun Dumb(): Pair<Int?, Int?> = mainDumb(values)

    @Benchmark
    fun Intermediary(): Pair<Int?, Int?> = intermediary(values)
}
*/