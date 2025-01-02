package kz.witme.project

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.transitions.SlideTransition
import kz.witme.project.common_ui.extension.collectAsStateWithLifecycle
import kz.witme.project.common_ui.screen.bottomNavigationPaddings
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import kz.witme.project.common_ui.theme.WitMeTheme
import kz.witme.project.data.network.HttpClientFactory
import kz.witme.project.navigation.Destination
import kz.witme.project.tabs.FabTimer
import kz.witme.project.tabs.Home
import kz.witme.project.tabs.Profile
import kz.witme.project.tabs.TabNavigationItem
import kz.witme.project.tabs.Timer

@Composable
fun App() {
    WitMeTheme {
        val navigateFlow by HttpClientFactory.navigateFlow.collectAsStateWithLifecycle()
        when (navigateFlow) {
            HttpClientFactory.NavigateFlow.LoginFlow -> LoginFlow()
            HttpClientFactory.NavigateFlow.TabsFlow -> TabsFlow()
            HttpClientFactory.NavigateFlow.SplashFlow -> SplashFlow()
        }
    }
}

@Composable
private fun TabsFlow() {
    TabNavigator(tab = Home) {
        Scaffold(
            content = {
                CurrentTab()
            },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true,
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier.clip(
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp
                        )
                    ),
                    cutoutShape = CircleShape,
                    backgroundColor = LocalWitMeTheme.colors.bottomNav,
                    windowInsets = WindowInsets(bottom = bottomNavigationPaddings()),
                    elevation = 16.dp
                ) {
                    TabNavigationItem(Home)
                    TabNavigationItem(Profile)
                }
            },
            floatingActionButton = {
                FabTimer(Timer)
            }
        )
    }
}

@Composable
private fun SplashFlow() {
    Navigator(screen = rememberScreen(Destination.Splash))
}

@Composable
private fun LoginFlow() {
    Navigator(screen = rememberScreen(Destination.Onboarding)) {
        SlideTransition(it)
    }
}