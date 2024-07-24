package tech.gelab.cardiograph.reports.impl.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import tech.gelab.cardiograph.reports.impl.R
import tech.gelab.cardiograph.reports.impl.model.Report
import tech.gelab.cardiograph.ui.theme.CardiographAppTheme
import tech.gelab.cardiograph.ui.theme.spacing

@Composable
fun AvailableReportsList(modifier: Modifier = Modifier, reports: ImmutableList<Report>) {
    Column(modifier) {
        reports.forEach { report ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = MaterialTheme.spacing.small,
                        vertical = MaterialTheme.spacing.extraSmall
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ReportView(report = report)
                Icon(
                    painter = painterResource(id = R.drawable.icon_download),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview
@Composable
private fun CachedReportsPrev() {
    CardiographAppTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            AvailableReportsList(
                modifier = Modifier.fillMaxWidth(), reports = persistentListOf(
                    Report("ID 1234", dateTimeString = "13.04.2024 15:45", isUploaded = false),
                    Report("ID 1234", dateTimeString = "13.04.2024 15:45", isUploaded = false),
                    Report("ID 1234", dateTimeString = "13.04.2024 15:45", isUploaded = false),
                    Report("ID 1234", dateTimeString = "13.04.2024 15:45", isUploaded = false),
                    Report("ID 1234", dateTimeString = "13.04.2024 15:45", isUploaded = false),
                )
            )
        }
    }
}