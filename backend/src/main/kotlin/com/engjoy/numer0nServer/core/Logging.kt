package com.engjoy.numer0nServer.core

import org.slf4j.Logger
import org.slf4j.LoggerFactory

interface Logging

inline fun <reified T : Logging> T.logger(): Logger {
    val logger = LoggerFactory.getLogger(T::class.java)
        ?: throw Exception("Fail to get logger.")
    return logger
}
