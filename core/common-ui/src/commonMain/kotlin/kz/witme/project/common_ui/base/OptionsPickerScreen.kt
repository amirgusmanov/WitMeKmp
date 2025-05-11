package kz.witme.project.common_ui.base

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import kz.witme.project.common_ui.theme.DefaultRoundedShape
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

class OptionsPickerScreen(
    private val options: List<OptionItem>
) : Screen {

    @Composable
    override fun Content() {
        OptionPickerScreenContent(options = options)
    }
}

@Stable
data class OptionItem(
    val icon: IconType,
    val labelRes: StringResource,
    val onClick: () -> Unit
) {
    sealed interface IconType {
        data class VectorIcon(
            val vector: ImageVector
        ) : IconType

        data class ResourceIcon(
            val res: DrawableResource
        ) : IconType
    }
}

@Composable
private fun OptionPickerScreenContent(
    options: List<OptionItem>
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = DefaultRoundedShape,
        color = LocalWitMeTheme.colors.white
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            options.forEach {
                ImageSourceButton(
                    optionItem = it,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun ImageSourceButton(
    optionItem: OptionItem,
    modifier: Modifier = Modifier
) {
    val cornerRadius = 12.dp
    Column(
        modifier = modifier
            .height(115.dp)
            .clip(RoundedCornerShape(cornerRadius))
            .dashBorder(
                cornerRadius = cornerRadius,
                borderColor = LocalWitMeTheme.colors.primary400
            )
            .clickable(onClick = optionItem.onClick),
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (optionItem.icon) {
            is OptionItem.IconType.ResourceIcon -> {
                Icon(
                    painter = painterResource(optionItem.icon.res),
                    contentDescription = "image source icon",
                    tint = LocalWitMeTheme.colors.primary400,
                    modifier = Modifier.size(24.dp)
                )
            }

            is OptionItem.IconType.VectorIcon -> {
                Icon(
                    imageVector = optionItem.icon.vector,
                    contentDescription = "image source icon",
                    tint = LocalWitMeTheme.colors.primary400,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        Text(
            text = stringResource(optionItem.labelRes),
            color = LocalWitMeTheme.colors.primary400,
            style = LocalWitMeTheme.typography.medium16,
            textAlign = TextAlign.Center
        )
    }
}

private fun Modifier.dashBorder(
    cornerRadius: Dp,
    borderColor: Color
): Modifier = drawWithContent {
    drawContent()
    drawRoundRect(
        color = borderColor,
        topLeft = Offset.Zero,
        size = Size(size.width, size.height),
        cornerRadius = CornerRadius(cornerRadius.toPx()),
        style = Stroke(
            width = 1.dp.toPx(),
            pathEffect = PathEffect.dashPathEffect(
                intervals = floatArrayOf(6.dp.toPx(), 6.dp.toPx()),
                phase = 0f
            )
        )
    )
}