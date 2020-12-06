package com.nibado.cards

data class Card(val rank: Int, val suit: Suit) {
    constructor(rank: Rank, suit: Suit) : this(rank.rank, suit)

    override fun toString(): String = Rank.fromInt(rank).string + suit.string

    companion object {
        fun fromString(card: String) : Card =
                Card(
                        Rank.fromString(card.substring(0 .. 0)),
                        Suit.fromString(card.substring(1 .. 1)))
    }
}
