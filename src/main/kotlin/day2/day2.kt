package day2

import readFile

data class Reach(val color: String, val amount: Int)
data class Game(val id: Int, val reaches: List<List<Reach>>)

fun parseGameReaches(instructions: String): List<List<Reach>> = instructions.split(';').map { reach ->
    reach.split(',').map {
        val a = it.trim().split(' ')
        Reach(a[1], a[0].toInt())
    }
}

fun parseInput(rawInput: String): List<Game> = rawInput.split(System.lineSeparator()).map {
    val s = it.split(':')
    val gameId = s[0].split(' ')[1].toInt()
    Game(gameId, parseGameReaches(s[1]))
}

val availableCubes = mapOf("red" to 12, "green" to 13, "blue" to 14)

fun solve1(games: List<Game>): Int =
    games.filter { cube ->
        cube.reaches.all { reach ->
            reach.all {
                it.amount <= availableCubes[it.color]!!
            }
        }
    }.sumOf { it.id }

fun getGamePower(game: Game): Int {
    val mins = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)

    game.reaches.forEach { reach ->
        reach.forEach {
            if (it.amount > mins[it.color]!!) mins[it.color] = it.amount
        }
    }

    return mins.values.fold(1) { prev, curr -> prev * curr }
}

fun solve2(games: List<Game>): Int =
    games.sumOf { getGamePower(it) }

fun main() {
    val rawInput = readFile("src\\main\\kotlin\\day2\\input.txt")
    val parsed = parseInput(rawInput)

    println(solve1(parsed))
    println(solve2(parsed))
}