package com.nibado.cards.game.blackjack

import com.nibado.cards.Deck
import com.nibado.cards.draw
import kotlin.math.round

class BlackjackSim(val holdAt: Int) {
    fun run() {
        val start = System.currentTimeMillis()
        var count = 0
        var wins = 0
        var ties = 0

        while(System.currentTimeMillis() - start < 5000) {
            count++
            when(runOnce()) {
                1 -> wins++
                0 -> ties++
            }
        }

        val winRate = round(wins.toDouble() / count.toDouble() * 100.0)
        val tieRate = round(ties.toDouble() / count.toDouble() * 100.0)

        println("$wins ($winRate%) wins and $ties ($tieRate%) ties out of $count")
    }

    private fun runOnce() : Int {
        val deck = Deck.build(4)

        val playerHand = BlackjackHand()
        val dealerHand = BlackjackHand()

        playerHand += deck.draw()
        dealerHand += deck.draw()
        playerHand += deck.draw()
        dealerHand += deck.draw()

        while(playerHand.score() < holdAt) {
            playerHand += deck.draw()
        }

        if(playerHand.isBust()) {
            return -1
        }

        while(dealerHand.score() < 16 && dealerHand.score() < playerHand.score()) {
            dealerHand += deck.draw()
        }

        return playerHand.compareTo(dealerHand)
    }
}

fun main() {
    (14 .. 19).forEach {
        print("$it: ")
        BlackjackSim(it).run()
    }

}
