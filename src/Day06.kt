fun main() {
    fun part1(input: List<Int>, days: Int): Long {
        val states = Array(9) {0L}

        for (state in input) {
            states[state] += 1L
        }

        for (i in 0 until days) {
            val newFishes = states[0]
            for (j in 0 until 8) {
                states[j] = states[j+1]
            }
            states[6] += newFishes
            states[8] = newFishes
        }
        return states.sum()
    }

    fun part2(input: List<Int>, days: Int): Long {
        return part1(input, days)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")[0].split(",").map { it.toInt() }
    check(part1(testInput, 18) == 26L)
    check(part1(testInput, 80) == 5934L)
    check(part2(testInput, 256) == 26984457539)

    val input = readInput("Day06")[0].split(",").map { it.toInt() }
    check(part1(input, 80) == 365862L)
    check(part2(input, 256) == 1653250886439)
    println(part1(input, 80))
    println(part2(input, 256))


}

