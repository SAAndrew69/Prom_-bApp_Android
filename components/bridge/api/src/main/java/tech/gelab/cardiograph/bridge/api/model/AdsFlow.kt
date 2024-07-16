package tech.gelab.cardiograph.bridge.api.model

class AdsFlow(
    override val recNo: Long,
    /** Номер сэмпла */
    val sampleNo: Int,
    /** TODO длина данных */
    val length: Int,
    /** Массив данных */
    val buff: ByteArray
) : Payload {
    override val packetID: Int
        get() = PacketID.PACKET_ID_ADS_FLOW
}