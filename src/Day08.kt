import java.lang.Exception
import kotlin.system.exitProcess

fun main() {
    fun part1(input: List<String>): Int {
        var result = 0
        for (digit in input) {
            val outputValue = digit.split("|")[1].trim()
            val outputValueDigits = outputValue.split(" ")
            for (outputValueDigit in outputValueDigits) {
                if (Digit(outputValueDigit).getDigit() != "UNKNOWN") {
                    result++
                }
            }
        }
        return result
    }

    fun part2(input: List<String>): Int {
        var result = 0
        for (digit in input) {
            val outputValue = digit.split("|")[1].trim()
            val uniqueSignalPatterns = digit.split("|")[0].trim()
            val display = Display(uniqueSignalPatterns)
            val outputValueDigits = outputValue.split(" ")
            var lineResult = ""
            for (outputValueDigit in outputValueDigits) {
                lineResult += display.getDigit(outputValueDigit)
            }
            result += lineResult.toInt()
        }
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("Day08")
    check(part1(input) == 349)
    check(part2(input) == 1070957)
    println(part1(input))
    println(part2(input))


}

class Digit(sequence: String) {
    private val differentChars: Int = sequence.trim().lowercase().chars().distinct().count().toInt()

    fun getDigit(): String {
        return when (differentChars) {
            2 -> "1"
            4 -> "4"
            3 -> "7"
            7 -> "8"
            else -> "UNKNOWN"
        }
    }
}

class Display(uniqueSignalPatterns: String) {

    private val digitMap: MutableMap<String, String> = mutableMapOf()

    init {
        val signalPatterns = uniqueSignalPatterns.split(" ").toMutableSet()
        getKnownPatterns(signalPatterns)
        getThree(signalPatterns)
        getNine(signalPatterns)
        getTwoAndFive(signalPatterns)
        getZeroAndSix(signalPatterns)
        try {

            check(digitMap.size == 10)
        } catch (e: Exception) {
            println("${digitMap}, $uniqueSignalPatterns")
            exitProcess(1)
        }
    }

    private fun getZeroAndSix(signalPatterns: MutableSet<String>) {
        val five = mutableSetOf<Char>()
        for ((key, value) in digitMap) {
            if (value == "5") {
                five.addAll(key.toCharArray().toSet())
            }
        }

        var zero = ""
        for (signalPattern in signalPatterns) {
            if (signalPattern.length == 6 && signalPattern.toCharArray().toSet().subtract(five).size == 2) {
                digitMap[signalPattern.toCharArray().sorted().joinToString("")] = "0"
                zero = signalPattern
                break
            }
        }

        signalPatterns.remove(zero)

        var six = ""
        for (signalPattern in signalPatterns) {
            if (signalPattern.length == 6 && signalPattern.toCharArray().toSet().subtract(five).size == 1) {
                digitMap[signalPattern.toCharArray().sorted().joinToString("")] = "6"
                six = signalPattern
                break
            }
        }

        signalPatterns.remove(six)

    }

    private fun getTwoAndFive(signalPatterns: MutableSet<String>) {
        val nine = mutableSetOf<Char>()
        for ((key, value) in digitMap) {
            if (value == "9") {
                nine.addAll(key.toCharArray().toSet())
            }
        }

        var two = ""
        for (signalPattern in signalPatterns) {
            if (signalPattern.length == 5 && nine.subtract(signalPattern.toCharArray().toSet()).size == 2) {
                digitMap[signalPattern.toCharArray().sorted().joinToString("")] = "2"
                two = signalPattern
                break
            }
        }

        signalPatterns.remove(two)

        var five = ""
        for (signalPattern in signalPatterns) {
            if (signalPattern.length == 5 && nine.subtract(signalPattern.toCharArray().toSet()).size == 1) {
                digitMap[signalPattern.toCharArray().sorted().joinToString("")] = "5"
                five = signalPattern
                break
            }
        }

        signalPatterns.remove(five)

    }

    private fun getThree(signalPatterns: MutableSet<String>) {
        val possibleThree = mutableSetOf<Char>()
        for ((key, value) in digitMap) {
            if (value == "1") {
                possibleThree.addAll(key.toCharArray().toSet())
            }
        }

        var three = ""
        for (signalPattern in signalPatterns) {
            if (signalPattern.length == 5 && signalPattern.toCharArray().toSet().subtract(possibleThree).size == 3) {
                digitMap[signalPattern.toCharArray().sorted().joinToString("")] = "3"
                three = signalPattern
                break
            }
        }

        signalPatterns.remove(three)
    }

    private fun getNine(signalPatterns: MutableSet<String>) {
        val possibleNine = mutableSetOf<Char>()
        for ((key, value) in digitMap) {
            if (value == "4" || value == "3") {
                possibleNine.addAll(key.toCharArray().toSet())
            }
        }

        var nine = ""
        for (signalPattern in signalPatterns) {
            if (signalPattern.length == 6 && possibleNine.subtract(signalPattern.toCharArray().toSet()).isEmpty()) {
                digitMap[signalPattern.toCharArray().sorted().joinToString("")] = "9"
                nine = signalPattern
                break
            }
        }

        signalPatterns.remove(nine)
    }

    private fun getKnownPatterns(signalPatterns: MutableSet<String>) {
        val knownPatterns = mutableListOf<String>()
        for (signalPattern in signalPatterns) {
            if (Digit(signalPattern).getDigit() != "UNKNOWN") {
                knownPatterns.add(signalPattern)
                digitMap[signalPattern.toCharArray().sorted().joinToString("")] = Digit(signalPattern).getDigit()
            }
        }

        for (knownPattern in knownPatterns) {
            signalPatterns.remove(knownPattern)
        }
    }

    fun getDigit(sequence: String): String {
        val key = sequence.toCharArray().sorted().joinToString("")
        val result = digitMap.getOrDefault(key, "UNKNOWN")
        return result
    }

    override fun toString(): String {
        return "Display(digitMap=$digitMap)"
    }


}

