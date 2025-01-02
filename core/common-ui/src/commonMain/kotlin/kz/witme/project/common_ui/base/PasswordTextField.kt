package kz.witme.project.common_ui.base

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kz.witme.project.common_ui.base.transformation.PlaceholderTransformation
import kz.witme.project.common_ui.theme.DefaultRoundedShape
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import kz.witme.project.common_ui.theme.WitMeTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.ic_eye
import witmekmp.core.common_ui.generated.resources.ic_eye_slash

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    query: String,
    textPlaceholder: String,
    minHeight: Dp = 40.dp,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onQueryChanged: (String) -> Unit = {}
) {
    var passwordVisibility by remember { mutableStateOf(false) }
    val icon = if (passwordVisibility) {
        painterResource(Res.drawable.ic_eye)
    } else {
        painterResource(Res.drawable.ic_eye_slash)
    }

    OutlinedTextField(
        value = query,
        onValueChange = onQueryChanged,
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = LocalWitMeTheme.colors.search,
            textColor = Color.Black,
            disabledTextColor = Color.Black,
            focusedBorderColor = LocalWitMeTheme.colors.search,
            unfocusedBorderColor = LocalWitMeTheme.colors.search,
            errorBorderColor = LocalWitMeTheme.colors.error200,
            errorTrailingIconColor = LocalWitMeTheme.colors.error200
        ),
        trailingIcon = {
            IconButton(onClick = {
                passwordVisibility = !passwordVisibility
            }) {
                Icon(
                    painter = icon,
                    contentDescription = "Visibility Icon"
                )
            }
        },
        keyboardOptions = keyboardOptions.copy(keyboardType = KeyboardType.Password),
        visualTransformation = if (query.isEmpty()) {
            PlaceholderTransformation(
                placeholder = textPlaceholder,
                placeholderColor = LocalWitMeTheme.colors.searchPlaceholder,
            )
        } else if (passwordVisibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        isError = isError,
        textStyle = LocalWitMeTheme.typography.regular14,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = minHeight)
            .clip(DefaultRoundedShape)
    )
}

@Preview
@Composable
private fun PasswordTextFieldPreview() {
    WitMeTheme {
        PasswordTextField(query = "", textPlaceholder = "Пароль")
    }
}