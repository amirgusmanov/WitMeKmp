package kz.witme.project.timer.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kz.witme.project.common_ui.extension.clickableWithPressedState
import kz.witme.project.common_ui.theme.DefaultRoundedShape
import kz.witme.project.common_ui.theme.LocalWitMeTheme

@Composable
internal fun TimerButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    buttonText: String
) {
    Box(
        modifier = modifier.clickableWithPressedState(
            onClick = onClick,
            scaleWhenPressed = 0.98f
        )
    ) {
        Box(
            modifier = modifier
                .clip(DefaultRoundedShape)
                .background(LocalWitMeTheme.colors.primary300.copy(alpha = 0.65f))
        ) {
            Text(
                text = buttonText,
                style = LocalWitMeTheme.typography.regular16,
                color = LocalWitMeTheme.colors.white,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}