package kz.witme.project.timer_details.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import kz.witme.project.common_ui.extension.clickableWithPressedState
import kz.witme.project.common_ui.theme.DefaultRoundedShape
import kz.witme.project.common_ui.theme.LocalWitMeTheme

@Composable
internal fun InfoCard(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    icon: Painter,
    onClick: () -> Unit = {}
) {
    ElevatedCard(
        modifier = modifier.clickableWithPressedState(
            onClick = onClick,
            scaleWhenPressed = 0.97f
        ),
        colors = CardDefaults.cardColors().copy(containerColor = LocalWitMeTheme.colors.white),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = DefaultRoundedShape
    ) {
        Row(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(40.dp),
                painter = icon,
                contentDescription = null,
                tint = LocalWitMeTheme.colors.primary400
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = title,
                    color = LocalWitMeTheme.colors.secondary400,
                    style = LocalWitMeTheme.typography.regular12
                )
                Text(
                    text = subtitle,
                    color = LocalWitMeTheme.colors.primary400,
                    style = LocalWitMeTheme.typography.medium24
                )
            }
        }
    }
}