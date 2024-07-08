package com.engjoy.numer0nServer.util

import org.slf4j.LoggerFactory

interface Logging

inline fun <reified T: Logging> T.logger() = LoggerFactory.getLogger(T::class.java)

