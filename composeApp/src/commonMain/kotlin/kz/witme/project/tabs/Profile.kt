@file:OptIn(ExperimentalMaterialApi::class)

package kz.witme.project.tabs

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import kz.witme.project.navigation.Destination
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import witmekmp.composeapp.generated.resources.Res
import witmekmp.composeapp.generated.resources.ic_profile
import witmekmp.composeapp.generated.resources.profile

object Profile : Tab {

    @Composable
    override fun Content() {
        BottomSheetNavigator(
            modifier = Modifier.animateContentSize(),
            sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            skipHalfExpanded = true
        ) {
            Navigator(rememberScreen(Destination.Profile)) {
                SlideTransition(it)
            }
        }
    }

    override val options: TabOptions
        @Composable
        get() {
            val icon = painterResource(Res.drawable.ic_profile)
            val title = stringResource(Res.string.profile)
            return remember {
                TabOptions(
                    index = 2u,
                    title = title,
                    icon = icon
                )
            }
        }
}