package com.engjoy.numer0nServer.domain.cards


class ClampRule(val min: Int, val max: Int) : CardRule {
    init {
        if (max < min) {
            throw IllegalArgumentException("Invalid clamp rule: min=$min, max=$max")
        }
    }

    override fun validate(numbers: List<Int>): Exception? {
        val underMin = numbers.firstOrNull { n -> n < min }
        if (underMin != null) {
            return IllegalArgumentException("$underMin is smaller than $min")
        }
        val overMax = numbers.firstOrNull { n -> max < n }
        if (overMax != null) {
            return IllegalArgumentException("$overMax is greater than $max")
        }
        return null
    }
}