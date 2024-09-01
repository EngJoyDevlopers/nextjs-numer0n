package com.engjoy.numer0nServer.domain.cards

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

private class CardsTest {
    //
    // helpers
    //
    class MockRule : CardRule {
        override fun validate(numbers: List<Int>): Exception? {
            return null
        }
    }

    fun String.toIntList(): List<Int> {
        return if (this.isEmpty()) {
            listOf()
        } else {
            this.split(",")
                .map { elem -> elem.toInt() }
                .toList()
        }
    }
    
    companion object {
        @JvmStatic
        fun getDataForAsk(): List<Arguments> {
            // ans is [-1, 0, 42]
            return listOf(
                Arguments.of(listOf(-4, -3, -2), AskResult(0, 0)),
                Arguments.of(listOf(-3, -2, -1), AskResult(0, 1)),
                Arguments.of(listOf(-1, -2, -3), AskResult(1, 0)),
                Arguments.of(listOf(0, -1, 2), AskResult(0, 2)),
                Arguments.of(listOf(-1, 0, -2), AskResult(2, 0)),
                Arguments.of(listOf(42, 0, 1), AskResult(1, 1)),
                Arguments.of(listOf(42, 0, -1), AskResult(1, 2)),
                Arguments.of(listOf(-1, 0, 42), AskResult(3, 0)),
            )
        }
    }

    //
    // tests
    //
    @Test
    fun `toString(ok)#empty cards`() {
        val cards = MockRule().toCards(listOf())

        val tested = cards.toString()

        assertEquals(tested, "[]")
    }

    @Test
    fun `toString(ok)#non-empty cards`() {
        val cards = MockRule().toCards(listOf(-1, 0, 42))

        val tested = cards.toString()

        assertEquals(tested, "[-1, 0, 42]")
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "",
            "1",
            "1,2",
            "1,2,3,-4"
        ]
    )
    fun `asked(err)#card size mismatch`(s: String) {
        val cards = MockRule().toCards(listOf(-1, 0, 42))
        val prediction = s.toIntList()

        assertFailsWith<IllegalArgumentException> {
            cards.asked(prediction)
        }
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "1,1,1",
            "0,1,0",
            "42,42,0",
            "0,3,3"
        ]
    )
    fun `asked(err)#duplicated numbers in prediction`(s: String) {
        val cards = MockRule().toCards(listOf(-1, 0, 42))
        val prediction = s.toIntList()

        assertFailsWith<IllegalArgumentException> {
            cards.asked(prediction)
        }
    }
    @ParameterizedTest
    @MethodSource("getDataForAsk")
    fun `asked(ok)`(pred: List<Int>, expected: AskResult) {
        val cards = MockRule().toCards(listOf(-1, 0, 42))
        
        val tested = cards.asked(pred)

        assertEquals(tested, expected)
    }
}
