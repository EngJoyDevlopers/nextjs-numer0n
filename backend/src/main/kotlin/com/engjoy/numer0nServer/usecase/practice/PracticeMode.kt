package com.engjoy.numer0nServer.usecase.practice

import com.engjoy.numer0nServer.core.Clock
import com.engjoy.numer0nServer.core.Logging
import com.engjoy.numer0nServer.core.logger
import com.engjoy.numer0nServer.domain.cards.AskResult
import com.engjoy.numer0nServer.domain.cards.Cards
import java.lang.IllegalArgumentException
import java.time.ZonedDateTime
import java.util.UUID

class PracticeMode(
    private val _genNpc: GenNpcCards,
    private val _clock: Clock
) {
    private val _rooms = HashMap<String, PracticeRoom>()

    companion object : Logging {
        val logger = logger()
    }

    fun createRoom(digits: Int): PracticeRoomInfo {
        removeExpired()
        val cards = _genNpc.genCards(digits)
        val room = PracticeRoom(cards, _clock)

        var id = UUID.randomUUID().toString()
        while (_rooms.containsKey(id)) {
            id = UUID.randomUUID().toString()
        }
        _rooms[id] = room

        logger.info("Room is created: id=$id, cards=${room.cards}")
        return PracticeRoomInfo(id, room.expiry)
    }

    fun ask(roomId: String, numbers: List<Int>): AskResult {
        removeExpired()
        val room = _rooms[roomId] ?: throw IllegalArgumentException("Room '$roomId' is not found or already expired.")
        room.updateExpiry()
        val res = room.cards.asked(numbers)
        logger.info(
            "Asked: " +
                "id=$roomId, " +
                "asked=${numbers.joinToString("-") { x -> x.toString() }}, " +
                "ans=${room.cards}, " +
                "eat=${res.eat}, bite=${res.bite}"
        )
        return res
    }

    fun getRoomInfo(roomId: String): PracticeRoomInfo? {
        removeExpired()
        val room = _rooms[roomId] ?: return null
        return PracticeRoomInfo(roomId, room.expiry)
    }

    private fun removeExpired() {
        val now = _clock.now()
        val expired = _rooms.asSequence()
            .filter { kv -> kv.value.expiry < now }
            .map { kv -> kv.key }
            .toList()

        expired.forEach { key -> _rooms.remove(key) }
    }
}


private class PracticeRoom(
    val cards: Cards,
    private val _clock: Clock
) {
    private var _expiry = _clock.now().plusHours(1)
    val expiry: ZonedDateTime
        get() = _expiry

    fun updateExpiry() {
        _expiry = _clock.now().plusHours(1)
    }
}

class PracticeRoomInfo(val id: String, val expirry: ZonedDateTime)
