package day9

import prepend
import readFile

typealias History = List<Int>
typealias OASISReport = List<History>

fun parseInput(input: String): OASISReport = input.lines().map { line -> line.split(' ').map { it.toInt() } }

fun predictNextHistoryRow(history: History): MutableList<Int> {
    val newStack = mutableListOf<Int>()

    for (i in 0..<(history.size - 1)) {
        newStack.add(history[i + 1] - history[i])
    }

    return newStack
}

fun createHistoryStack(history: History): MutableList<MutableList<Int>> {
    val stack = mutableListOf(history.toMutableList())

    while (!stack.last().all { it == 0 }) {
        stack.add(predictNextHistoryRow(stack.last()))
    }

    return stack
}

fun predictLastHistoryValue(history: History): Int {
    val stack = createHistoryStack(history)

    stack.last().add(0)
    for (i in (stack.size - 2) downTo 0) {
        stack[i].add(stack[i + 1].last() + stack[i].last())
    }

    return stack.first().last()
}

fun predictFirstHistoryValue(history: History): Int {
    val stack = createHistoryStack(history)

    stack.last().add(0, 0)
    for (i in (stack.size - 2) downTo 0) {
        stack[i].prepend(stack[i].first() - stack[i + 1].first())
    }

    return stack.first().first()
}

fun solve1(report: OASISReport): Int = report.sumOf { predictLastHistoryValue(it) }
fun solve2(report: OASISReport): Int = report.sumOf { predictFirstHistoryValue(it) }

fun main() {
    val inputString = readFile("src\\main\\kotlin\\day9\\input.txt")
    val input = parseInput(inputString)

    println(solve1(input))
    println(solve2(input))
}