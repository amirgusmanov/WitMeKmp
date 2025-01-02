package kz.witme.project.tabs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import kz.witme.project.navigation.Destination
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import witmekmp.composeapp.generated.resources.Res
import witmekmp.composeapp.generated.resources.home
import witmekmp.composeapp.generated.resources.ic_home

object Home : Tab {

    @Composable
    override fun Content() {
        Navigator(rememberScreen(Destination.Dashboard)) {
            SlideTransition(it)
        }
    }

    override val options: TabOptions
        @Composable
        get() {
            val icon = painterResource(Res.drawable.ic_home)
            val title = stringResource(Res.string.home)
            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }
}