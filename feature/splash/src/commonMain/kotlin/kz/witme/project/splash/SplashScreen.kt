package kz.witme.project.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import kz.witme.project.common_ui.screen.getScreenWidth
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import org.jetbrains.compose.resources.painterResource
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.ic_launcher_foreground

class SplashScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel: SplashViewModel = koinScreenModel()
        val screenWidth: Dp = getScreenWidth()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = LocalWitMeTheme.colors.white),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size((screenWidth.value * 0.75).dp),
                painter = painterResource(Res.drawable.ic_launcher_foreground),
                contentDescription = "App logo"
            )
        }
    }
}