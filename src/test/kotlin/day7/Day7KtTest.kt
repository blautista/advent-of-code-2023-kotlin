package day7

import kotlin.test.*

class Day7KtTest {

    @Test
    fun getHandScore() {
        assertEquals(HandScores.PAIR, getHandScore("32T3K"))
        assertEquals(HandScores.THREE_OF_A_KIND, getHandScore("T55J5"))
        assertEquals(HandScores.TWO_PAIR, getHandScore("KK677"))
        assertEquals(HandScores.TWO_PAIR, getHandScore("KTJJT"))
        assertEquals(HandScores.THREE_OF_A_KIND, getHandScore("QQQJA"))
        assertEquals(HandScores.FOUR_OF_A_KIND, getHandScore("QQQQA"))
        assertEquals(HandScores.FIVE_OF_A_KIND, getHandScore("QQQQQ"))
        assertEquals(HandScores.FULL_HOUSE, getHandScore("QQQAA"))
    }

    @Test
    fun getHandScoreWithJokers() {
        assertEquals(HandScores.PAIR, getHandScoreWithJokers("32T3K"))
        assertEquals(HandScores.FOUR_OF_A_KIND, getHandScoreWithJokers("T55J5"))
        assertEquals(HandScores.TWO_PAIR, getHandScoreWithJokers("KK677"))
        assertEquals(HandScores.FOUR_OF_A_KIND, getHandScoreWithJokers("KTJJT"))
        assertEquals(HandScores.FOUR_OF_A_KIND, getHandScoreWithJokers("QQQJA"))
    }
}