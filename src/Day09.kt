fun main() {
    fun getSurroundings(i: Int, j: Int, heightmap: List<List<Int>>): List<Int> {
        val result = mutableListOf<Int>()

        try {
            result.add(heightmap[i][j - 1])
        } catch (_: Exception) {

        }

        try {
            result.add(heightmap[i][j + 1])
        } catch (_: Exception) {

        }

        try {
            result.add(heightmap[i - 1][j])
        } catch (_: Exception) {

        }

        try {
            result.add(heightmap[i + 1][j])
        } catch (_: Exception) {

        }

        return result

    }

    fun getSurroundingsPositions(i: Int, j: Int, heightmap: List<List<Int>>): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        val result2 = mutableListOf<Int>()


        try {
            result2.add(heightmap[i][j - 1])
            result.add(Pair(i, j - 1))
        } catch (_: Exception) {

        }

        try {
            result2.add(heightmap[i][j + 1])
            result.add(Pair(i, j + 1))
        } catch (_: Exception) {

        }

        try {
            result2.add(heightmap[i - 1][j])
            result.add(Pair(i - 1, j))
        } catch (_: Exception) {

        }

        try {
            result2.add(heightmap[i + 1][j])
            result.add(Pair(i + 1, j))
        } catch (_: Exception) {

        }

        return result

    }

    fun getAdjacentNumber(currentPair: Pair<Int, Int>, heightmap: List<List<Int>>): List<Pair<Int, Int>> {
        val points = mutableListOf<Pair<Int, Int>>()
        val surroundings = getSurroundingsPositions(currentPair.first, currentPair.second, heightmap)
        for (surrounding in surroundings) {
            if (heightmap[surrounding.first][surrounding.second] !=9) {
                points.add(surrounding)
            }
        }
        return points
    }

    fun getAdjacentNumbers(surroundings: List<Pair<Int, Int>>, heightmap: List<List<Int>>): List<Int> {
        var points = mutableListOf(0,0,0)
        for (surrounding in surroundings) {
            val result = mutableSetOf<Pair<Int, Int>>()
            val done = mutableSetOf<Pair<Int, Int>>()
            result.add(surrounding)
            val list = getAdjacentNumber(surrounding, heightmap).toMutableList()
            result.addAll(list)
            while (list.isNotEmpty()) {
                val element = list[0]
                list.removeAt(0)
                val numbers = getAdjacentNumber(element, heightmap)
                done.add(element)
                for (number in numbers) {
                    if (!done.contains(number)) {
                        list.add(number)
                    }
                }
                result.addAll(numbers)
            }
            if (points[2] < result.size) {
                points[2] = result.size
                points = points.sorted().reversed().toMutableList()
            }
        }
        return points
    }

    fun getNumber(number: Int, surroundings: List<Int>): Int {
        var result = true

        for (surroundingNumber in surroundings) {
            result = result && (number < surroundingNumber)
        }

        if (result) {
            return number + 1
        }
        return 0
    }


    fun part1(heightmap: List<List<Int>>): Int {
        var result = 0
        for ((i, smokeFlow) in heightmap.withIndex()) {
            for ((j, flow) in smokeFlow.withIndex()) {
                val surroundings = getSurroundings(i, j, heightmap)
                result += getNumber(flow, surroundings)
            }
        }
        return result
    }

    fun part2(heightmap: List<List<Int>>): Int {
        val points = mutableListOf<Pair<Int, Int>>()
        for ((i, smokeFlow) in heightmap.withIndex()) {
            for ((j, flow) in smokeFlow.withIndex()) {
                val surroundings = getSurroundings(i, j, heightmap)
                if (getNumber(flow, surroundings) != 0) {
                    points.add(Pair(i, j))
                }
            }
        }
        val result = getAdjacentNumbers(points, heightmap)
        return result[0] * result[1] * result[2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test").map { it.chunked(1).map { it2 -> it2.toInt() } }
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("Day09").map { it.chunked(1).map { it2 -> it2.toInt() } }
    check(part1(input) == 532)
    check(part2(input) == 1110780)
    println(part1(input))
    println(part2(input))

}
