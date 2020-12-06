package com.nibado.cards.game.blackjack

import com.nibado.cards.Card
import com.nibado.cards.Hand
import com.nibado.cards.Rank

class BlackjackHand(override val cards: MutableList<Card>) : Hand, Comparable<BlackjackHand> {

    constructor(vararg cards: Card) : this(cards.toMutableList())
    constructor(vararg cards: String) : this(cards.map { Card.fromString(it) }.toMutableList())
    constructor(hand: Hand) : this(hand.cards.toMutableList())
    constructor() : this(mutableListOf<Card>())

    fun score() : Int {
        val scores = cards.map { cardScore(it) }.toMutableList()
        while(scores.sum() > 21 && scores.any { it == 11 }) {
            scores.remove(11)
            scores += 1
        }

        return scores.sum()
    }

    operator fun plusAssign(card: Card) {
        cards += card
    }

    fun isBust() : Boolean = score() > 21
    fun isBlackJack() : Boolean = cards.size == 2
            && cards.count { it.rank == Rank.ACE.rank } == 1
            && cards.count { it.rank in Rank.JACK.rank .. Rank.KING.rank } == 1

    override fun compareTo(other: BlackjackHand): Int =
            when {
                this.isBust() && other.isBust() -> 0
                this.isBust() -> -1
                other.isBust() -> 1
                this.isBlackJack() && other.isBlackJack() -> 0
                this.isBlackJack() -> 1
                other.isBlackJack() -> -1
                else -> this.score().compareTo(other.score())
            }

    companion object {
        fun cardScore(card: Card) : Int =
            when(card.rank) {
                in 2 .. 10 -> card.rank
                in Rank.JACK.rank .. Rank.KING.rank -> 10
                Rank.ACE.rank -> 11
                else -> throw IllegalArgumentException("Unknown card: $card")
            }
    }
}
