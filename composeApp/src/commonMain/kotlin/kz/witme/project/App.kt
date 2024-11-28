package kz.witme.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import kz.witme.project.common_ui.theme.WitMeTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import witmekmp.composeapp.generated.resources.Res
import witmekmp.composeapp.generated.resources.compose_multiplatform

/**
 * todo
 * 1. make something with previews
 * 2. add koin di
 * 3. add voyager cmp support
 * 4. research about what can replace main activity viewmodel in cmp
 * 5. add data module with ktor networking support with (darwin and okhttp)
 * 6. add network call wrapers to handle network exception etc
 */
@Composable
@Preview
fun App() {
    WitMeTheme {
        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.safeDrawing),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { showContent = !showContent }
            ) {
                Text(
                    "Click me!",
                    style = LocalWitMeTheme.typography.regular12,
                    color = LocalWitMeTheme.colors.link200
                )
            }
            AnimatedVisibility(showContent) {
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text(
                        "Compose",
                        style = LocalWitMeTheme.typography.regular12,
                        color = LocalWitMeTheme.colors.link200
                    )
                }
            }
        }
    }
}