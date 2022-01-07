fun main() {

    val openSymbol = setOf("(", "[", "{", "<")
    val closeSymbol = setOf(")", "]", "}", ">")
    val openSymbolTranslator = mapOf("(" to ")", "[" to "]", "{" to "}", "<" to ">")
    val closeSymbolTranslator = mapOf(")" to "(", "]" to "[", "}" to "{", ">" to "<")
    val closeSymbolPoint = mapOf(")" to 3, "]" to 57, "}" to 1197, ">" to 25137)
    val closeSymbolPoint2 = mapOf(")" to 1, "]" to 2, "}" to 3, ">" to 4)

    fun processLine(line: List<String>): Int {
        val stack = ArrayDeque<String>()
        for (char in line) {
            if (openSymbol.contains(char)) {
                stack.add(char)
            }
            if (closeSymbol.contains(char)) {
                val symbol = stack.removeLast()
                if (symbol != closeSymbolTranslator[char]) {
                    return closeSymbolPoint.getOrDefault(char, 0)
                }
            }
        }
        return 0
    }

    fun findIndexToDelete(line: List<String>): List<Int> {
        val list: MutableList<Int> = mutableListOf()
        for (i in 0 until line.lastIndex) {
            if (openSymbol.contains(line[i])) {
                if (line[i + 1] == openSymbolTranslator[line[i]]) {
                    list.add(i)
                    list.add(i + 1)
                }
            }
        }
        return list
    }

    fun processIncompleteLine(line: List<String>): Long {
        var result = 0L
        val list: MutableList<String> = line.toMutableList()

        do {
            val toDelete = findIndexToDelete(list)
            for ((index, delete) in toDelete.withIndex()) {
                list.removeAt(delete - index)
            }
        } while (toDelete.isNotEmpty())

        for (char in list.reversed()) {
            result = (result * 5) + closeSymbolPoint2.getOrDefault(openSymbolTranslator[char], 0)
        }
        return result
    }

    fun part1(lists: List<List<String>>): Int {
        var result = 0
        for (list in lists) {
            val points = processLine(list)
            if (points != 0) {
                result += points
            }
        }
        return result
    }

    fun part2(lists: List<List<String>>): Long {
        val result: MutableList<Long> = mutableListOf()
        for (list in lists) {
            val points = processLine(list)
            if (points != 0) {
                continue
            } else {
                val points2 = processIncompleteLine(list)
                result.add(points2)
            }
        }
        return result.sorted()[result.size / 2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test").map { it.chunked(1) }
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("Day10").map { it.chunked(1) }
    check(part1(input) == 469755)
    //  check(part2(input) == 1110780)
    println(part1(input))
    println(part2(input))

}
