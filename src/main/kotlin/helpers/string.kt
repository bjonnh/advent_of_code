package helpers

infix fun String?.inRange(v: Pair<Int, Int>): Boolean = this?.toIntOrNull() in v.first..v.second
fun String?.rFindGroups(r: String) = r.toRegex().find(this ?: "")?.groupValues
fun String?.rFindGroupsDestructured(r: String) = r.toRegex().find(this ?: "")?.destructured
fun String?.rMatches(r: String) = (this?.matches(r.toRegex()) == true)