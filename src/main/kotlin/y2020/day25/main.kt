package y2020.day25

const val COLOR = 20201227
const val SUBJECT = 7

fun encrypt(turns: Int, subject: Long) = generateSequence(1L) { (it * subject) % COLOR }.elementAtOrNull(turns) ?: -1L
fun findLoopsForValue(value: Long): Int = generateSequence(1L) { (it * SUBJECT) % COLOR }.indexOf(value)

fun main() {
    val cardPubKey = 2959251L //5764801L
    val doorPubKey = 4542595L //17807724L
    val cardTurns = findLoopsForValue(cardPubKey)
    println(encrypt(cardTurns, doorPubKey))
}