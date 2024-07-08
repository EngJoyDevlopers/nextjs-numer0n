package com.engjoy.numer0nServer.domain

import kotlin.random.Random

class AskResult(val eat: Int, val bite: Int)

class Cards(private val numbers: List<Int>) {
    val digits: Int
        get() = numbers.size

    init {
        if (numbers.isEmpty() || 9 < numbers.size) {
            throw Exception("Invalid card numbers. Must be 1~0 digits, but actual value has  ${numbers.size} digits.")
        }
        if (numbers.toSet().size != numbers.size) {
            throw Exception("Duplicated numbers are found in given cards, $numbers")
        }
    }

    fun ask(numbers: List<Int>): AskResult {
        if (numbers.size != this.numbers.size) {
            throw Exception("Size mismatch. The number of cards is ${this.numbers.size} but the digit of asked number is ${numbers.size}")
        }
        var eat = 0
        var bite = 0
        for (idx in numbers.indices) {
            val askedValue = numbers[idx]
            if (askedValue ==  this.numbers[idx]) {
                eat += 1
            } else if (askedValue in this.numbers) {
                bite += 1
            }
        }
        return AskResult(eat, bite)
    }

    override fun toString(): String {
        return numbers.joinToString("-") { x -> x.toString() }
    }

    companion object {
        fun createRandomly(numDigits: Int, rng: Random): Cards {
            if (numDigits == 0 || 9 < numDigits) {
                throw Exception("Invalid card numbers. Must be 1~0 digits, but actual value has  $numDigits digits.")
            }
            val numbers = List(numDigits) { _ -> 0 }.toMutableList()
            for (i in numbers.indices) {
                while (true) {
                    val rnd = (rng.nextDouble() * 10).toInt()
                    if (rnd !in numbers.take(i)) {
                        numbers[i] = rnd
                        break
                    }
                }
            }
            return Cards(numbers)
        }
    }
}

