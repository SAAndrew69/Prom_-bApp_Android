package tech.gelab.cardiograph.reports.impl.model

data class Report(
    val name: String,
    val dateTimeString: String,
    val isUploaded: Boolean
)