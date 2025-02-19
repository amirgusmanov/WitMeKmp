@file:OptIn(ExperimentalMaterialApi::class)

package kz.witme.project

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.transitions.SlideTransition
import kz.witme.project.common_ui.extension.collectAsStateWithLifecycle
import kz.witme.project.common_ui.screen.bottomNavigationPaddings
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import kz.witme.project.common_ui.theme.WitMeTheme
import kz.witme.project.data.network.HttpClientFactory
import kz.witme.project.navigation.Destination
import kz.witme.project.navigation.tabs.FabTimer
import kz.witme.project.navigation.tabs.Home
import kz.witme.project.navigation.tabs.Profile
import kz.witme.project.navigation.tabs.TabNavigationItem
import kz.witme.project.navigation.tabs.Timer

@Composable
fun App() {
    WitMeTheme {
        val navigateFlow by HttpClientFactory.navigateFlow.collectAsStateWithLifecycle()
        AnimatedContent(
            targetState = navigateFlow
        ) { flow ->
            when (flow) {
                HttpClientFactory.NavigateFlow.LoginFlow -> LoginFlow()
                HttpClientFactory.NavigateFlow.TabsFlow -> TabsFlow()
                HttpClientFactory.NavigateFlow.SplashFlow -> SplashFlow()
            }
        }
    }
}

@Composable
private fun TabsFlow() {
    BottomSheetNavigator(
        modifier = Modifier.animateContentSize(),
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        skipHalfExpanded = true
    ) {
        TabNavigator(tab = Home) { tabNavigator ->
            val isFirstInStack = when (val currentTab = tabNavigator.current) {
                is Home -> currentTab.isFirstInStack().collectAsStateWithLifecycle().value
                is Profile -> currentTab.isFirstInStack().collectAsStateWithLifecycle().value
                else -> true
            }.not()
            Scaffold(
                floatingActionButtonPosition = FabPosition.Center,
                isFloatingActionButtonDocked = true,
                bottomBar = {
                    AnimatedVisibility(
                        visible = isFirstInStack,
                        enter = expandVertically(),
                        exit = shrinkVertically()
                    ) {
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
                    }
                },
                floatingActionButton = {
                    if (isFirstInStack) {
                        FabTimer(Timer)
                    }
                },
                content = {
                    CurrentTab()
                }
            )
        }
    }
}

@Composable
private fun SplashFlow() {
    Navigator(screen = rememberScreen(Destination.Splash))
}

@Composable
private fun LoginFlow() {
    BottomSheetNavigator(
        modifier = Modifier.animateContentSize(),
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        skipHalfExpanded = true
    ) {
        Navigator(screen = rememberScreen(Destination.Onboarding)) {
            SlideTransition(it)
        }
    }
}