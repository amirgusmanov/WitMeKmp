@file:OptIn(ExperimentalMaterialApi::class)

package kz.witme.project

import androidx.compose.animation.animateContentSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.transitions.SlideTransition
import kz.witme.project.common_ui.theme.DefaultRoundedShape
import kz.witme.project.common_ui.theme.WitMeTheme
import kz.witme.project.navigation.Destination

/**
 * todo
 * 1. make something with previews
 * 2. research about what can replace main activity viewmodel in cmp
 * 3. refactor navigator work, use different navigator when bottom nav is implemented
 */
@Composable
fun App() {
    WitMeTheme {
        BottomSheetNavigator(
            modifier = Modifier.animateContentSize(),
            sheetShape = DefaultRoundedShape,
            skipHalfExpanded = true
        ) {
            Navigator(screen = rememberScreen(Destination.Onboarding)) {
                SlideTransition(navigator = it)
            }
        }
    }
}