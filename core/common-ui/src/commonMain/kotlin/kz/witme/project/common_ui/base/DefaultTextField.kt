package kz.witme.project.common_ui.base

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kz.witme.project.common_ui.theme.DefaultRoundedShape
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import kz.witme.project.common_ui.theme.WitMeTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DefaultTextField(
    modifier: Modifier = Modifier,
    query: String,
    textPlaceholder: String,
    minHeight: Dp = 40.dp,
    maxLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    onQueryChanged: (String) -> Unit = {},
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    containerColor: Color = LocalWitMeTheme.colors.search,
    borderColor: Color = LocalWitMeTheme.colors.search,
    placeholderTextColor: Color = LocalWitMeTheme.colors.searchPlaceholder,
    placeholderTextStyle: TextStyle = LocalWitMeTheme.typography.regular14,
    textStyle: TextStyle = LocalWitMeTheme.typography.regular14,
    trailingIcon: ImageVector? = null,
    singleLine: Boolean = true,
    openKeyboardEnabled: Boolean = true,
    isError: Boolean = false
) {
    OutlinedTextField(
        value = query,
        readOnly = openKeyboardEnabled.not(),
        onValueChange = onQueryChanged,
        placeholder = {
            Text(
                text = textPlaceholder,
                style = placeholderTextStyle,
                color = placeholderTextColor
            )
        },
        singleLine = singleLine,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = containerColor,
            textColor = Color.Black,
            disabledTextColor = Color.Black,
            focusedBorderColor = borderColor,
            unfocusedBorderColor = borderColor,
            errorBorderColor = LocalWitMeTheme.colors.error200,
            errorTrailingIconColor = LocalWitMeTheme.colors.error200
        ),
        interactionSource = interactionSource,
        maxLines = maxLines,
        minLines = maxLines.takeIf { maxLines != 1 } ?: 1,
        keyboardOptions = keyboardOptions,
        textStyle = textStyle,
        shape = DefaultRoundedShape,
        isError = isError,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = minHeight),
        trailingIcon = {
            trailingIcon?.let {
                Icon(
                    imageVector = it,
                    tint = LocalWitMeTheme.colors.black,
                    contentDescription = null
                )
            }
        }
    )
}

@Preview
@Composable
private fun DefaultTextFieldPreview() {
    WitMeTheme {
        DefaultTextField(
            query = "",
            textPlaceholder = "Email адрес",
            placeholderTextColor = Color.Black,
            placeholderTextStyle = LocalWitMeTheme.typography.regular16,
            trailingIcon = Icons.Outlined.DateRange
        )
    }
}