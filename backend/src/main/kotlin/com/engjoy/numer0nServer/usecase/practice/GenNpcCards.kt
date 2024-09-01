package com.engjoy.numer0nServer.usecase.practice

import com.engjoy.numer0nServer.domain.cards.Cards

interface GenNpcCards {
    fun genCards(digits: Int): Cards
}
