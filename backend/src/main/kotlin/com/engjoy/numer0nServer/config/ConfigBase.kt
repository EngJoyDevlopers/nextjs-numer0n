package com.engjoy.numer0nServer.config

import com.engjoy.numer0nServer.core.Clock
import com.engjoy.numer0nServer.usecase.practice.GenNpcCards

interface ConfigBase {
    fun npcGenerator(): GenNpcCards
    fun clock(): Clock
}
