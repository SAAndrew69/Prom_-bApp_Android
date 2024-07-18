package tech.gelab.cardiograph.bridge.impl.parser

import tech.gelab.cardiograph.bridge.api.model.AdsFlow
import tech.gelab.cardiograph.bridge.api.model.EventSysEnum
import tech.gelab.cardiograph.bridge.api.model.EventTime
import tech.gelab.cardiograph.bridge.api.model.Max172xxVals
import tech.gelab.cardiograph.bridge.api.model.Packet
import tech.gelab.cardiograph.bridge.api.model.PacketEventID
import tech.gelab.cardiograph.bridge.api.model.PacketID
import tech.gelab.cardiograph.bridge.impl.exception.IllegalEventID
import tech.gelab.cardiograph.bridge.impl.exception.IllegalPacketID
import timber.log.Timber

object PacketHelper {

    private fun getAdsFlow(recNo: Long, data: DataBuffer): AdsFlow {
        val sampleNo = data.getUInt16()
        val length = data.getUInt16()
        if (length < data.available()) {
            Timber.w("Available data is more than parsed length in $recNo, data: ${data.data!!.toHexString()}")
        }
        val sample = IntArray(length)
        for (i in 0 until length) {
            sample[i] = data.getUInt8().toInt()
        }
        return AdsFlow(recNo, sampleNo, sample)
    }

    private fun getEventTime(recNo: Long, data: DataBuffer): EventTime {
        val eventID = data.getUInt16()
        val systime = data.getUInt32()
        return when (eventID) {
            PacketEventID.ADS_STARTED, PacketEventID.ADS_STOPPED -> getAdsEvent(
                eventID,
                recNo,
                systime,
                data
            )

            PacketEventID.SYS_ERROR -> getSysErrorEvent(recNo, systime, data)
            PacketEventID.SYSTEM -> getSystemEvent(recNo, systime, data)
            PacketEventID.SET_SYSTIME -> getSetTimeEvent(recNo, systime, data)
            PacketEventID.BAT -> getBatEvent(recNo, systime, data)
            PacketEventID.UNKNOWN -> getUnknown(recNo, systime, data)
            else -> throw IllegalEventID(eventID)
        }
    }

    private fun getAdsEvent(eventID: Int, recNo: Long, systime: Long, data: DataBuffer): EventTime {
        val err = data.getUInt16()
        val cfgLen = data.getUInt16()
        val cfg = ByteArray(cfgLen)
        for (i in 0 until cfgLen) {
            cfg[i] = data.getInt8()
        }
        return if (eventID == PacketEventID.ADS_STARTED) EventTime.AdsStarted(
            recNo,
            systime,
            err,
            cfg
        ) else EventTime.AdsStopped(
            recNo,
            systime,
            err,
            cfg
        )
    }

    private fun getSysErrorEvent(recNo: Long, systime: Long, data: DataBuffer): EventTime.SysError {
        val error = data.getUInt16()
        return EventTime.SysError(recNo, systime, error)
    }

    private fun getSystemEvent(recNo: Long, systime: Long, data: DataBuffer): EventTime.System {
        val eventSys = EventSysEnum.entries[data.getUInt8().toInt()]
        return EventTime.System(recNo, systime, eventSys)
    }

    private fun getSetTimeEvent(
        recNo: Long,
        systime: Long,
        data: DataBuffer
    ): EventTime.SetSystime {
        val newSystime = data.getUInt32()
        return EventTime.SetSystime(recNo, systime, newSystime)
    }

    private fun getBatEvent(recNo: Long, systime: Long, data: DataBuffer): EventTime.Bat {
        val max172xxVals = getMax172xxVals(data)
        return EventTime.Bat(recNo, systime, max172xxVals)
    }

    private fun getUnknown(recNo: Long, systime: Long, data: DataBuffer): EventTime.Unknown {
        val arr = ByteArray(data.available())
        for (i in 0..arr.size) {
            arr[i] = data.getInt8()
        }
        return EventTime.Unknown(recNo, systime, arr)
    }

    private fun getMax172xxVals(data: DataBuffer): Max172xxVals {
        val temp = data.getUInt16()
        val batt = data.getUInt16()
        val avgCurr = data.getInt16()
        val percents = data.getUInt16()
        val avgCap = data.getUInt16()
        val timeToEmpty = data.getUInt16()
        val timeToFull = data.getUInt16()
        return Max172xxVals(temp, batt, avgCurr.toInt(), percents, avgCap, timeToEmpty, timeToFull)
    }


    fun getPacket(flags: UByte, logVer: Int, data: DataBuffer): Packet {
        val packetID = data.getUInt16()
        val recNo = data.getUInt32()
        return when (packetID) {
            PacketID.PACKET_ID_ADS_FLOW -> Packet.AdsFlowPacket(
                flags,
                logVer,
                getAdsFlow(recNo, data)
            )

            PacketID.PACKET_ID_EVENT_TIME -> Packet.EventTimePacket(
                flags,
                logVer,
                getEventTime(recNo, data)
            )

            else -> throw IllegalPacketID(packetID)
        }
    }

}