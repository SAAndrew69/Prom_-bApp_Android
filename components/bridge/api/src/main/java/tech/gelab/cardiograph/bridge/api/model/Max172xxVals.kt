package tech.gelab.cardiograph.bridge.api.model

/** Структура информации о батарее  */
data class Max172xxVals(
    /** Средняя температура в градусах (val/256) */
    val temp: Int,
    /** Среднее напряжение на батарее, вольт (val/12800) (1,25 мВ на бит) */
    val batt: Int,
    /** Среднее значение тока мА 1,5625 мкА/Rsens (1 бит = 156,25 мкА для шунта 10 мОм), когда идет заряд, ток положительный */
    val avgCurr: Int,
    /** Процент заряда (val/256) */
    val percents: Int,
    /** Средняя емкость в мА*час (val/2) 5 мкВ*ч/Rsens (1 бит = 0,5 мА*ч для шунта 10 мОм) */
    val avgCap: Int,
    /** Время до полного разряда в часах (val/640) */
    val timeToEmpty: Int,
    /** Время до полного заряда в часах (val/640) */
    val timeToFull: Int
)