package tech.gelab.cardiograph.bridge.api.model

interface Payload {
    /** Идентификатор данных */
    val packetID: Int
    /** Уникальный номер записи */
    val recNo: Long
}