package kz.witme.project.common_ui.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import kz.witme.project.common_ui.extension.textBrush
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import kz.witme.project.common_ui.theme.TextBrush
import kz.witme.project.common_ui.theme.WitMeTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.ic_back

@Composable
fun DefaultToolbar(
    modifier: Modifier = Modifier,
    toolbarTitle: String,
    titleStyle: TextStyle = LocalWitMeTheme.typography.semiBold32,
    titleColor: Color = LocalWitMeTheme.colors.white,
    iconTint: Color = LocalWitMeTheme.colors.white,
    titleGradient: Brush? = null,
    iconGradient: Brush? = null,
    toolbarBackgroundColor: Color = Color.Transparent,
    onBackClick: (() -> Unit)? = null,
) {
    val currentOnClick by rememberUpdatedState(onBackClick)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(toolbarBackgroundColor)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            currentOnClick?.let {
                IconButton(onClick = it) {
                    iconGradient?.let {
                        Icon(
                            painter = painterResource(Res.drawable.ic_back),
                            contentDescription = null,
                            modifier = Modifier
                                .graphicsLayer(alpha = 0.99f)
                                .drawWithCache {
                                    onDrawWithContent {
                                        drawContent()
                                        drawRect(TextBrush, blendMode = BlendMode.SrcAtop)
                                    }
                                }
                        )
                    } ?: run {
                        Icon(
                            painter = painterResource(Res.drawable.ic_back),
                            contentDescription = null,
                            tint = iconTint
                        )
                    }
                }
            }

            titleGradient?.let {
                Text(
                    text = toolbarTitle,
                    style = titleStyle,
                    modifier = Modifier.textBrush(TextBrush)
                )
            } ?: run {
                Text(
                    text = toolbarTitle,
                    style = titleStyle,
                    color = titleColor,
                )
            }
        }
    }
}

@Preview
@Composable
private fun DefaultToolbarPreview() {
    WitMeTheme {
        DefaultToolbar(toolbarTitle = "Сейчас читаю", onBackClick = {})
    }
}