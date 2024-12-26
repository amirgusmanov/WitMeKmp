package kz.witme.project.timer.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import org.jetbrains.compose.resources.stringResource
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.hours
import witmekmp.core.common_ui.generated.resources.minutes
import witmekmp.core.common_ui.generated.resources.seconds

@Composable
internal fun TimerView(
    hours: String,
    minutes: String,
    seconds: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TimeUnitView(unitValue = hours)
            TimeLabelView(label = stringResource(Res.string.hours))
        }
        SeparatorView()
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TimeUnitView(unitValue = minutes)
            TimeLabelView(label = stringResource(Res.string.minutes))
        }
        SeparatorView()
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TimeUnitView(unitValue = seconds)
            TimeLabelView(label = stringResource(Res.string.seconds))
        }
    }
}

@Composable
private fun TimeUnitView(unitValue: String) {
    Text(
        text = unitValue,
        style = LocalWitMeTheme.typography.semiBold48,
        color = LocalWitMeTheme.colors.white,
        modifier = Modifier.padding(horizontal = 8.dp)
    )
}

@Composable
private fun SeparatorView() {
    Text(
        text = ":",
        style = LocalWitMeTheme.typography.semiBold48,
        color = LocalWitMeTheme.colors.white,
    )
}

@Composable
private fun TimeLabelView(
    label: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = label,
        color = LocalWitMeTheme.colors.primary200,
        style = LocalWitMeTheme.typography.regular16
    )
}
