package tech.gelab.cardiograph.bridge.api.model

/** Системные события */
enum class EventSysEnum {
    /** Выключение */
    EVENT_SYS_PWR_OFF,
    /** Включение */
    EVENT_SYS_PWR_ON,
    /** Поставили на зарядку */
    EVENT_SYS_CHRG_ON,
    /** Сняли с зарядки */
    EVENT_SYS_CHRG_OFF
}