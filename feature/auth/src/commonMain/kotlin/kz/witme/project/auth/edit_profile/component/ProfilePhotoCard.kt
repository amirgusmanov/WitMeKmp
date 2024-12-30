@file:OptIn(ExperimentalFoundationApi::class)

package kz.witme.project.auth.edit_profile.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kz.witme.project.common.extension.isNull
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.ic_add_profile_photo
import witmekmp.core.common_ui.generated.resources.profile_photo

@Composable
internal fun ProfilePhotoCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    chosenPhoto: ImageBitmap? = null,
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors().copy(containerColor = LocalWitMeTheme.colors.white),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(size = 16.dp)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(color = LocalWitMeTheme.colors.primary200)
        )
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.profile_photo),
                style = LocalWitMeTheme.typography.medium24,
                color = LocalWitMeTheme.colors.black,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(22.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (chosenPhoto.isNull()) {
                    Image(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(100))
                            .combinedClickable(
                                onClick = onClick,
                                onLongClick = onLongClick
                            ),
                        contentScale = ContentScale.Crop,
                        painter = painterResource(Res.drawable.ic_add_profile_photo),
                        contentDescription = null
                    )
                } else {
                    chosenPhoto?.let {
                        Image(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(100))
                                .combinedClickable(
                                    onClick = onClick,
                                    onLongClick = onLongClick
                                ),
                            contentScale = ContentScale.Crop,
                            bitmap = it,
                            contentDescription = null
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(22.dp))
        }
    }
}