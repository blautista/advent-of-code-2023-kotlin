package day7

import readFile

const val JOKER = 'J'
val CARD_VALUES = "23456789TJQKA".toSet()
val CARD_VALUES_WITH_JOKER = "J23456789TQKA".toSet()

object HandScores {
    const val HIGH_CARD = 1
    const val PAIR = 2
    const val TWO_PAIR = 3
    const val THREE_OF_A_KIND = 4
    const val FULL_HOUSE = 5
    const val FOUR_OF_A_KIND = 6
    const val FIVE_OF_A_KIND = 7
}

fun parseInput(input: String) = input.lines().map { line ->
    line.split(' ').let { (cards, bid) -> Hand(cards, bid.toInt()) }
}

fun getHandMap(cards: String): Map<Char, Int> =
    cards.associateBy({ it }, { c -> cards.count { it == c } })

fun getHandScore(cards: String): Int = getHandMap(cards).let { map ->
    when {
        map.containsValue(5) -> HandScores.FIVE_OF_A_KIND
        map.containsValue(4) -> HandScores.FOUR_OF_A_KIND
        map.containsValue(3) && map.containsValue(2) -> HandScores.FULL_HOUSE
        map.containsValue(3) -> HandScores.THREE_OF_A_KIND
        map.filterValues { it > 1 }.size == 2 -> HandScores.TWO_PAIR
        map.containsValue(2) -> HandScores.PAIR
        else -> HandScores.HIGH_CARD
    }
}

fun convertJokers(cards: String): String {
    val map = getHandMap(cards)

    val biggestEntry = map.maxByOrNull { if (it.key != JOKER) it.value else 0 } ?: return "KKKKK"

    return cards.replace(JOKER, biggestEntry.key)
}

fun getHandScoreWithJokers(cards: String): Int = getHandScore(convertJokers(cards))

fun getCardValue(card: Char) = CARD_VALUES.indexOf(card)
fun getCardValueWithJokers(card: Char) = CARD_VALUES_WITH_JOKER.indexOf(card)

data class Hand(private val cards: String, val bid: Int) {
    fun strongerThan(opposingHand: Hand, useJokers: Boolean): Boolean {
        val scoreGetter = if (useJokers) ::getHandScoreWithJokers else ::getHandScore
        val cardValueGetter = if (useJokers) ::getCardValueWithJokers else ::getCardValue

        val score = scoreGetter(this.cards)
        val opposingScore = scoreGetter(opposingHand.cards)

        if (score > opposingScore) {
            return true
        }

        if (score < opposingScore) {
            return false
        }

        for (i in 0..4) {
            val cardValue = cardValueGetter(this.cards[i])
            val opposingCardValue = cardValueGetter(opposingHand.cards[i])

            if (cardValue > opposingCardValue) {
                return true
            } else if (cardValue < opposingCardValue) {
                return false
            }
        }

        throw Exception("Two hands equal!! mamma mia!!")
    }
}

fun solve(hands: List<Hand>, useJokers: Boolean) =
    hands.sortedWith { hand1, hand2 -> if (hand1.strongerThan(hand2, useJokers)) 1 else -1 }
        .mapIndexed { i, hand -> hand.bid * (i + 1) }.sum()

fun solve1(hands: List<Hand>): Int = solve(hands, useJokers = false)

fun solve2(hands: List<Hand>): Int = solve(hands, useJokers = true)

fun main() {
    val inputString = readFile("src\\main\\kotlin\\day7\\input.txt")
    val input = parseInput(inputString)
    
    println(solve1(input))
    println(solve2(input))
}