fun main() {
    fun part1(input: List<Int>): Int {
        var result = Int.MAX_VALUE
        val positions = input.size

        for (i in 0 until positions) {
            var current = 0
            for (j in input) {
                current += if (j > i) {
                    (j-i)
                } else {
                    (i-j)
                }
            }
            if (current < result) {
                result = current
            }
        }
        return result
    }

    fun part2(input: List<Int>): Int {
        var result = Int.MAX_VALUE
        val positions = input.size

        for (i in 0 until positions) {
            var current = 0
            for (j in input) {
                current += if (j > i) {
                    (0 .. j-i).sum()
                } else {
                    (0 .. i-j).sum()
                }
            }
            if (current < result) {
                result = current
            }
        }
        return result    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")[0].split(",").map { it.toInt() }
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07")[0].split(",").map { it.toInt() }
    check(part1(input) == 336131)
    check(part2(input) == 92676646)
    println(part1(input))
    println(part2(input))


}

