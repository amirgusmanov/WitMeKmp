package kz.witme.project.web_view.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import kz.witme.project.common_ui.base.DefaultToolbar
import kz.witme.project.common_ui.screen.toolbarPaddings
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import kz.witme.project.web_view.component.WebView

class WebViewScreen(
    private val title: String,
    private val url: String
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            contentWindowInsets = WindowInsets.statusBars,
            containerColor = LocalWitMeTheme.colors.white,
            topBar = {
                DefaultToolbar(
                    modifier = Modifier
                        .toolbarPaddings()
                        .padding(bottom = 16.dp),
                    toolbarTitle = title,
                    titleColor = LocalWitMeTheme.colors.primary400,
                    iconTint = LocalWitMeTheme.colors.primary400,
                    toolbarBackgroundColor = LocalWitMeTheme.colors.white,
                    titleStyle = LocalWitMeTheme.typography.medium20,
                    onBackClick = {
                        navigator?.pop()
                    }
                )
            }
        ) { contentPaddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPaddingValues)
            ) {
                WebView(url = url)
            }
        }
    }
}