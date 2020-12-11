package y2015.day11


fun String.validPassword(): Boolean {
    if (this.any { it in arrayOf('i', 'o', 'l') }) return false
    if (this.length != 8) return false
    var firstValidPair = -10
    val repPairs = (0..this.length - 2).count { pos ->
        (this[pos + 1] == this[pos] && (pos - firstValidPair) > 1).also { if (it) firstValidPair = pos }
    }
    if (repPairs != 2) return false
    val increasing = (0..this.length - 3).any { // sounds like the work for a window
        (this[it + 2] == this[it + 1] + 1) && (this[it + 1] == this[it] + 1)
    }
    if (!increasing) return false
    return true
}

fun String.nextPass(): String {
    var newPass = this.toCharArray()
    var carry = 0
    newPass[7] = newPass[7] + 1
    for (i in 7 downTo 0) {
        var newChar = newPass.get(i) + carry
        if (newChar > 'z') {
            newChar = 'a'
            carry = 1
        } else {
            carry = 0
        }
        newPass[i] = newChar
    }
    return newPass.concatToString()
}

fun String.nextValidPass(): String {
    var nextPass = this.nextPass()
    while (!nextPass.validPassword()) {
        nextPass = nextPass.nextPass()
    }
    return nextPass
}

fun main() {
    println("hepxcrrq".nextValidPass())
    println("hepxcrrq".nextValidPass().nextValidPass())
}