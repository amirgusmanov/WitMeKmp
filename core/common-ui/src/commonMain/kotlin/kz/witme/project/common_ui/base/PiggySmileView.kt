package kz.witme.project.common_ui.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import kz.witme.project.common_ui.theme.WitMeTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.piggy_smile

@Composable
fun PiggySmileView(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(80.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PiggyEye()
            PiggyEye()
        }
        Spacer(modifier = Modifier.height(9.dp))
        Image(
            painter = painterResource(Res.drawable.piggy_smile),
            contentDescription = null,
            modifier = Modifier.size(50.dp),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
private fun PiggyEye() {
    Box(
        modifier = Modifier
            .size(18.dp)
            .clip(CircleShape)
            .background(LocalWitMeTheme.colors.white),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(11.dp)
                .offset(3.5.dp, 2.5.dp)
                .clip(CircleShape)
                .background(LocalWitMeTheme.colors.primary400)
        )
    }
}

@Preview
@Composable
private fun PiggySmilePreview() {
    WitMeTheme {
        PiggySmileView()
    }
}