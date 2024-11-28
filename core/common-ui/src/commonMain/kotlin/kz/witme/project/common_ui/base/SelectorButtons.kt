package kz.witme.project.common_ui.base

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import kz.witme.project.common_ui.theme.WitMeTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun <T : Enum<T>> DefaultSelector(
    modifier: Modifier = Modifier,
    enumValues: ImmutableList<T>,
    selectedValue: T,
    onValueSelected: (T) -> Unit,
    getDisplayName: (T) -> String = { it.name },
    selectedContainerColor: Color = LocalWitMeTheme.colors.primary400,
    unselectedContainerColor: Color = LocalWitMeTheme.colors.white,
    selectedContentColor: Color = LocalWitMeTheme.colors.white,
    unselectedContentColor: Color = LocalWitMeTheme.colors.black
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        enumValues.forEach { value ->
            SelectorButton(
                text = getDisplayName(value),
                isSelected = selectedValue == value,
                onClick = { onValueSelected(value) },
                selectedContainerColor = selectedContainerColor,
                unselectedContainerColor = unselectedContainerColor,
                selectedContentColor = selectedContentColor,
                unselectedContentColor = unselectedContentColor,
            )
        }
    }
}

@Composable
fun <T : Enum<T>> TitledSelector(
    modifier: Modifier = Modifier,
    titleText: String,
    enumValues: ImmutableList<T>,
    selectedValue: T?,
    onValueSelected: (T) -> Unit,
    getDisplayName: (T) -> String = { it.name },
    selectedContainerColor: Color = LocalWitMeTheme.colors.primary400,
    unselectedContainerColor: Color = LocalWitMeTheme.colors.white,
    selectedContentColor: Color = LocalWitMeTheme.colors.white,
    unselectedContentColor: Color = LocalWitMeTheme.colors.black
) {
    Column(modifier = modifier) {
        Text(
            text = titleText,
            style = LocalWitMeTheme.typography.semiBold16
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            enumValues.forEach { value ->
                SelectorButton(
                    text = getDisplayName(value),
                    isSelected = selectedValue == value,
                    onClick = { onValueSelected(value) },
                    selectedContainerColor = selectedContainerColor,
                    unselectedContainerColor = unselectedContainerColor,
                    selectedContentColor = selectedContentColor,
                    unselectedContentColor = unselectedContentColor,
                )
            }
        }
    }
}


@Composable
fun SelectorButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    selectedContainerColor: Color = LocalWitMeTheme.colors.primary400,
    unselectedContainerColor: Color = LocalWitMeTheme.colors.white,
    selectedContentColor: Color = LocalWitMeTheme.colors.white,
    unselectedContentColor: Color = LocalWitMeTheme.colors.black
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isSelected) selectedContainerColor else unselectedContainerColor,
            contentColor = if (isSelected) selectedContentColor else unselectedContentColor
        ),
        border = BorderStroke(
            width = 1.dp,
            color = LocalWitMeTheme.colors.black
        ),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        Text(
            text = text,
            style = LocalWitMeTheme.typography.regular12
        )
    }
}

private enum class BookTypeExample(val displayName: String) {
    PAPER("Бумажная книга"),
    ELECTRONIC("Электронная книга"),
    AUDIO("Аудио книга")
}

@Preview
@Composable
private fun EnumSelectorPreview() {
    val selectedType = remember { mutableStateOf(BookTypeExample.ELECTRONIC) }

    WitMeTheme {
        DefaultSelector(
            modifier = Modifier.padding(16.dp),
            enumValues = BookTypeExample.entries.toImmutableList(),
            selectedValue = selectedType.value,
            onValueSelected = { selectedType.value = it },
            getDisplayName = { it.displayName }
        )
    }
}

@Preview
@Composable
private fun TitledSelectorPreview() {
    val selectedType = remember { mutableStateOf(BookTypeExample.ELECTRONIC) }

    WitMeTheme {
        TitledSelector(
            modifier = Modifier.padding(16.dp),
            enumValues = BookTypeExample.entries.toImmutableList(),
            selectedValue = selectedType.value,
            onValueSelected = { selectedType.value = it },
            getDisplayName = { it.displayName },
            titleText = "Тип книги"
        )
    }
}
