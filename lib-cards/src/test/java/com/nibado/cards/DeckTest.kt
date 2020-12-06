package com.nibado.cards

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class DeckTest {
    private val deckSize = Suit.values().size * Rank.values().size

    @Test
    fun `A standard deck should contain 52 cards in 4 suits`() {
        val deck = Deck.build()

        assertThat(deck).hasSize(deckSize)

        Suit.values().forEach { suit ->
            assertThat(deck.filter { it.suit == suit }).hasSize(deckSize / 4)
        }

        val hand = mutableSetOf<Card>()

        for(i in 1 .. deckSize) {
            hand += deck.draw()
        }

        assertThat(deck.isEmpty())
        assertThat(hand).hasSize(deckSize)
    }

    @Test
    fun `A deck from 4 packs should contain 224 cards`() {
        val deck = Deck.build(4)

        assertThat(deck).hasSize(deckSize * 4)
        assertThat(deck.toSet()).hasSize(deckSize)
    }

    @Test
    fun `A standard deck should contain all cards`() {
        val cards = Rank.values().flatMap { rank -> Suit.values().map { suit -> rank.string + suit.string } }
                .map(Card::fromString)

        val deck = Deck.build()

        cards.forEach { card ->
            assertThat(deck.contains(card)).isTrue()
        }
    }
}
