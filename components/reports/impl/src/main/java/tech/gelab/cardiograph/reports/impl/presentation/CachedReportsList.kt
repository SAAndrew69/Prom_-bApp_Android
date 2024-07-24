package tech.gelab.cardiograph.reports.impl.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import tech.gelab.cardiograph.reports.impl.R
import tech.gelab.cardiograph.reports.impl.model.Report
import tech.gelab.cardiograph.ui.theme.CardiographAppTheme
import tech.gelab.cardiograph.ui.theme.spacing

@SuppressLint("DefaultLocale")
@Composable
fun CachedReportsList(modifier: Modifier = Modifier, reports: ImmutableList<Report>) {
    Surface(
        modifier,
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Column(modifier = Modifier.padding(MaterialTheme.spacing.small)) {
            Text(
                modifier = Modifier.padding(top = MaterialTheme.spacing.small),
                text = String.format(
                    "%s %d %s",
                    stringResource(R.string.text_you_have),
                    reports.size,
                    stringResource(R.string.text_undelivired_reports)
                ),
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                modifier = Modifier.padding(top = MaterialTheme.spacing.small),
                text = stringResource(R.string.text_network_required),
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            reports.forEach { report ->
                ReportView(
                    modifier = Modifier.padding(MaterialTheme.spacing.extraSmall),
                    report = report
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
            CachedReportsList(
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