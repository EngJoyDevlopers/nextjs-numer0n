package com.engjoy.numer0nServer.domain.cards

import java.lang.IllegalArgumentException


class Cards internal constructor(
    val rule: CardRule,
    val numbers: List<Int>
) {
    override fun toString(): String {
        return "[${numbers.joinToString(", ") { n -> n.toString() }}]"
    }

    fun asked(prediction: List<Int>): AskResult {
        if (prediction.size != numbers.size) {
            throw IllegalArgumentException("Length mismatch between cards and prediction. cards=$numbers.size, pred=$prediction.size")
        }
        val dedup = prediction.toSet()
        if (dedup.size != prediction.size) {
            val dups = dedup.filter { n ->
                prediction.count { m -> n == m } != 1
            }
            throw IllegalArgumentException("Prediction contains duplicated numbers: $dups")
        }

        var eat = 0
        var bite = 0
        for (idx in prediction.indices) {
            val pred = prediction[idx]
            if (pred == numbers[idx]) {
                eat += 1
            } else if (numbers.contains(pred)) {
                bite += 1
            }
        }
        return AskResult(eat, bite)
    }
}
