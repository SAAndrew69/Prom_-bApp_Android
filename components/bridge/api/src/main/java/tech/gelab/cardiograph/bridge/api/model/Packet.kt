package tech.gelab.cardiograph.bridge.api.model

sealed class Packet {
    abstract val flags: UByte
    abstract val logVer: Int
    abstract val payload: Payload

    data class AdsFlowPacket(
        override val flags: UByte,
        override val logVer: Int,
        override val payload: AdsFlow
    ): Packet()

    data class EventTimePacket(
        override val flags: UByte,
        override val logVer: Int,
        override val payload: EventTime
    ): Packet()

}