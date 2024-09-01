package com.engjoy.numer0nServer.config

import com.engjoy.numer0nServer.core.Clock
import com.engjoy.numer0nServer.core.FlozenClock
import com.engjoy.numer0nServer.domain.cards.ClampRule
import com.engjoy.numer0nServer.domain.cards.toCards
import com.engjoy.numer0nServer.usecase.practice.GenNpcCards
import com.engjoy.numer0nServer.usecase.practice.GenNpcCardsInMemory
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.context.annotation.Bean
import java.time.ZonedDateTime

@Configuration
@Profile("test")
class Test : ConfigBase {
    @Bean
    override fun npcGenerator(): GenNpcCards {
        val rule = ClampRule(0, 9)
        val cards = mapOf(
            3 to listOf(
                rule.toCards(listOf(1, 2, 3)),
                rule.toCards(listOf(4, 5, 6)),
                rule.toCards(listOf(7, 8, 9)),
            ),
            5 to listOf(
                rule.toCards(listOf(1, 2, 3, 4, 5)),
                rule.toCards(listOf(4, 5, 6, 1, 2)),
            ),
        )
        return GenNpcCardsInMemory(cards.toMap(HashMap()))
    }

    @Bean
    override fun clock(): Clock {
        val now = ZonedDateTime.parse("2024-08-01T15:00:00+09:00")
        return FlozenClock(now)
    }
}