package day4

import readFile
import kotlin.math.pow

data class Card(val id: Int, val winningNumbers: List<Int>, val numbers: List<Int>)
typealias Cards = List<Card>

fun parseInput(input: String): Cards =
    input.split(System.lineSeparator()).map { card ->
        val idNumbersSplit = card.split(':')
        val cardId = idNumbersSplit[0].split("Card")[1].trim().toInt()
        val numbersSplit = idNumbersSplit[1].split(" | ").map {
            it.trim().replace("  ", " ").split(' ').map { it.toInt() }
        }
        Card(cardId, numbersSplit[0], numbersSplit[1])
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

fun solve2(origCards: Cards): Int {
    val cards = origCards.toMutableList()
    val cardsIterator = cards.listIterator()

    while (cardsIterator.hasNext()) {
        val card = cardsIterator.next()
        val cardMatches = calculateCardMatches(card)
        for (j in 1..cardMatches) {
            val stepCard = origCards.find { it.id == card.id + j }
            if (stepCard != null) {
                cardsIterator.add(stepCard)
                cardsIterator.previous()
            }
        }
    }

    return cards.size
}

fun main() {
    val rawInput = readFile("src\\main\\kotlin\\day4\\input.txt")
    val input = parseInput(rawInput)

    println(solve1(input))
    println(solve2(input))
}