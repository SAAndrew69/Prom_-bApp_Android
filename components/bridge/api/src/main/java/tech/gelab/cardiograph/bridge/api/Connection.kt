package tech.gelab.cardiograph.bridge.api

import kotlinx.coroutines.flow.Flow
import tech.gelab.cardiograph.bridge.api.model.AdsFlow
import tech.gelab.cardiograph.bridge.api.model.EventTime

interface Connection {

    fun getLastEventTime(): EventTime?

    fun getLastAdsFlow(): AdsFlow?

    fun eventTimeFlow(): Flow<EventTime>

    fun adsFlow(): Flow<AdsFlow>

}