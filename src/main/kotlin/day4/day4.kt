package day4

import readFile
import kotlin.math.pow

data class Card(val id: Int, val winningNumbers: List<Int>, val numbers: Set<Int>)
typealias Cards = List<Card>

fun parseInput(input: String): Cards =
    input.split(System.lineSeparator()).map { card ->
        val idNumbersSplit = card.split(':')
        val cardId = idNumbersSplit[0].split("Card")[1].trim().toInt()
        val numbersSplit = idNumbersSplit[1].split(" | ").map {
            it.trim().replace("  ", " ").split(' ').map { it.toInt() }
        }
        Card(cardId, numbersSplit[0], numbersSplit[1].toSet())
    }

fun calculateCardMatches(card: Card): Int = card.winningNumbers.filter { card.numbers.contains(it) }.size

fun calculateCardScore(card: Card): Int {
    val nCardMatches = calculateCardMatches(card)

    if (nCardMatches == 0) {
        return 0
    }

    return 2f.pow(nCardMatches - 1).toInt()
}

fun solve1(cards: Cards): Int =
    cards.sumOf { calculateCardScore(it) }

fun solve2(cards: Cards): Int {
    val cardAmounts = mutableMapOf(*cards.map { Pair(it.id, 1) }.toTypedArray())

    for (card in cards) {
        val matches = calculateCardMatches(card)

        for (i in 1..matches) {
            val relativeCard = cardAmounts[i + card.id]
            if (relativeCard != null) {
                cardAmounts[i + card.id] = relativeCard + cardAmounts[card.id]!!
            }
        }
    }

    return cardAmounts.values.sum()
}

fun main() {
    val rawInput = readFile("src\\main\\kotlin\\day4\\input.txt")
    val input = parseInput(rawInput)

    println(solve1(input))
    println(solve2(input))
}