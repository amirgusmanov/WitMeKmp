package kz.witme.project.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import kz.witme.project.common_ui.theme.LocalWitMeTheme

class SplashScreen : Screen {

    @Composable
    override fun Content() {
        //todo implement ui
        val viewModel: SplashViewModel = koinScreenModel()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = LocalWitMeTheme.colors.primary200
                )
        )
    }
}