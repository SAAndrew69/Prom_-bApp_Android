package tech.gelab.cardiograph.bridge.impl.parser

import tech.gelab.cardiograph.bridge.api.model.PacketID

class Packet(
    val packetID: PacketID,
    val flags: Byte,
    val logVer: Int,
    val payloadSize: Int,
    val payload: ByteArray
)