package day11

import readFile
import kotlin.math.absoluteValue

data class Galaxy(val i: Int, val j: Int)

fun isGalaxy(c: Char) = c == '#'
fun isEmptySpace(c: Char) = c == '.'

fun isColumnEmpty(input: List<String>, j: Int): Boolean = input.all { isEmptySpace(it[j]) }

fun isRowEmpty(input: List<String>, i: Int): Boolean {
    for (j in input.indices) {
        if (!isEmptySpace(input[i][j])) return false
    }

    return true
}


fun parseInput(input: String, emptySpaceMultiplier: Int): List<Galaxy> {
    val galaxies = mutableListOf<Galaxy>()

    val lines = input.lines()

    var rowOffset = 0
    for ((i, line) in lines.withIndex()) {
        var colOffset = 0

        if (isRowEmpty(lines, i)) {
            rowOffset += emptySpaceMultiplier - 1
        }

        for (j in line.indices) {
            if (isColumnEmpty(lines, j)) {
                colOffset += emptySpaceMultiplier - 1
            }
            if (isGalaxy(line[j])) {
                galaxies.add(Galaxy(rowOffset, colOffset))
            }

            colOffset++
        }

        rowOffset++
    }

    return galaxies
}

fun calculateGalaxyDistance(galaxy1: Galaxy, galaxy2: Galaxy): Long =
    ((galaxy2.i - galaxy1.i).absoluteValue + (galaxy2.j - galaxy1.j).absoluteValue).toLong()

fun calculateGalaxiesDistanceSum(galaxies: List<Galaxy>): Long {
    val distances = mutableListOf<Long>()

    for (i in galaxies.indices) {
        for (j in i + 1..<galaxies.size) {
            distances.add(calculateGalaxyDistance(galaxies[i], galaxies[j]))
        }
    }

    return distances.sum()
}

fun solve1(input: String): Long = calculateGalaxiesDistanceSum(parseInput(input, 2))
fun solve2(input: String): Long = calculateGalaxiesDistanceSum(parseInput(input, 1000 * 1000))

fun main() {
    val input = readFile("src\\main\\kotlin\\day11\\input.txt")

    println(solve1(input))
    println(solve2(input))
}