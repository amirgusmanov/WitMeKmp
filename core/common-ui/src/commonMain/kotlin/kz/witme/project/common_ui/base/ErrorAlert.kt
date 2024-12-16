package kz.witme.project.common_ui.base

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
import kz.witme.project.common_ui.theme.DefaultRoundedShape
import kz.witme.project.common_ui.theme.LocalWitMeTheme.typography
import kz.witme.project.common_ui.theme.lightPalette
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.error
import witmekmp.core.common_ui.generated.resources.ic_error
import witmekmp.core.common_ui.generated.resources.ok

@Composable
fun ErrorAlert(
    modifier: Modifier = Modifier,
    errorText: String,
    onDismiss: () -> Unit = {}
) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = modifier
                .clip(DefaultRoundedShape)
                .background(color = lightPalette.white)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_error),
                tint = lightPalette.error100,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(Res.string.error),
                color = lightPalette.primary400,
                style = typography.medium16
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = errorText,
                color = lightPalette.secondary500,
                style = typography.regular16,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            DefaultButton(
                onClick = onDismiss,
                text = stringResource(Res.string.ok),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}