package kz.witme.project.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kz.witme.project.common_ui.base.DefaultButton
import kz.witme.project.common_ui.theme.DefaultRoundedShape
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import kz.witme.project.common_ui.theme.LocalWitMeTheme.typography
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.error
import witmekmp.core.common_ui.generated.resources.ic_error
import witmekmp.core.common_ui.generated.resources.logout

@Composable
internal fun TimerAlert(
    modifier: Modifier = Modifier,
    errorText: String,
    onButtonClick: () -> Unit = {},
    onDismissRequest: () -> Unit = {}
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = modifier
                .clip(DefaultRoundedShape)
                .background(color = LocalWitMeTheme.colors.white)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_error),
                tint = LocalWitMeTheme.colors.error100,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(Res.string.error),
                color = LocalWitMeTheme.colors.primary400,
                style = typography.medium16
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = errorText,
                color = LocalWitMeTheme.colors.secondary500,
                style = typography.regular16,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            DefaultButton(
                onClick = onButtonClick,
                text = stringResource(Res.string.logout),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}