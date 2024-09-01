package com.engjoy.numer0nServer.endpoint.grpc.convert

import com.google.protobuf.Timestamp
import java.time.ZonedDateTime

fun ZonedDateTime.toProtobufTimestamp(): Timestamp {
    return Timestamp.newBuilder()
        .setSeconds(this.toEpochSecond())
        .setNanos(this.nano)
        .build()
}
