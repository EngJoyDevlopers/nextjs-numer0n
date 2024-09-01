package com.engjoy.numer0nServer.config

import com.engjoy.numer0nServer.core.Clock
import com.engjoy.numer0nServer.core.SystemClock
import kotlin.random.Random
import com.engjoy.numer0nServer.usecase.practice.GenNpcCards
import com.engjoy.numer0nServer.usecase.practice.GenNpcCardsRnd
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.context.annotation.Bean

@Configuration
@Profile("dev")
class Dev : ConfigBase {
    @Bean
    override fun npcGenerator(): GenNpcCards {
        return GenNpcCardsRnd(Random(0), 0, 9)
    }

    @Bean
    override fun clock(): Clock {
        return SystemClock()
    }
}
