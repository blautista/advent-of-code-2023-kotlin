package day1

import readFile

fun parseInput(rawInput: String) = rawInput.split('\n')

fun getDigit(row: String): Int {
    val onlyDigits = row.replace("[^0-9]".toRegex(), "")

    val firstDigit = onlyDigits.first()
    val lastDigit = onlyDigits.last()

    return "$firstDigit$lastDigit".toInt()
}

fun solve1(rows: List<String>): Int = rows
    .map { getDigit(it) }
    .fold(0) { prev, curr -> prev + curr }

val numbersMap = mapOf(
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9
)

fun getFirstDigit(str: String): Int {
    var temp = ""

    for (c in str) {
        if (c.isDigit()) {
            return c.digitToInt()
        }

        temp += c;

        for (entry in numbersMap) {
            if (temp.contains(entry.key)) {
                return entry.value;
            }
        }
    }

    throw Exception("Error parsing row $str")
}

fun getLastDigit(str: String): Int {
    var temp = ""

    for (i in str.length - 1 downTo 0) {
        val c = str[i]

        if (c.isDigit()) {
            return c.digitToInt()
        }

        temp = c + temp;

        for (entry in numbersMap) {
            if (temp.contains(entry.key)) {
                return entry.value;
            }
        }
    }

    throw Exception("Error parsing row $str")
}

fun solve2(rows: List<String>): Int = rows
    .map { "${getFirstDigit(it)}${getLastDigit(it)}".toInt() }
    .fold(0) { prev, curr -> prev + curr }


fun main() {
    val inputString = readFile("src\\main\\kotlin\\day1\\input.txt")

    val parsedInput = parseInput(inputString)

    println(solve1(parsedInput))
    println(solve2(parsedInput))
}