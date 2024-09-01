package com.engjoy.numer0nServer.usecase.practice

import com.engjoy.numer0nServer.domain.cards.Cards

class GenNpcCardsInMemory(private val _data: HashMap<Int, List<Cards>>) : GenNpcCards {
    private var _cursor = HashMap<Int, Int>()

    init {
        val empties = _data
            .filter { kv -> kv.value.isEmpty() }
            .map { kv -> kv.key }
            .toSet()
        for (key in empties) {
            _data.remove(key)
        }
        _data.keys.associateWithTo(_cursor) { _ -> 0 }
    }

    override fun genCards(digits: Int): Cards {
        val idx = _cursor[digits]
        val lst = _data[digits]
        val res = idx?.let { i -> lst?.getOrNull(i) }
        if (idx == null || lst == null || res == null) {
            throw UnsupportedOperationException("Data is not found for $digits")
        }
        _cursor[digits] = (idx + 1) % lst.size
        return res
    }
}