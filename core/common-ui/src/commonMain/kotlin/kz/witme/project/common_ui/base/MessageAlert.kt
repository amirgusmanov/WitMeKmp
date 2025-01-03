package kz.witme.project.common_ui.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import kz.witme.project.common_ui.theme.LocalWitMeTheme.typography
import kz.witme.project.common_ui.theme.lightPalette
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.attention
import witmekmp.core.common_ui.generated.resources.ic_notification_message
import witmekmp.core.common_ui.generated.resources.not_okay
import witmekmp.core.common_ui.generated.resources.okay

@Composable
fun MessageAlert(
    modifier: Modifier = Modifier,
    subtitle: String,
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
    negativeButtonText: String = stringResource(Res.string.not_okay),
    positiveButtonText: String =  stringResource(Res.string.okay)
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
                painter = painterResource(Res.drawable.ic_notification_message),
                tint = LocalWitMeTheme.colors.focusHelperAlert,
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(Res.string.attention),
                color = lightPalette.primary400,
                style = typography.medium16
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = subtitle,
                color = lightPalette.secondary500,
                style = typography.regular16,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DefaultOutlinedButton(
                    onClick = onDismiss,
                    text = negativeButtonText,
                    modifier = Modifier.weight(1f)
                )
                DefaultButton(
                    onClick = onConfirm,
                    text = positiveButtonText,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}