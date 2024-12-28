package kz.witme.project.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import kz.witme.project.common_ui.theme.DefaultRoundedShape
import kz.witme.project.common_ui.theme.LocalWitMeTheme

internal class BaseTimerBottomSheet(
    private val title: String,
    private val content: @Composable () -> Unit
) : Screen {

    @Composable
    override fun Content() {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = DefaultRoundedShape,
            color = LocalWitMeTheme.colors.white
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(
                    modifier = Modifier
                        .width(80.dp)
                        .height(5.dp)
                        .background(color = LocalWitMeTheme.colors.secondary300)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = title,
                    style = LocalWitMeTheme.typography.medium20,
                    color = LocalWitMeTheme.colors.primary400
                )
                Spacer(modifier = Modifier.height(24.dp))
                content()
            }
        }
    }
}