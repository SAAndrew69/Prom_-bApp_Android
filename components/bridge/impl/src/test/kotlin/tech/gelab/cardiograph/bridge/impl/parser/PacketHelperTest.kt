package tech.gelab.cardiograph.bridge.impl.parser

import org.junit.Before
import org.junit.Test
import tech.gelab.cardiograph.bridge.api.model.EventTime
import tech.gelab.cardiograph.bridge.api.model.Packet

class PacketHelperTest {

    private lateinit var adsPayload: ByteArray
    private lateinit var adsTestPacket: ByteArray

    private lateinit var max172xxVals: ByteArray
    private lateinit var batPayload: ByteArray

    @Before
    fun setUp() {
        adsPayload = byteArrayOf(
            // PACKET ID
            0x00, 0x02,
            // REC NO
            0x04, 0x55, 0x55, 0x55,
            // SAMPLE NO
            0x01, 0x55,
            // LENGTH
            0x00, 0x06,
            // DATA
            0x01, 0x02, 0x03, 0x04, 0x05, 0x06
        )
        adsTestPacket = byteArrayOf(
            // KRDU
            0x4B, 0x52, 0x44, 0x55,
            // FLAGS
            0x00,
            // LOG VER
            0x01,
            // PAYLOAD SIZE
            0x00, 0x0E,
            // PAYLOAD
            *adsPayload,
            // CHECKSUM
            0xFD.toByte(), 0x44
        )

        max172xxVals = byteArrayOf(
            // TEMP
            0x00, 0x24,
            // BATT
            0x00, 0x98.toByte(),
            // AVG CURR
            0x00, 0xFF.toByte(),
            // PERCENTS
            0x00, 0x50,
            // AVG CAP
            0x00, 0x40,
            // TIME TO EMPTY
            0x00, 0x01,
            // TIME TO FULL
            0x00, 0x01
        )

        batPayload = byteArrayOf(
            // PACKET ID
            0x00, 0x01,
            // REC NO
            0x04, 0x55, 0x55, 0x56,
            // EVENT ID
            0x00, 0x06,
            // SYSTIME
            0x66, 0x99.toByte(), 0x75, 0x08,
            // max172xx_vals
            *max172xxVals
        )
    }

    @Test
    fun `ads bytes success parse`() {
        val packet = PacketHelper.getPacket(0x00u, 0x01, DataBuffer(adsPayload))
        assert(
            packet is Packet.AdsFlowPacket && packet.payload.buff.contentEquals(
                intArrayOf(1, 2, 3, 4, 5, 6)
            )
        )
    }

    @Test
    fun `bat event time success parse`() {
        val packet = PacketHelper.getPacket(0x00u, 0x01, DataBuffer(batPayload))
        assert(
            packet is Packet.EventTimePacket && (packet.payload as EventTime.Bat).max172xxVals.percents == 80
        )
    }

}