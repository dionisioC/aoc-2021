fun main() {
    fun part1(input: List<String>): Int {
        val bingo = Bingo(input)
        return bingo.run()
    }

    fun part2(input: List<String>): Int {
        val bingo = Bingo(input)
        return bingo.longRun()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)


    val input = readInput("Day04")
    check(part1(input) == 10680)
    check(part2(input) == 31892)
    println(part1(input))
    println(part2(input))
}

private class Card {
    val lines: MutableList<MutableList<Pair<Int, Boolean>>> = mutableListOf()

    fun addLine(line: String) {
        val numbers = line.trimIndent().split(" ")
        val linetoAdd: MutableList<Pair<Int, Boolean>> = mutableListOf()
        for (number in numbers) {
            if (number.isEmpty()) continue
            linetoAdd.add(Pair(number.trim().toInt(), false))
        }
        lines.add(linetoAdd)
    }

    fun checkWinner(): Pair<Boolean, Int> {
        var winner: Boolean
        val total: Int
        val pair = checkLines()
        total = pair.first
        winner = pair.second

        if (!winner) {
            winner = checkColumns()
        }

        return Pair(winner, total)
    }

    private fun checkColumns(): Boolean {
        var winner = false
        val verticalIndexes = lines[0].indices
        val horizontalIndexes = lines.indices

        for (i in verticalIndexes) {
            var lineCheck = true
            for (j in horizontalIndexes) {
                lineCheck = lineCheck and lines[j][i].second
            }
            if (lineCheck) {
                winner = true
            }
        }
        return winner
    }

    private fun checkLines(): Pair<Int, Boolean> {
        var total = 0
        var winner = false
        for (line in lines) {
            var lineCheck = true
            for (number in line) {
                lineCheck = lineCheck and number.second
                if (!number.second) {
                    total += number.first
                }
            }
            if (lineCheck) {
                winner = true
            }
        }
        return Pair(total, winner)
    }
}

private class Bingo(input: List<String>) {
    private var numbers: List<Int> = input[0].split(",").map { it.toInt() }
    private var cards: MutableList<Card> = mutableListOf()

    init {
        val inputWithoutHeaders = input.drop(2)
        var card = Card()
        for (line in inputWithoutHeaders) {
            if (line.isEmpty()) {
                cards.add(card)
                card = Card()
                continue
            }
            card.addLine(line)
        }
        cards.add(card)
    }

    fun run(): Int {
        for (number in numbers) {
            for (card in cards) {
                for (line in card.lines) {
                    for ((index, value) in line.withIndex()) {
                        if (value.first == number) {
                            line[index] = line[index].copy(value.first, true)
                        }
                    }
                }
                val cardStatus = card.checkWinner()
                if (cardStatus.first) {
                    return cardStatus.second * number
                }
            }
        }
        return -1
    }

    fun longRun(): Int {
            val turns: MutableList<Pair<Int, Int>> = mutableListOf()
            for (card in cards) {
                for ((movements, number) in numbers.withIndex()) {
                    for (line in card.lines) {
                    for ((index, value) in line.withIndex()) {
                        if (value.first == number) {
                            line[index] = line[index].copy(value.first, true)
                        }
                    }
                }
                val cardStatus = card.checkWinner()
                if (cardStatus.first) {
                    turns.add(Pair(movements, cardStatus.second * number))
                    break
                }
            }
        }
        val result = turns.maxByOrNull { pair -> pair.first }
        return result!!.second
    }
}
