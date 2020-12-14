package helpers


fun Collection<Char>.fromBinarytoLong() = this.joinToString("").toLong(2)
fun Int.toBinaryPadded(length: Int) = this.toString(2).padStart(length, '0')
fun Long.toBinaryPadded(length: Int) = this.toString(2).padStart(length, '0')
