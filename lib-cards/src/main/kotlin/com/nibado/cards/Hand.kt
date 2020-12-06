package com.nibado.cards

interface Hand {
    val cards: List<Card>

    companion object {
        fun of(hand: String): Hand = object : Hand {
            override val cards = hand.split(",").map { Card.fromString(it) }
        }
    }
}
