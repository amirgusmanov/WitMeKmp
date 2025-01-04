package kz.witme.project.timer_details.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kz.witme.project.common_ui.extension.clickableWithPressedState
import kz.witme.project.common_ui.theme.DefaultRoundedShape
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import org.jetbrains.compose.resources.painterResource
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.ic_confirm

@Composable
internal fun ConfirmButton(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = modifier.clickableWithPressedState(
            onClick = onClick,
            scaleWhenPressed = 0.97f
        ),
        colors = CardDefaults.cardColors().copy(containerColor = LocalWitMeTheme.colors.primary400),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = DefaultRoundedShape
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(40.dp).align(Alignment.Center),
                    color = LocalWitMeTheme.colors.white
                )
            } else {
                Icon(
                    modifier = Modifier.size(40.dp).align(Alignment.Center),
                    painter = painterResource(Res.drawable.ic_confirm),
                    contentDescription = null,
                    tint = LocalWitMeTheme.colors.white
                )
            }
        }
    }
}