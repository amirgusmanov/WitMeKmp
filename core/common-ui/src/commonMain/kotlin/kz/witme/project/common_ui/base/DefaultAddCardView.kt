package kz.witme.project.common_ui.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kz.witme.project.common_ui.extension.clickableWithoutRipple
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import org.jetbrains.compose.resources.painterResource
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.ic_add
import witmekmp.core.common_ui.generated.resources.ic_plus_1
import witmekmp.core.common_ui.generated.resources.ic_plus_2
import witmekmp.core.common_ui.generated.resources.ic_plus_3
import witmekmp.core.common_ui.generated.resources.ic_plus_4

@Composable
fun DefaultAddCardView(
    modifier: Modifier = Modifier,
    text: String? = null,
    photo: ImageBitmap? = null,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier,
        contentColor = LocalWitMeTheme.colors.white,
        elevation = 2.dp,
        shape = RoundedCornerShape(size = 16.dp)
    ) {
        Box {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .background(color = LocalWitMeTheme.colors.primary200)
                    .align(Alignment.TopCenter)
            )
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(18.dp))
                text?.let {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = it,
                        style = LocalWitMeTheme.typography.medium24,
                        color = LocalWitMeTheme.colors.black,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                }
                AddCardViewImage(onClick = onClick, photo = photo)
            }
        }
    }
}

@Composable
internal fun AddCardViewImage(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    photo: ImageBitmap?
) {
    val painterResource = painterResource(Res.drawable.ic_add)

    Row(modifier = modifier) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_plus_4),
                contentDescription = null
            )
            Spacer(Modifier.weight(0.5f))
            Image(
                painter = painterResource(Res.drawable.ic_plus_3),
                contentDescription = null,
                modifier = Modifier.padding(end = 16.dp)
            )
            Spacer(Modifier.weight(0.5f))
            Image(
                painter = painterResource(Res.drawable.ic_plus_2),
                contentDescription = null,
                modifier = Modifier.padding(start = 16.dp)
            )
            Spacer(Modifier.weight(0.5f))
            Image(
                painter = painterResource(Res.drawable.ic_plus_1),
                contentDescription = null,
                modifier = Modifier.padding(end = 16.dp)
            )
        }
        if (photo == null) {
            Image(
                painter = painterResource,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .weight(1f)
                    .clickableWithoutRipple(onClick = onClick)
                    .clip(RoundedCornerShape(size = 0.dp))
            )
        } else {
            Image(
                bitmap = photo,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(1f)
                    .clickableWithoutRipple(onClick = onClick)
                    .clip(RoundedCornerShape(size = 8.dp))
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_plus_4),
                contentDescription = null,
                modifier = Modifier.graphicsLayer(scaleX = -1f)
            )
            Spacer(Modifier.weight(0.5f))
            Image(
                painter = painterResource(Res.drawable.ic_plus_3),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .graphicsLayer(scaleX = -1f)
            )
            Spacer(Modifier.weight(0.5f))
            Image(
                painter = painterResource(Res.drawable.ic_plus_2),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .graphicsLayer(scaleX = -1f)
            )
            Spacer(Modifier.weight(0.5f))
            Image(
                painter = painterResource(Res.drawable.ic_plus_1),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .graphicsLayer(scaleX = -1f)
            )
        }
    }
}