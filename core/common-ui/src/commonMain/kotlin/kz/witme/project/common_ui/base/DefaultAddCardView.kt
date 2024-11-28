package kz.witme.project.common_ui.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import kz.witme.project.common_ui.theme.WitMeTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DefaultAddCardView(
    modifier: Modifier = Modifier,
    text: String? = null,
    //todo research how to work with images
//    photoUri: Uri? = null,
    onClick: () -> Unit = {},
    minHeight: Dp = 190.dp,
    maxHeight: Dp = 195.dp,
) {
    Card(
        modifier = modifier
            .widthIn(min = 200.dp)
            .heightIn(min = minHeight, max = maxHeight),
        contentColor = LocalWitMeTheme.colors.white,
        elevation = 1.dp,
        shape = RoundedCornerShape(size = 16.dp)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(color = LocalWitMeTheme.colors.primary200)
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

//            AddCardViewImage(
//                onClick = onClick,
//                photoUri = photoUri
//            )
        }
    }
}

//@Composable
//internal fun AddCardViewImage(
//    modifier: Modifier = Modifier,
//    onClick: () -> Unit,
//    photoUri: Uri?
//) {
//    val painterResource = if (photoUri == null) {
//        painterResource(id = R.drawable.ic_add)
//    } else {
//        rememberAsyncImagePainter(
//            ImageRequest
//                .Builder(LocalContext.current)
//                .data(data = photoUri)
//                .build()
//        )
//    }
//
//    Row(modifier = modifier) {
//        Column(
//            modifier = Modifier.weight(1f),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Image(
//                painter = painterResource(R.drawable.ic_plus_4),
//                contentDescription = null
//            )
//            Spacer(Modifier.weight(0.5f))
//            Image(
//                painter = painterResource(R.drawable.ic_plus_3),
//                contentDescription = null,
//                modifier = Modifier.padding(end = 16.dp)
//            )
//            Spacer(Modifier.weight(0.5f))
//            Image(
//                painter = painterResource(R.drawable.ic_plus_2),
//                contentDescription = null,
//                modifier = Modifier.padding(start = 16.dp)
//            )
//            Spacer(Modifier.weight(0.5f))
//            Image(
//                painter = painterResource(R.drawable.ic_plus_1),
//                contentDescription = null,
//                modifier = Modifier.padding(end = 16.dp)
//            )
//        }
//        Image(
//            painter = painterResource,
//            contentDescription = null,
//            contentScale = if (photoUri == null) {
//                ContentScale.Fit
//            } else {
//                ContentScale.Crop
//            },
//            modifier = Modifier
//                .weight(1f)
//                .clickable(onClick = onClick)
//                .clip(
//                    RoundedCornerShape(
//                        size = if (photoUri == null) {
//                            0.dp
//                        } else {
//                            8.dp
//                        }
//                    )
//                )
//        )
//        Column(
//            modifier = Modifier.weight(1f),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Image(
//                painter = painterResource(R.drawable.ic_plus_4),
//                contentDescription = null,
//                modifier = Modifier.graphicsLayer(scaleX = -1f)
//            )
//            Spacer(Modifier.weight(0.5f))
//            Image(
//                painter = painterResource(R.drawable.ic_plus_3),
//                contentDescription = null,
//                modifier = Modifier
//                    .padding(start = 16.dp)
//                    .graphicsLayer(scaleX = -1f)
//            )
//            Spacer(Modifier.weight(0.5f))
//            Image(
//                painter = painterResource(R.drawable.ic_plus_2),
//                contentDescription = null,
//                modifier = Modifier
//                    .padding(end = 16.dp)
//                    .graphicsLayer(scaleX = -1f)
//            )
//            Spacer(Modifier.weight(0.5f))
//            Image(
//                painter = painterResource(R.drawable.ic_plus_1),
//                contentDescription = null,
//                modifier = Modifier
//                    .padding(start = 16.dp)
//                    .graphicsLayer(scaleX = -1f)
//            )
//        }
//    }
//}

@Preview
@Composable
private fun DefaultAddCardViewPreview() {
    WitMeTheme {
        DefaultAddCardView(
            text = "Добавить книгу",
            modifier = Modifier.padding(32.dp)
        )
    }
}