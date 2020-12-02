package y2020.day01

import java.io.File
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

fun main() {
    var case1: Int? = null
    var case2: Int? = null

    val values: IntArray
    measureNanoTime {
        //println("Reading data")
        // A version that use a hard-coded array to demonstrate how fast it is
        values = makeArray() //File("data/input_puzzle_1.csv").readLines().map { it.toInt() }.toIntArray()
    }.let { println("Reading the file took ${it/1_000_000.0} ms") }

    println("Lets run the crazy optimized version")
    crazyOpt(values)
    println("Lets run the intermediary version")
    intermediary(values)
    println("Lets run the dumb version")
    mainDumb(values)
}

fun mainDumb(values: IntArray) {
    var case1: Int? = null
    var case2: Int? = null

    val count = measureTimeMillis {
        // It is fast enough that we don't need to reduce complexity
        // We could have exited the loops as soon as an answer is found
        // We also don't need to do the full permutations 3 times (2000^3)
        // again it took a few ms to find the answer so… not worth it here.

        values.forEach { v1 ->
            values.forEach { v2 ->
                if ((v1 + v2) == 2020) {
                    case1 = v1 * v2
                }

                values.forEach { v3 ->
                    if ((v1 + v2 + v3) == 2020) {
                        case2 = v1 * v2 * v3
                    }
                }
            }
        }
    }

    println("It took me $count ms")
    println("Solution 1: $case1")
    println("Solution 2: $case2")
}

fun intermediary(values: IntArray) {
    var case1: Int? = null
    var case2: Int? = null

    val count = measureTimeMillis {
        values.forEach out@{ v1 ->
            values.forEach { v2 ->
                if (case1 == null) {
                    if ((v1 + v2) == 2020) {
                        case1 = v1 * v2
                    }
                }

                if (case2 == null) {
                    values.forEach { v3 ->
                        if ((v1 + v2 + v3) == 2020) {
                            case2 = v1 * v2 * v3
                        }
                    }
                }

                if ((case1 != null) && (case2 != null)) return@out
            }
        }
    }

    println("It took me $count ms")
    println("Solution 1: $case1")
    println("Solution 2: $case2")
}

fun crazyOpt(values: IntArray) {
    var case1: Int? = null
    var case2: Int? = null

    val count = measureNanoTime {
        run out@{
            for (i in values.indices) {
                val v1 = values[i]
                for (j in i until values.size) {
                    val v2 = values[j]
                    if (case1 == null) {
                        if ((v1 + v2) == 2020) {
                            case1 = v1 * v2
                        }
                    }
                    if ((case2 == null) && (v1 + v2) < 2020) {
                        for (k in j until values.size) {
                            val v3 = values[k]

                            if ((v1 + v2 + v3) == 2020) {
                                case2 = v1 * v2 * v3
                            }
                        }
                    }
                    if ((case1 != null) && (case2 != null)) return@out
                }
            }
        }
    }

    println("It took me ${count / 1_000_000.0} ms")
    println("Solution 1: $case1")
    println("Solution 2: $case2")
}

fun makeArray() =
    intArrayOf(
        1714, 1960, 1256, 1597, 1853, 1609, 1936, 2003, 1648, 1903, 1248, 1525, 1330, 1281, 1573, 1892, 1563, 1500,
        1858, 176, 1802, 1370, 1708, 1453, 1342, 1830, 1580, 1607, 1848, 1626, 1602, 1919, 1640, 1574, 1414, 766, 1581,
        1924, 1727, 1949, 1406, 323, 957, 1862, 1354, 1427, 1583, 1067, 1863, 1553, 1923, 1990, 691, 1372, 1357, 1887,
        1485, 1799, 1270, 1743, 1601, 1457, 1723, 1888, 1272, 1600, 1880, 1381, 1413, 1452, 277, 1866, 1542, 1693, 1760,
        1637, 1675, 1975, 1304, 1327, 1985, 1842, 1255, 1915, 1266, 1944, 1824, 1770, 1392, 1259, 1313, 1547, 1293,
        1393, 1896, 1828, 1642, 1979, 1871, 1502, 1548, 1508, 710, 1786, 1845, 1334, 1362, 1940, 2009, 1271, 1448, 1964,
        1676, 1654, 1804, 1835, 1910, 1939, 1298, 1572, 1704, 1841, 1399, 1576, 1164, 1868, 1035, 262, 1569, 1639, 1669,
        1543, 1616, 1658, 1750, 1765, 1718, 1861, 1351, 1531, 1665, 1771, 1348, 1289, 875, 1408, 1486, 1275, 1625, 1594,
        1816, 704, 1800, 1564, 1291, 1234, 1981, 1843, 1387, 1938, 1827, 1883, 1911, 1755, 1353, 1808, 1498, 1416, 2006,
        1916, 1411, 1539, 1963, 1874, 1898, 1951, 1292, 1366, 1912, 1369, 1478, 1359, 1859, 1421, 1384, 1534, 1283,
        1913, 1794, 1494, 1860, 1312, 1869, 1730, 1510, 1319, 1428, 1706, 1432, 1532
    )
