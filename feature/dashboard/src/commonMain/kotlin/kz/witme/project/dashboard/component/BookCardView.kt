package kz.witme.project.dashboard.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import kz.witme.project.common_ui.extension.clickableWithoutRipple
import kz.witme.project.common_ui.image.getImageUrl
import kz.witme.project.common_ui.theme.LinearGradient
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.pluralStringResource
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.ic_current_status
import witmekmp.core.common_ui.generated.resources.ic_date
import witmekmp.core.common_ui.generated.resources.ic_notes
import witmekmp.core.common_ui.generated.resources.ic_timer
import witmekmp.core.common_ui.generated.resources.notes_amount

@Composable
internal fun BookCardView(
    id: String,
    imageUrl: String?,
    name: String,
    date: String?,
    status: String,
    notes: Int,
    modifier: Modifier = Modifier,
    onTimerClick: (bookId: String) -> Unit = {},
    onBookClick: () -> Unit = {}
) {
    ElevatedCard(
        modifier = modifier
            .padding(vertical = 4.dp)
            .clickableWithoutRipple(onClick = onBookClick),
        colors = CardDefaults.cardColors(containerColor = LocalWitMeTheme.colors.white),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(size = 16.dp)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(color = LocalWitMeTheme.colors.primary200)
        )
        Column(
            modifier = Modifier.padding(horizontal = 14.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = name,
                style = LocalWitMeTheme.typography.medium20,
                color = LocalWitMeTheme.colors.secondary500
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                AsyncImage(
                    model = getImageUrl(imageUrl),
                    contentDescription = "Atomic Habits",
                    contentScale = ContentScale.Crop,
                    placeholder = ColorPainter(LocalWitMeTheme.colors.secondary300),
                    error = ColorPainter(LocalWitMeTheme.colors.secondary300),
                    fallback = ColorPainter(LocalWitMeTheme.colors.secondary300),
                    modifier = Modifier
                        .weight(0.4f)
                        .wrapContentHeight()
                        .padding(bottom = 8.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.width(14.dp))
                Column(
                    modifier = Modifier.weight(0.6f)
                ) {
                    if (!date.isNullOrBlank()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.ic_date),
                                contentDescription = "Date",
                                tint = LocalWitMeTheme.colors.primary400
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = date,
                                style = LocalWitMeTheme.typography.regular16,
                                color = LocalWitMeTheme.colors.secondary400
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_current_status),
                            contentDescription = "Reading",
                            tint = LocalWitMeTheme.colors.primary400
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = status,
                            style = LocalWitMeTheme.typography.regular16,
                            color = LocalWitMeTheme.colors.secondary400
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_notes),
                            contentDescription = "Notes",
                            tint = LocalWitMeTheme.colors.primary400
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = pluralStringResource(
                                resource = Res.plurals.notes_amount,
                                quantity = notes,
                                notes
                            ),
                            style = LocalWitMeTheme.typography.regular16,
                            color = LocalWitMeTheme.colors.secondary400
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .clip(RoundedCornerShape(4.dp))
                            .background(brush = LinearGradient)
                            .clickableWithoutRipple { onTimerClick(id) }
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_timer),
                            contentDescription = "Timer",
                            modifier = Modifier.padding(vertical = 12.dp, horizontal = 20.dp),
                            tint = LocalWitMeTheme.colors.white
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}