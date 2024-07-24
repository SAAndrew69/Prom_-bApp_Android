package tech.gelab.cardiograph.conclusion.impl.domain

sealed interface ConclusionEvent {
    data object GetConclusionClick : ConclusionEvent
    data object DownloadConclusionClick : ConclusionEvent
    data object StartNewMeasure : ConclusionEvent
}