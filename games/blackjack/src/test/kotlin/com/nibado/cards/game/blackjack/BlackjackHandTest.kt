package com.nibado.cards.game.blackjack

import com.nibado.cards.Card
import com.nibado.cards.Hand
import com.nibado.cards.Rank
import com.nibado.cards.Suit
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

internal class BlackjackHandTest {

    @Test
    fun `An Ace with Jack, Queen or Kind should be blackjack`() {
        val aces = Suit.values().map { suit -> Card(Rank.ACE, suit) }
        val faceCards = Suit.values().flatMap { suit -> Rank.faceRanks.map { rank -> Card(rank, suit) } }

        for(ace in aces) {
            for(rank in faceCards) {
                val hand = BlackjackHand(ace, rank)
                assertThat(hand.isBlackJack()).isTrue()
                assertThat(hand.score()).isEqualTo(21)
            }
        }
    }

    @TestFactory
    fun `Should calculate scores correctly`() : Collection<DynamicTest> =
        listOf(
                "Ac,Kc" to 21,
                "Ac,As,Kc" to 12,
                "8c,Ac,As,Kc" to 20,
                HBJ to 21,
                H4 to 4,
                H21 to 21,
                H20 to 20,
                H25 to 25
        )

       .map { (hand, score) ->
            DynamicTest.dynamicTest("$hand should score $score") {
                assertThat(hand.toHand().score()).isEqualTo(score)
            }
        }

    @TestFactory
    fun `Should calculate busts correctly`() : Collection<DynamicTest> =
            listOf(
                    "Ac,Kc,Ks,2s",
                    "Ac,As,Kc,Ks",
                    "8c,Ac,As,Kc,2s",
                    H25
            )

                    .map { hand ->
                        DynamicTest.dynamicTest("$hand should bust") {
                            assertThat(hand.toHand().isBust()).isTrue()
                        }
                    }

    @TestFactory
    fun `Should compare correctly`() : Collection<DynamicTest> =
            listOf(
                    //Win, Loss
                    HBJ to H25,
                    H20 to H25,
                    H4 to H25,

                    HBJ to H21,
                    HBJ to H20,
                    HBJ to H4,

                    H21 to H20,
                    H20 to H4
            )

                    .map { (win, loss) ->
                        DynamicTest.dynamicTest("$win should win from $loss") {
                            val winHand = win.toHand()
                            val lossHand = loss.toHand()

                            assertThat(winHand).isGreaterThan(lossHand)
                            assertThat(lossHand).isLessThan(winHand)
                        }
                    }

    @TestFactory
    fun `Should compare ties correctly`() : Collection<DynamicTest> =
            listOf(HBJ, H21, H20, H4, H25)

                    .map { hand ->
                        DynamicTest.dynamicTest("$hand should tie with itself") {
                           val hand = hand.toHand()

                            assertThat(hand.compareTo(hand)).isZero()
                        }
                    }

    companion object {
        const val HBJ = "Ac,Kc"
        const val H21 = "Kc,9c,2c"
        const val H20 = "Kc,Kh"
        const val H4 = "2c,2h"
        const val H25 = "Kc,Kh,5c"
    }

    private fun String.toHand() = BlackjackHand(Hand.of(this))
}
