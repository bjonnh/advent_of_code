package y2020.day15

// We can reach sub 300ms with the Epsilon GC compressed pointers and oops, Xshared Xmx and ms of 1g and large pages

fun finalNumCached(list: List<Int>, turns: Int): Int {
    val cache = IntArray(turns) { -1 }
    list.forEachIndexed { idx, it -> cache[it] = idx }
    val num = turns - 1
    if (num < list.size) return list[num]
    var lastNum = list.last()
    (list.size - 1 until num).forEach { pos ->
        val value = cache[lastNum]
        cache[lastNum] = pos
        lastNum = if (value == -1) 0 else pos - value
    }
    return lastNum
}

fun main() {
    val input = listOf(9, 3, 1, 0, 8, 4)
    println(finalNumCached(input, 2020))
    println(finalNumCached(input, 30_000_000))
}