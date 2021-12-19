import kotlin.math.abs

fun main() {
    fun part1(input: List<Segment>): Int {
        val sizes = biggestHorizontalAnVertical(input)
        val diagram = Array(sizes.first + 1) { Array(sizes.second + 1) { 0 } }
        var count = 0
        for (segment in input) {
            val path = generatePath(segment)
            for (step in path) {
                diagram[step.y][step.x]++
            }
        }

        for (line in diagram) {
            for (row in line) {
                if (row > 1) {
                    count++
                }
            }
        }

        return count
    }

    fun part2(input: List<Segment>): Int {
        val sizes = biggestHorizontalAnVertical(input)
        val diagram = Array(sizes.first + 1) { Array(sizes.second + 1) { 0 } }
        var count = 0
        for (segment in input) {
            val path = generatePath(segment)
            for (step in path) {
                diagram[step.y][step.x]++
            }

            val pathDiagonal = generateDiagonal(segment)
            for (step in pathDiagonal) {
                diagram[step.y][step.x]++
            }
        }

        for (line in diagram) {
            for (row in line) {
                if (row > 1) {
                    count++
                }
            }
        }

        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = toSegmentList(readInputCoordinates("Day05_test"))
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)


    val input = toSegmentList(readInputCoordinates("Day05"))
    check(part1(input) == 5608)
    check(part2(input) == 20299)
    println(part1(input))
    println(part2(input))


}

fun toSegmentList(input: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>): List<Segment> {
    return input.map { Segment(it.first.toCoordinate(), it.second.toCoordinate()) }
}

fun biggestHorizontalAnVertical(input: List<Segment>): Pair<Int, Int> {
    val horizontal: MutableSet<Int> = mutableSetOf()
    val vertical: MutableSet<Int> = mutableSetOf()

    for (segment in input) {
        horizontal.add(segment.start.x)
        horizontal.add(segment.end.x)
        vertical.add(segment.start.y)
        vertical.add(segment.end.y)
    }

    return Pair(horizontal.maxOrNull()!!, vertical.maxOrNull()!!)
}

data class Coordinate(val x: Int, val y: Int)

fun Pair<Int, Int>.toCoordinate(): Coordinate {
    return Coordinate(this.first, this.second)
}

data class Segment(val start: Coordinate, val end: Coordinate)

fun generatePath(segment: Segment): List<Coordinate> {
    val result: MutableList<Coordinate> = mutableListOf()

    if (segment.start.x != segment.end.x && segment.start.y != segment.end.y) return result
    if (segment.start.x > segment.end.x) {
        val difference = segment.start.x - segment.end.x
        for (i in 0..difference) {
            result.add(Coordinate(segment.end.x + i, segment.end.y))
        }
    }

    if (segment.end.x > segment.start.x) {
        val difference = segment.end.x - segment.start.x
        for (i in 0..difference) {
            result.add(Coordinate(segment.end.x - i, segment.end.y))
        }
    }

    if (segment.start.y > segment.end.y) {
        val difference = segment.start.y - segment.end.y
        for (i in 0..difference) {
            result.add(Coordinate(segment.end.x, segment.end.y + i))
        }
    }

    if (segment.end.y > segment.start.y) {
        val difference = segment.end.y - segment.start.y
        for (i in 0..difference) {
            result.add(Coordinate(segment.end.x, segment.end.y - i))
        }
    }

    return result
}


fun isDiagonal(segment: Segment): Boolean {
  return (abs(segment.start.x - segment.start.y ) == abs(segment.end.x - segment.end.y )) || (abs(segment.start.x + segment.start.y ) == abs(segment.end.x + segment.end.y ))
}

fun generateDiagonal(segment: Segment): List<Coordinate> {
    val result: MutableList<Coordinate> = mutableListOf()

    if (!isDiagonal(segment)) return result

    val steps = abs(segment.start.x - segment.end.x)
    for (i in 0..steps) {
        var x = segment.start.x
        var y = segment.start.y

        if (segment.start.x - segment.end.x > 0) {
            x -= i
        } else {
            x += i
        }

        if (segment.start.y - segment.end.y > 0) {
            y -= i
        } else {
            y += i
        }
        result.add(Coordinate(x, y))

    }
    return result
}