package kz.witme.project.common_ui.base

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kz.witme.project.common_ui.theme.DefaultRoundedShape
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import kz.witme.project.common_ui.theme.WitMeTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DefaultButton(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    buttonColor: Color = LocalWitMeTheme.colors.primary400,
    onClick: () -> Unit,
    text: String,
    textStyle: TextStyle = LocalWitMeTheme.typography.medium16,
    textColor: Color = LocalWitMeTheme.colors.white,
    minHeight: Dp = 60.dp,
    maxHeight: Dp = 60.dp,
    shape: Shape = DefaultRoundedShape,
    borderStroke: BorderStroke = BorderStroke(
        width = 0.dp,
        color = LocalWitMeTheme.colors.primary400
    ),
    isEnabled: Boolean = true,
) {
    Button(
        modifier = modifier
            .heightIn(minHeight, maxHeight),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = buttonColor,
            disabledBackgroundColor = buttonColor.copy(alpha = 0.5f),
        ),
        onClick = onClick,
        shape = shape,
        border = borderStroke,
        enabled = isEnabled,
    ) {
        Text(
            modifier = textModifier,
            text = text,
            style = textStyle,
            color = textColor,
        )
    }
}

@Preview
@Composable
private fun DefaultButtonPreview() {
    WitMeTheme {
        DefaultButton(
            onClick = {},
            text = "Hello world"
        )
    }
}