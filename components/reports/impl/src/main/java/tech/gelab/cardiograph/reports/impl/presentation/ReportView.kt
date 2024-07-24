package tech.gelab.cardiograph.reports.impl.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import tech.gelab.cardiograph.reports.impl.R
import tech.gelab.cardiograph.reports.impl.model.Report
import tech.gelab.cardiograph.ui.theme.CardiographAppTheme
import tech.gelab.cardiograph.ui.theme.spacing

@Composable
fun ReportView(modifier: Modifier = Modifier, report: Report) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(id = R.drawable.icon_report),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground
        )
        Column(modifier = Modifier.padding(start = MaterialTheme.spacing.medium)) {
            Text(
                text = report.name,
                style = MaterialTheme.typography.labelLarge.copy(lineHeight = 16.sp)
            )
            Text(
                modifier = Modifier.padding(top = MaterialTheme.spacing.extraSmall),
                text = report.dateTimeString,
                style = MaterialTheme.typography.labelSmall.copy(lineHeight = 16.sp),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview
@Composable
private fun ReportPrev() {
    CardiographAppTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            ReportView(
                report = Report(
                    "ID 1234",
                    dateTimeString = "13.04.2024 15:45",
                    isUploaded = false
                )
            )
        }
    }
}