package y2015.day04

import java.lang.Math.pow
import java.math.BigDecimal
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.math.pow
import kotlin.system.measureTimeMillis

fun ByteArray.toHex() = joinToString("") { "%02x".format(it) }

fun findMatchingMD5(secretKey: String, matchBegin: String): Int {
    val md = MessageDigest.getInstance("MD5")
    var count = 0
    while (true) {
        val sign = secretKey + count.toString()
        val key = md.digest(sign.toByteArray()).toHex()
        if (key.startsWith(matchBegin) || count == 9999999) break
        count++
    }
    return count
}

// There is an obvious optimization here, we can work directly with the bytes
fun findMatchingMD5fast(secretKey: String, numberOfZeros: Int): Int {
    val md = MessageDigest.getInstance("MD5")
    var count = 0

    val secretKeyBA = secretKey.toByteArray()

    val numberOfBytesToGet = ((numberOfZeros - 1) / 2) + 1
    val ignoreLastNibble = (numberOfZeros % 2) == 1
    println("We are going to get $numberOfBytesToGet and the last nibble will be ignored: $ignoreLastNibble")
    val NIB1 = 16.toByte()
    val NIB0 = 0.toByte()
    while (true) {
        val sign = secretKeyBA + count.toString().toByteArray()
        val digest = md.digest(sign)

        var zerosCount = 0
        var loopNumber = 0
        while (zerosCount <= numberOfZeros) {
            if ((digest[loopNumber] == NIB0)) {
                loopNumber += 1
                zerosCount += 2
            } else {
                if (ignoreLastNibble && (zerosCount >= numberOfZeros - 1) && (digest[loopNumber] in 0..NIB1)) {
                    zerosCount += 1
                } else {
                    break
                }
            }
        }

        if (zerosCount >= numberOfZeros) {
            println(digest.toHex())
            break
        }

        if ((count == 9999999)) break
        count++
    }
    return count
}


fun main() {
    val secretkey = "yzbqklnj"
    val part1: Int
    val part2: Int
    val time = measureTimeMillis {
        //val part1 = findMatchingMD5(secretkey, "00000")
        part1 = findMatchingMD5fast(secretkey, 5)
        part2 = findMatchingMD5fast(secretkey, 6) // 9962624
    }
    println("And the answer is: part 1: $part1 part 2: $part2 in $time ms")
}