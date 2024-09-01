package com.engjoy.numer0nServer.core

import java.time.ZonedDateTime

interface Clock {
    fun now(): ZonedDateTime
}

class SystemClock : Clock {
    override fun now(): ZonedDateTime {
        return ZonedDateTime.now()
    }
}

class FlozenClock(var value: ZonedDateTime) : Clock {
    override fun now(): ZonedDateTime {
        return value
    }
}
