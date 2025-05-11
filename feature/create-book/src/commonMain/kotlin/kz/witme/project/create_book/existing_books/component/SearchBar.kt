@file:OptIn(ExperimentalMaterial3Api::class)

package kz.witme.project.create_book.existing_books.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import kz.witme.project.common_ui.base.DefaultTextField
import org.jetbrains.compose.resources.stringResource
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.book_name
import androidx.compose.material3.SearchBar as MaterialSearchBar

@Composable
internal fun SearchBar(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    query: String,
    content: @Composable () -> Unit,
    onQueryChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    onExpandedChange: (Boolean) -> Unit
) {
    MaterialSearchBar(
        modifier = modifier.fillMaxWidth(),
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        inputField = {
            DefaultTextField(
                query = query,
                textPlaceholder = stringResource(Res.string.book_name),
                onQueryChanged = onQueryChange,
                shape = CircleShape,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                trailingIcon = if (expanded) Icons.Default.Search else Icons.Default.Close,
                onTrailingIconClick = {
                    if (expanded) onSearchClick() else onExpandedChange(false)
                }
            )
        },
        content = {
            content()
        }
    )
}