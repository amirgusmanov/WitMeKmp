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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import kz.witme.project.common_ui.theme.DefaultRoundedShape
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.ic_camera
import witmekmp.core.common_ui.generated.resources.ic_picture
import witmekmp.core.common_ui.generated.resources.make_from_camera
import witmekmp.core.common_ui.generated.resources.pick_from_gallery

class PhotoPickerOptionBottomSheetScreen(
    private val onCameraOptionChoose: () -> Unit,
    private val onGalleryOptionChoose: () -> Unit
) : Screen {

    @Composable
    override fun Content() {
        PhotoPickerOptionBottomSheetContent(
            onCameraOptionChoose = onCameraOptionChoose,
            onGalleryOptionChoose = onGalleryOptionChoose
        )
    }
}

@Composable
private fun PhotoPickerOptionBottomSheetContent(
    onCameraOptionChoose: () -> Unit,
    onGalleryOptionChoose: () -> Unit
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
            ImageSourceButton(
                kind = ImageSourceButtonKind.Gallery,
                onClick = onGalleryOptionChoose,
                modifier = Modifier.weight(1f)
            )
            ImageSourceButton(
                kind = ImageSourceButtonKind.Camera,
                onClick = onCameraOptionChoose,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ImageSourceButton(
    kind: ImageSourceButtonKind,
    onClick: () -> Unit,
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
            .clickable(onClick = onClick),
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(
                when (kind) {
                    ImageSourceButtonKind.Gallery -> Res.drawable.ic_picture
                    ImageSourceButtonKind.Camera -> Res.drawable.ic_camera
                }
            ),
            contentDescription = "image source icon",
            tint = LocalWitMeTheme.colors.primary400,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = when (kind) {
                ImageSourceButtonKind.Gallery -> stringResource(Res.string.pick_from_gallery)
                ImageSourceButtonKind.Camera -> stringResource(Res.string.make_from_camera)
            },
            color = LocalWitMeTheme.colors.primary400,
            style = LocalWitMeTheme.typography.medium16
        )
    }
}

private enum class ImageSourceButtonKind {
    Gallery,
    Camera
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