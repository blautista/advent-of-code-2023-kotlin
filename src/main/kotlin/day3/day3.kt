package day3

import readFile

data class EngineNumber(var num: Int, val x: Int, val y: Int)
data class EngineSymbol(var c: Char, val x: Int, val y: Int)
data class Engine(val numbers: List<EngineNumber>, val symbols: List<EngineSymbol>)

// this is pretty terrible
fun parseInput(input: String): Engine {
    val lines = input.split(System.lineSeparator())
    val symbols = mutableListOf<EngineSymbol>()
    val numbers = mutableListOf<EngineNumber>()

    for ((y, line) in lines.withIndex()) {
        var currentParsingNumber: EngineNumber? = null

        for ((x, c) in line.withIndex()) {
            if (c.isDigit()) {
                if (currentParsingNumber == null) {
                    currentParsingNumber = EngineNumber(c.digitToInt(), x, y)
                } else {
                    currentParsingNumber.num = "${currentParsingNumber.num}$c".toInt()
                }
            } else if (c == '.') {
                if (currentParsingNumber != null) {
                    numbers.add(currentParsingNumber)
                }
                currentParsingNumber = null
            } else {
                if (currentParsingNumber != null) {
                    numbers.add(currentParsingNumber)
                }
                symbols.add(EngineSymbol(c, x, y))
                currentParsingNumber = null
            }
        }

        if (currentParsingNumber != null) {
            numbers.add(currentParsingNumber)
        }
    }

    return Engine(numbers, symbols.toList())
}

fun getNumberCells(num: EngineNumber): List<Pair<Int, Int>> {
    return num.num.toString().mapIndexed { i, _ ->
        Pair(num.x + i, num.y)
    }
}

fun getCellsAroundSymbol(symbol: EngineSymbol): MutableList<Pair<Int, Int>> {
    val list = mutableListOf<Pair<Int, Int>>()

    for (x in -1..1) {
        for (y in -1..1) {
            if (x == 0 && y == 0) continue
            list.add(Pair(symbol.x + x, symbol.y + y))
        }
    }
    return list
}

fun solve1(engine: Engine): Int = engine.numbers.filter { number ->
    engine.symbols.any { symbol ->
        getCellsAroundSymbol(symbol).any { getNumberCells(number).contains(it) }
    }
}.sumOf { it.num }

fun isGear(symbol: EngineSymbol) = symbol.c == '*'

fun getGearRatio(gear: EngineSymbol, numbers: List<EngineNumber>): Int {
    val cellsAroundSymbol = getCellsAroundSymbol(gear)

    val adjacentNumbers = numbers.filter { number ->
        cellsAroundSymbol.any { getNumberCells(number).contains(it) }
    }

    if (adjacentNumbers.size != 2) return 0

    return adjacentNumbers.fold(1) { prev, curr -> prev * curr.num }
}

fun solve2(engine: Engine): Int =
    engine.symbols.filter { isGear(it) }.sumOf { getGearRatio(it, engine.numbers) }

fun main() {
    val rawInput = readFile("src\\main\\kotlin\\day3\\input.txt")
    val engine = parseInput(rawInput)

    println(solve1(engine))
    println(solve2(engine))
}