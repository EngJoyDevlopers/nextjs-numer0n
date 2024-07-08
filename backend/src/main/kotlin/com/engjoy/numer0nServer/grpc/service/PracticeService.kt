package com.engjoy.numer0nServer.grpc.service

import com.engjoy.kotlingrpc.proto.Numer0nPracticeServiceGrpcKt
import com.engjoy.kotlingrpc.proto.Practice
import com.engjoy.numer0nServer.domain.Cards
import com.engjoy.numer0nServer.util.Logging
import com.engjoy.numer0nServer.util.logger
import com.google.rpc.BadRequest
import io.grpc.Metadata
import io.grpc.Status
import io.grpc.StatusException
import io.grpc.protobuf.ProtoUtils
import org.lognet.springboot.grpc.GRpcService
import toProtobufTimestamp
import java.time.ZonedDateTime
import java.util.*
import kotlin.collections.HashMap
import kotlin.random.Random

private class PracticeRoom(numDigits: Int) {
    val cards = Cards.createRandomly(numDigits, Random(Math.round(Math.random())))
    private var _expiry = ZonedDateTime.now().plusHours(1)

    init {
        println(cards.toString())
    }

    val expiry: ZonedDateTime
        get() = _expiry

    fun updateExpiry() {
        _expiry = ZonedDateTime.now().plusMinutes(30)
    }
}

@GRpcService
class PracticeService: Numer0nPracticeServiceGrpcKt.Numer0nPracticeServiceCoroutineImplBase() {
    private val _rooms = HashMap<String, PracticeRoom>()

    companion object: Logging {
        val logger = logger()
    }

    private fun cleanupRoom() {
        val now = ZonedDateTime.now()
        val expiredIds = _rooms.filter { kv -> kv.value.expiry < now }.map { kv -> kv.key }.toSet()
        _rooms.keys.removeAll(expiredIds)
    }

    override suspend fun createRoom(request: Practice.CreateRoomRequest): Practice.CreateRoomResponse {
        cleanupRoom()
        if (request.digits == 0 || 9 < request.digits) {
            val err = BadRequest.FieldViolation.newBuilder()
                .setField("digits")
                .setDescription("Digits must be either of 1 ~ 9, but ${request.digits} is requested.")
                .build()

            val errs = BadRequest.newBuilder().addFieldViolations(err).build()

            val detail = Metadata()
            detail.put(ProtoUtils.keyForProto(errs), errs)

            throw StatusException(Status.INVALID_ARGUMENT, detail)
        }

        val id =  UUID.randomUUID().toString()
        _rooms[id] = PracticeRoom(request.digits)
        logger.info("Room is created: id=$id, cards=${_rooms[id]!!.cards}")

        return Practice.CreateRoomResponse.newBuilder()
            .setRoomId(id)
            .setExpiry(_rooms[id]!!.expiry.toProtobufTimestamp())
            .build()
    }

    override suspend fun ask(request: Practice.AskRequest): Practice.AskResponse {
        cleanupRoom()
        val room = _rooms[request.roomId]
        if (room == null) {
            val err = BadRequest.FieldViolation.newBuilder()
                .setField("roomId")
                .setDescription("Room is not found or already expired. ${request.roomId}")
                .build()

            val errs = BadRequest.newBuilder().addFieldViolations(err).build()

            val detail = Metadata()
            detail.put(ProtoUtils.keyForProto(errs), errs)

            throw StatusException(Status.INVALID_ARGUMENT, detail)
        }
        if (room.cards.digits != request.numbersCount) {
            val err = BadRequest.FieldViolation.newBuilder()
                .setField("numbers")
                .setDescription("Digit of asked numbers does not match with card digits. ${room.cards.digits} is expected.")
                .build()

            val errs = BadRequest.newBuilder().addFieldViolations(err).build()

            val detail = Metadata()
            detail.put(ProtoUtils.keyForProto(errs), errs)

            throw StatusException(Status.INVALID_ARGUMENT, detail)
        }

        room.updateExpiry()
        val result = room.cards.ask(request.numbersList)
        logger.info("Asked: " +
            "id=${request.roomId}, " +
            "asked=${request.numbersList.joinToString("-") { x -> x.toString() }}, " +
            "ans=${room.cards}, " +
            "eat=${result.eat}, bite=${result.bite}"
        )

        return Practice.AskResponse.newBuilder()
            .setExpiry(room.expiry.toProtobufTimestamp())
            .setEat(result.eat)
            .setBite(result.bite)
            .build()
    }
}