package com.engjoy.numer0nServer.core

import java.util.UUID

interface GenUuid {
    fun next(): UUID
}

class GenUuidRandom : GenUuid {
    override fun next(): UUID {
        return UUID.randomUUID()
    }
}

class GenUuidSequential : GenUuid {
    private var _num: Long = 0
    val num: Long
        get() { return _num }

    override fun next(): UUID {
        val res = UUID(0, _num)
        _num += 1
        return res
    }
}