fun main() {
    fun part1(input: List<Pair<String, Int>>): Int {
        var horizontal = 0
        var depth = 0
        for ((command, units) in input) {
            when (command) {
                "up" -> depth -= units
                "down" -> depth += units
                "forward" -> horizontal += units
            }
        }
        return horizontal * depth
    }

    fun part2(input: List<Pair<String, Int>>): Int {
        var horizontal = 0
        var depth = 0
        var aim = 0
        for ((command, units) in input) {
            when (command) {
                "up" -> aim -= units
                "down" -> aim += units
                "forward" -> {
                    horizontal += units
                    depth += units * aim
                }
            }
        }
        return horizontal * depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsStringIntPair("Day02_test")
    check(part1(testInput) == 150)

    val input = readInputAsStringIntPair("Day02")
    println(part1(input))
    println(part2(input))
}
