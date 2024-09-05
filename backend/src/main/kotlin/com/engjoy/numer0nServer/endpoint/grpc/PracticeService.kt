package com.engjoy.numer0nServer.endpoint.grpc

import com.engjoy.kotlingrpc.proto.Numer0nPracticeServiceGrpcKt
import com.engjoy.kotlingrpc.proto.Practice
import com.engjoy.numer0nServer.core.Clock
import com.engjoy.numer0nServer.core.GenUuid
import com.google.rpc.BadRequest
import io.grpc.Metadata
import io.grpc.Status
import io.grpc.StatusException
import io.grpc.protobuf.ProtoUtils
import org.lognet.springboot.grpc.GRpcService
import com.engjoy.numer0nServer.endpoint.grpc.convert.toProtobufTimestamp
import com.engjoy.numer0nServer.usecase.practice.GenNpcCards
import com.engjoy.numer0nServer.usecase.practice.PracticeMode

@GRpcService
class PracticeService(
    genUuid: GenUuid,
    genNpc: GenNpcCards,
    clock: Clock
) : Numer0nPracticeServiceGrpcKt.Numer0nPracticeServiceCoroutineImplBase() {
    private val _service = PracticeMode(genUuid, genNpc, clock)

    override suspend fun createRoom(request: Practice.CreateRoomRequest): Practice.CreateRoomResponse {
        try {
            val createdRoom = _service.createRoom(request.digits)
            return Practice.CreateRoomResponse.newBuilder()
                .setRoomId(createdRoom.id)
                .setExpiry(createdRoom.expirry.toProtobufTimestamp())
                .build()
        } catch (e: Exception) {
            val err = BadRequest.FieldViolation.newBuilder()
                .setField("digits")
                .setDescription(e.message)
                .build()

            val errs = BadRequest.newBuilder().addFieldViolations(err).build()

            val detail = Metadata()
            detail.put(ProtoUtils.keyForProto(errs), errs)

            throw StatusException(Status.INVALID_ARGUMENT, detail)
        }
    }

    override suspend fun ask(request: Practice.AskRequest): Practice.AskResponse {
        val roomInfo = _service.getRoomInfo(request.roomId)
        if (roomInfo == null) {
            val err = BadRequest.FieldViolation.newBuilder()
                .setField("roomId")
                .setDescription("Room '${request.roomId}' is not found or already expired.")
                .build()

            val errs = BadRequest.newBuilder().addFieldViolations(err).build()

            val detail = Metadata()
            detail.put(ProtoUtils.keyForProto(errs), errs)

            throw StatusException(Status.NOT_FOUND, detail)
        }

        try {
            val result = _service.ask(roomInfo.id, request.numbersList)
            return Practice.AskResponse.newBuilder()
                .setExpiry(roomInfo.expirry.toProtobufTimestamp())
                .setEat(result.eat)
                .setBite(result.bite)
                .build()
        } catch (e: Exception) {
            val err = BadRequest.FieldViolation.newBuilder()
                .setField("numbers")
                .setDescription(e.message)
                .build()

            val errs = BadRequest.newBuilder().addFieldViolations(err).build()

            val detail = Metadata()
            detail.put(ProtoUtils.keyForProto(errs), errs)

            throw StatusException(Status.INVALID_ARGUMENT, detail)
        }
    }
}
