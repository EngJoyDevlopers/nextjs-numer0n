package com.engjoy.numer0nServer.usecase.practice

import com.engjoy.numer0nServer.domain.cards.Cards
import com.engjoy.numer0nServer.domain.cards.ClampRule
import com.engjoy.numer0nServer.domain.cards.toCards
import kotlin.random.Random


class GenNpcCardsRnd(
    private val _rng: Random,
    min: Int,
    max: Int
) : GenNpcCards {
    private val _base = ClampRule(min, max)

    override fun genCards(digits: Int): Cards {
        if ((_base.max - _base.min + 1) <= digits) {
            throw Exception("Too large digits. max=${_base.max - _base.min + 1}, given=$digits")
        }
        val res = mutableListOf<Int>()
        while (res.size < digits) {
            val generated = _rng.nextInt(_base.min, _base.max)
            if (!res.contains(generated)) {
                res.addLast(generated)
            }
        }
        return _base.toCards(res)
    }
}