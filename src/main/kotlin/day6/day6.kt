package day6

import readFile
import kotlin.math.*

data class Race(val time: Long, val distance: Long)

fun parseInput(input: String): List<Race> {
    val s = input.lines()
    val times = s[0].split(":")[1].replace(Regex("  +"), " ").trim().split(" ").map { it.toLong() }
    val distances = s[1].split(":")[1].replace(Regex("  +"), " ").trim().split(" ").map { it.toLong() }

    return times.mapIndexed { i, time -> Race(time, distances[i]) }
}

fun parseInputWithoutKerning(input: String): Race {
    val s = input.lines()
    val time = s[0].split(":")[1].replace(" ", "").toLong()
    val distance = s[1].split(":")[1].replace(" ", "").toLong()

    return Race(time, distance)
}

fun quadratic(a: Double, b: Double, c: Double): Pair<Double, Double> {
    val d = sqrt(b.pow(2) - (4 * a * c))
    return Pair((-b + d) / (2 * a), (-b - d) / (2 * a))
}

fun getPossibleRecords(race: Race): Long {
    val (time, distance) = race

    val (min, max) = quadratic((-1).toDouble(), time.toDouble(), -(distance + 1).toDouble())

    return (floor(max) - ceil(min)).toLong() + 1
}

fun solve1(races: List<Race>): Long = races.map {
    getPossibleRecords(it)
}.fold(1) { p, c -> p * c }

fun solve2(race: Race): Long = getPossibleRecords(race)

fun main() {
    val inputString = readFile("src\\main\\kotlin\\day6\\input.txt")

    println(solve1(parseInput(inputString)))
    println(solve2(parseInputWithoutKerning(inputString)))
}