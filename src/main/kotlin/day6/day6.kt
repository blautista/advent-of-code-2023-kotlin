package day6

import readFile

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

fun calculateTravelledDistance(timeHeld: Long, totalTime: Long): Long {
    return timeHeld * (totalTime - timeHeld)
}

fun bruteForcePossibleRecords(race: Race): Long {
    var records = 0L

    for (i in 0..race.time) {
        val travelledDistance = calculateTravelledDistance(i, race.time)
        if (travelledDistance > race.distance) records++
    }

    return records
}

fun solve1(races: List<Race>): Long = races.map {
    bruteForcePossibleRecords(it)
}.fold(1) { p, c -> p * c }

fun solve2(race: Race): Long = bruteForcePossibleRecords(race)

fun main() {
    val inputString = readFile("src\\main\\kotlin\\day6\\input.txt")

    println(solve1(parseInput(inputString)))
    println(solve2(parseInputWithoutKerning(inputString)))
}