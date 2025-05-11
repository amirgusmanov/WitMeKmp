package previews

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kz.witme.project.create_book.existing_books.component.SearchBar
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
private fun SearchBar_Preview() {
    SearchBar(
        modifier = Modifier.fillMaxSize(),
        expanded = true,
        query = "",
        content = @Composable {

        },
        onQueryChange = {},
        onSearchClick = {},
        onExpandedChange = {},
    )
}