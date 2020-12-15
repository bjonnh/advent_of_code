package y2020.day15

fun finalNumCached(list: List<Long>, turns: Int): Long {
    val cache = list.mapIndexed { idx, it -> it to idx.toLong() }.toMap().toMutableMap()
    val num = turns - 1
    if (num < list.size) return list[num]
    var lastNum = list.last()
    (list.size - 1 until num.toLong()).map { pos ->
        val oldNum = lastNum
        lastNum = cache[lastNum]?.let { pos - it } ?: 0L
        cache[oldNum] = pos
    }
    return lastNum
}

fun main() {
    val input = listOf(9, 3, 1, 0, 8, 4).map { it.toLong() }
    println(finalNumCached(input, 2020))
    println(finalNumCached(input, 30000000))
}