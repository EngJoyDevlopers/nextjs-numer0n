package com.engjoy.numer0nServer.domain.cards

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

private class ClampRuleTest {
    //
    // helpers
    //
    fun String.toIntPair(): Pair<Int, Int> {
        val ints = this.split(",")
            .map { elem -> elem.toInt() }

        return Pair(ints[0], ints[1])
    }

    //
    // tests
    //
    @ParameterizedTest
    @ValueSource(
        strings = [
            "0,-1",
            "-1,-3",
            "1,0",
            "42,-42"
        ]
    )
    fun `constructor(err)#max is smaller than min`(s: String) {
        val minmax = s.toIntPair()

        assertFailsWith<IllegalArgumentException>(
            block = {
                ClampRule(minmax.first, minmax.second)
            }
        )
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "-42,-42",
            "-42,-41",
            "-1,-1",
            "-2,-1",
            "-1,0",
            "0,0",
            "0,1",
            "1,1",
            "1,2",
            "42,42",
            "42,43",
            "42,100",
            "-1,3",
            "-42,42"
        ]
    )
    fun `constructor(ok)`(s: String) {
        val minmax = s.toIntPair()

        val tested = ClampRule(minmax.first, minmax.second)

        assertEquals(tested.min, minmax.first)
        assertEquals(tested.max, minmax.second)
    }

    @ParameterizedTest
    @ValueSource(
        ints = [
            -10,
            0,
            5,
            9
        ]
    )
    fun `validate(err)#value is smaller than min`(min: Int) {
        val rule = ClampRule(10, 30)
        val nums = (0..<10)
            .map { i -> min + 2 * i }
            .shuffled()

        val tested = rule.validate(nums)

        assert(tested is IllegalArgumentException)
    }

    @ParameterizedTest
    @ValueSource(
        ints = [
            31,
            32,
            35,
            42
        ]
    )
    fun `validate(err)#value is greater than max`(max: Int) {
        val rule = ClampRule(10, 30)
        val nums = (0..9)
            .map { i -> max - 2 * i }
            .shuffled()

        val tested = rule.validate(nums)

        assert(tested is IllegalArgumentException)
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "10,0",
            "10,1",
            "10,10",
            "15,1",
            "20,5",
            "30,1"
        ]
    )
    fun `validate(ok)`(s: String) {
        val minnum = s.toIntPair()
        val rule = ClampRule(10, 30)
        val nums = (0..<minnum.second)
            .map { i -> minnum.first + 2 * i }
            .shuffled()

        val tested = rule.validate(nums)

        assertEquals(tested, null)
    }
}