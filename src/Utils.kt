import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

fun readInputAsInt(name: String) = readInput(name).map { it.toInt() }

fun readInputAsStringIntPair(name: String) = readInput(name).map { it.split(" ") }.map { Pair(it[0], it[1].toInt()) }

fun readInputCoordinates(name: String) = readInput(name).map { it.split(" -> ") }.map {
    val firstPair = it[0].split(",")
    val secondPair = it[1].split(",")
    Pair(Pair(firstPair[0].toInt(),firstPair[1].toInt()), Pair(secondPair[0].toInt(),secondPair[1].toInt()))
}

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)
