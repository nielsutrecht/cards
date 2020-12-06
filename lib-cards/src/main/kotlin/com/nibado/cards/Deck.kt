package com.nibado.cards

object Deck {
    fun build(decks: Int = 1, shuffled: Boolean = true) : MutableList<Card> {
        val cards =
                (1 .. decks).flatMap {
                    Suit.values().flatMap { suit -> Rank.values().map { rank -> Card(rank, suit) } }
                }.toMutableList()

        if(shuffled) {
            cards.shuffle()
        }

        return cards
    }
}
