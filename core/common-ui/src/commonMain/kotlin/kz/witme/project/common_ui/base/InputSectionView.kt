package kz.witme.project.common_ui.base

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import kz.witme.project.common_ui.theme.WitMeTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun InputSectionView(
    modifier: Modifier = Modifier,
    hintText: String,
    titleText: String,
    query: String,
    maxLines: Int = 1,
    minHeight: Dp = 40.dp,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    onQueryChanged: (text: String) -> Unit = {},
    trailingIcon: ImageVector? = null,
    openKeyboardEnabled: Boolean = true,
    singleLine: Boolean = true
) {
    Column(modifier = modifier) {
        Text(
            text = titleText,
            style = LocalWitMeTheme.typography.semiBold16
        )
        Spacer(modifier = Modifier.height(8.dp))
        DefaultTextField(
            query = query,
            textPlaceholder = hintText,
            minHeight = minHeight,
            containerColor = LocalWitMeTheme.colors.white,
            borderColor = LocalWitMeTheme.colors.black,
            placeholderTextColor = LocalWitMeTheme.colors.secondary500,
            placeholderTextStyle = LocalWitMeTheme.typography.regular16,
            textStyle = LocalWitMeTheme.typography.regular14,
            maxLines = maxLines,
            keyboardOptions = keyboardOptions,
            onQueryChanged = onQueryChanged,
            trailingIcon = trailingIcon,
            openKeyboardEnabled = openKeyboardEnabled,
            interactionSource = interactionSource,
            singleLine = singleLine
        )
    }
}

@Preview
@Composable
private fun InputSectionViewPreview() {
    WitMeTheme {
        InputSectionView(
            hintText = "Богатый папа - бедный папа...",
            titleText = "Название книги",
            query = "",
        )
    }
}