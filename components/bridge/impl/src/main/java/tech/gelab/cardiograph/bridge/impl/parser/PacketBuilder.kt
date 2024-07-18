package tech.gelab.cardiograph.bridge.impl.parser

import tech.gelab.cardiograph.bridge.api.model.Packet
import tech.gelab.cardiograph.bridge.impl.exception.EmptyPayloadData
import tech.gelab.cardiograph.bridge.impl.exception.IntegrityCheckFailed
import tech.gelab.cardiograph.bridge.impl.exception.ParseException

class PacketBuilder {

    companion object {
        internal const val KRDU = 0x4B524455L
        private const val KRDU_SUM = 0x136
        private const val EXPECTED_CHECKSUM = 0xFFFF
    }

    private val payload: DataBuffer = DataBuffer()

    private var byteSum = 0

    private var krduFound: Boolean = false
        set(value) {
            if (value) {
                byteSum += KRDU_SUM
            }
            field = value
        }

    var flags: UByte? = null
        set(value) {
            increaseByteSum(value)
            field = value
        }

    var logVer: UByte? = null
        set(value) {
            increaseByteSum(value)
            field = value
        }

    var checkSum: Int? = null
        set(value) {
            increaseByteSum(value)
            field = value
        }

    private fun increaseByteSum(value: Int?) {
        if (value != null) {
            byteSum += value ushr 24
            byteSum += (value ushr 16).toUByte().toShort()
            byteSum += (value ushr 8).toUByte().toShort()
            byteSum += value.toUByte().toShort()
        }
    }

    private fun increaseByteSum(value: UByte?) {
        if (value != null) {
            byteSum += value.toInt()
        }
    }

    fun trySetKrdu(uInt32: Long): Boolean {
        val isKrdu = uInt32 == KRDU
        if (isKrdu) {
            krduFound = true
        }
        return isKrdu
    }

    fun isKrduSet() : Boolean {
        return krduFound
    }

    /** Allocates memory for the buffer with given size */
    fun allocateBuffer(size: Int) {
        increaseByteSum(size)
        payload.capacity(size)
    }

    fun getPayloadCapacity(): Int {
        return payload.capacity()
    }

    fun setPayload(data: Byte) {
        increaseByteSum(data.toUByte())
        payload.setUInt8(data)
    }

    /** Get buffered payload data
     *  @return null if buffer capacity equals zero or integrity check was failed */
    fun getPayloadData(): ByteArray? {
        return if (!checkIntegrity()) null
        else payload.data
    }

    fun build(): Packet {
        val payloadData = this.payload
        val flags = this.flags ?: throw ParseException("Null flags data")
        val logVer = this.logVer ?: throw ParseException("Null logVer")

        if (!checkIntegrity()) {
            throw IntegrityCheckFailed("Expected byte sum of $EXPECTED_CHECKSUM, got $byteSum")
        } else if (payloadData.data == null || payloadData.capacity() == 0) {
            throw EmptyPayloadData()
        }

        return PacketHelper.getPacket(flags, logVer.toInt(), payloadData)
    }

    fun checkIntegrity(): Boolean {
        return byteSum == EXPECTED_CHECKSUM
    }

    fun reset() {
        krduFound = false
        flags = null
        logVer = null
        payload.capacity(0)
        checkSum = null
        byteSum = 0
    }
}