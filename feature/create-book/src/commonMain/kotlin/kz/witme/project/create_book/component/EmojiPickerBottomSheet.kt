package kz.witme.project.create_book.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kz.witme.project.common_ui.theme.DefaultRoundedShape
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import org.jetbrains.compose.resources.stringResource
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.average_emoji

internal class EmojiBottomSheetScreen(
    private val selectedEmoji: Int,
    private val onEmojiSelected: (Int) -> Unit
) : Screen {

    @Composable
    override fun Content() {
        EmojiPickerBottomSheet(
            emojis = emojis,
            selectedEmoji = selectedEmoji,
            onEmojiSelected = onEmojiSelected,
        )
    }

    companion object {
        private val emojis = persistentListOf(
            "\uD83D\uDE00", // 😁
            "\uD83D\uDE42", // 🙂
            "\uD83D\uDE10", // 😐
            "\uD83D\uDE12", // 😒
            "\uD83D\uDE44", // 🙄
            "\uD83D\uDE2D", // 😭
            "\uD83D\uDE33", // 😳
            "\uD83E\uDD74", // 🤔
            "\uD83D\uDE20"  // 😠
        )
    }
}

@Composable
private fun EmojiPickerBottomSheet(
    emojis: ImmutableList<String>,
    selectedEmoji: Int,
    onEmojiSelected: (Int) -> Unit
) {
    var selectedEmojiTemp by remember {
        mutableIntStateOf(selectedEmoji)
    }
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = DefaultRoundedShape,
        color = LocalWitMeTheme.colors.white
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(Res.string.average_emoji),
                style = LocalWitMeTheme.typography.medium20,
                color = LocalWitMeTheme.colors.primary400,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp),
                columns = GridCells.Adaptive(minSize = 64.dp)
            ) {
                items(emojis.size) { index ->
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(CircleShape)
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(
                                if (index == selectedEmojiTemp)
                                    LocalWitMeTheme.colors.primary200
                                else
                                    Color.Transparent
                            )
                            .clickable {
                                onEmojiSelected(index)
                                selectedEmojiTemp = index
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = emojis[index],
                            fontSize = 42.sp
                        )
                    }
                }
            }
        }
    }
}