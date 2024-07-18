package tech.gelab.cardiograph.bridge.api.model

class AdsFlow(
    override val recNo: Long,
    /** Номер сэмпла */
    val sampleNo: Int,
    /** Массив данных */
    val buff: IntArray
) : Payload {
    override val packetID: Int
        get() = PacketID.PACKET_ID_ADS_FLOW
}