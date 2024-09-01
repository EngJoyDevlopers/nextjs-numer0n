package com.engjoy.numer0nServer.domain.cards

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

private class CardRuleTest {
    //
    // helpers
    //
    fun String.toIntList(): List<Int> {
        return if (this.isEmpty()) {
            listOf()
        } else {
            this.split(",")
                .map { elem -> elem.toInt() }
                .toList()
        }
    }

    class MockException : Exception("Mock")

    class MockRule(private val raise: Boolean) : CardRule {
        override fun validate(numbers: List<Int>): Exception? {
            return if (raise) {
                MockException()
            } else {
                null
            }
        }
    }

    //
    // tests
    //
    @ParameterizedTest
    @ValueSource(
        strings = [
            "",
            "1",
            "1,2",
            "0,-500,3",
            "1,2,3,-4,5,6,-7,8,9,10,11"
        ]
    )
    fun `toCards(err)#propagate error`(s: String) {
        val rule = MockRule(true)
        val numbers = s.toIntList()

        assertFailsWith<MockException>(
            block = {
                rule.toCards(numbers)
            }
        )
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "1,1",
            "0,-500,0",
            "1,2,3,-4,5,6,-7,8,9,-7,11"
        ]
    )
    fun `toCards(err)#duplicated numbers`(s: String) {
        val rule = MockRule(false)
        val numbers = s.toIntList()

        assertFailsWith<IllegalArgumentException>(
            block = {
                rule.toCards(numbers)
            }
        )
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "",
            "1",
            "0,-500",
            "1,2,3,-4,5,6,-7,8,9,7,11",
            "0,1,2,3,4,5,6,7,8,9,10"
        ]
    )
    fun `toCards(ok)`(s: String) {
        val rule = MockRule(false)
        val numbers = s.toIntList()

        val cards = rule.toCards(numbers)

        assertEquals(numbers, cards.numbers)
        assertEquals(rule, cards.rule)
    }
}