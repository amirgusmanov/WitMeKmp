package kz.witme.project.common_ui.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
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
import androidx.compose.ui.unit.dp
import kz.witme.project.common_ui.extension.clickableWithPressedState
import kz.witme.project.common_ui.extension.textBrush
import kz.witme.project.common_ui.theme.LinearGradient
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import org.jetbrains.compose.resources.painterResource
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
            currentOnClick?.let { onClick ->
                iconGradient?.let {
                    Icon(
                        painter = painterResource(Res.drawable.ic_back),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .clickableWithPressedState(onClick = onClick)
                            .graphicsLayer(alpha = 0.99f)
                            .drawWithCache {
                                onDrawWithContent {
                                    drawContent()
                                    drawRect(LinearGradient, blendMode = BlendMode.SrcAtop)
                                }
                            }
                    )
                } ?: run {
                    Icon(
                        painter = painterResource(Res.drawable.ic_back),
                        contentDescription = null,
                        tint = iconTint,
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .clickableWithPressedState(onClick = onClick)
                    )
                }
            }

            titleGradient?.let {
                Text(
                    text = toolbarTitle,
                    style = titleStyle,
                    modifier = Modifier.textBrush(LinearGradient)
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