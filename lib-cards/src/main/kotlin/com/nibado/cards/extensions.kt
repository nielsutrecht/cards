package com.nibado.cards

fun MutableList<Card>.draw(): Card =
        this.removeAt(0)
