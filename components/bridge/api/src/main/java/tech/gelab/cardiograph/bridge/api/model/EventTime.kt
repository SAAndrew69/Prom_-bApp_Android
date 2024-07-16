package tech.gelab.cardiograph.bridge.api.model

sealed class EventTime(
    /** Идентификатор события */
    val eventId: Int
): Payload {

    override val packetID = PacketID.PACKET_ID_EVENT_TIME
    /** Системное время */
    abstract val sysTime: Long

    /** Запуск измерений */
    class AdsStarted(
        override val recNo: Long,
        override val sysTime: Long,
        /** Код ошибки */
        val err: Int,
        /** TODO Длина конфигурации */
        val cfgLen: Int,
        /** Текущая конфигурация */
        val cfg: ByteArray
    ) : EventTime(eventId = PacketEventID.ADS_STARTED)

    /** Остановка измерений */
    class AdsStopped(
        override val recNo: Long,
        override val sysTime: Long,
        /** Код ошибки */
        val err: Int,
        /** TODO Длина конфигурации */
        val cfgLen: Int,
        /** Текущая конфигурация */
        val cfg: ByteArray
    ) : EventTime(eventId = PacketEventID.ADS_STOPPED)

    /** Системная ошибка */
    class SysError(
        override val recNo: Long,
        override val sysTime: Long,
        /** Код ошибки */
        val error: Int
    ) : EventTime(eventId = PacketEventID.SYS_ERROR)

    /** Системные события, не относящиеся к ошибкам */
    class System(
        override val recNo: Long,
        override val sysTime: Long,
        /** Системное событие */
        val eventSysEnum: EventSysEnum
    ) : EventTime(eventId = PacketEventID.SYSTEM)

    /** Установка системного времени */
    class SetSystime(
        override val recNo: Long,
        override val sysTime: Long,
        /** Новое системное время */
        val newSystime: Long
    ) : EventTime(eventId = PacketEventID.SET_SYSTIME)

    /** Телеметрия от батареи */
    class Bat(
        override val recNo: Long,
        override val sysTime: Long,
        /** Информация от батареи */
        val max172xxVals: Max172xxVals
    ) : EventTime(eventId = PacketEventID.BAT)

}