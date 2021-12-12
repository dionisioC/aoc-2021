fun main() {
    fun part1(input: List<String>): Int {
        val size = input[0].length
        val columns: MutableList<Frequency> = emptyList<Frequency>().toMutableList()

        var gamma = ""
        var epsilon = ""

        for (i in 0 until size) {
            columns.add(Frequency(binaryNumbers = input, index = i))
        }

        for (i in 0 until size) {
            val frequent = columns[i]
            gamma += frequent.getMostFrequent()
            epsilon += frequent.getLessFrequent()
        }

        return gamma.toInt(2) * epsilon.toInt(2)
    }

    fun part2(input: List<String>): Int {
        val o2: String
        val co2: String
        val size = input[0].length


        var o2ToFilter = input.toMutableList()
        for (i in 0 until size) {
            val frequency = Frequency(binaryNumbers = o2ToFilter, index = i)

            val toKeep = frequency.getMostFrequentOrOne()
            o2ToFilter = remover(o2ToFilter, toKeep, i)
            if (o2ToFilter.size == 1) break
        }
        o2 = o2ToFilter.single()


        var co2ToFilter = input.toMutableList()
        for (i in 0 until size) {
            val frequency = Frequency(binaryNumbers = co2ToFilter, index = i)

            val toKeep = frequency.getLessFrequentOrZero()
            co2ToFilter = remover(co2ToFilter, toKeep, i)

            if (co2ToFilter.size == 1) break
        }

        co2 = co2ToFilter.single()
        return o2.toInt(2) * co2.toInt(2)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)


    val input = readInput("Day03")
    check(part1(input) == 3009600)
    check(part2(input) == 6940518)
    println(part1(input))
    println(part2(input))
}

private fun remover (input: MutableList<String>, filter: Char, index: Int): MutableList<String> {
    val toRemove = HashSet<String>()
    for (binaryO2ToFilter in input) {
        if (binaryO2ToFilter[index] != filter) {
            toRemove.add(binaryO2ToFilter)
        }
    }
    input.removeAll(toRemove)
    return input
}

private data class Frequency(var one: Int = 0, var zero: Int = 0, val binaryNumbers: List<String>, val index: Int) {

    init {
        for (binaryNumber in binaryNumbers) {
                addBit(binaryNumber[index])
        }
    }

    fun addBit(bit: Char) {
        if (bit == '1') {
            one++
        } else {
            zero++
        }
    }

    fun getMostFrequent(): String {
        return if (one > zero) {
            "1"
        } else {
            "0"
        }
    }

    fun getLessFrequent(): String {
        return if (one > zero) {
            "0"
        } else {
            "1"
        }
    }

    fun getMostFrequentOrOne(): Char {
        return if (one > zero || one == zero) {
            '1'
        } else {
            '0'
        }
    }

    fun getLessFrequentOrZero(): Char {
        return if (one > zero || one == zero) {
            '0'
        } else {
            '1'
        }
    }

}