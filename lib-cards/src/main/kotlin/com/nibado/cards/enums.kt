package com.nibado.cards

enum class Suit(val string: String) {
    HEARTS("h"),
    SPADES("s"),
    CLUBS("c"),
    DIAMONDS("d");

    companion object {
        fun fromString(suit: String) =
                when {
                    suit.startsWith("h", true) -> HEARTS
                    suit.startsWith("s", true) -> SPADES
                    suit.startsWith("c", true) -> CLUBS
                    suit.startsWith("d", true) -> DIAMONDS
                    else -> throw IllegalArgumentException("Invalid suit: $suit")
                }

    }
}

enum class Rank(val rank: Int) {
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(11),
    QUEEN(12),
    KING(13),
    ACE(14);

    val string: String
        get() = when (rank) {
            in 1..9 -> rank.toString()
            10 -> "T"
            11 -> "J"
            12 -> "Q"
            13 -> "K"
            14 -> "A"
            else -> throw IllegalStateException()
        }

    companion object {
        private val RANK_NUM_REGEX = "[2-9]".toRegex()
        fun fromInt(rank: Int) = Rank.values()[rank - 2]
        fun fromString(rank: String): Rank =
                when {
                    RANK_NUM_REGEX.matches(rank) -> fromInt(rank.toInt())
                    rank == "T" -> TEN
                    rank == "J" -> JACK
                    rank == "Q" -> QUEEN
                    rank == "K" -> KING
                    rank == "A" -> ACE
                    else -> throw IllegalArgumentException("Invalid rank: $rank")
                }

        val faceRanks = listOf(JACK, QUEEN, KING)
    }
}
