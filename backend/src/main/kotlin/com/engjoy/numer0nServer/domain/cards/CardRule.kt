package com.engjoy.numer0nServer.domain.cards

interface CardRule {
    fun validate(numbers: List<Int>): Exception?
}

fun <T : CardRule> T.toCards(numbers: List<Int>): Cards {
    validate(numbers)?.let { e -> throw e }

    val dedupNums = numbers.toSet()
    if (dedupNums.size != numbers.size) {
        val dups = dedupNums.filter { n ->
            numbers.count { m -> n == m } != 1
        }
        throw IllegalArgumentException("Duplicated numbers are detected: $dups")
    }
    return Cards(this, numbers)
}
