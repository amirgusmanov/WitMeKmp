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
import witmekmp.composeapp.generated.resources.Res
import witmekmp.composeapp.generated.resources.ic_timer_tab

object Timer : Tab {

    @Composable
    override fun Content() {
        Navigator(screen = rememberScreen(Destination.Timer())) {
            SlideTransition(it)
        }
    }

    override val options: TabOptions
        @Composable
        get() {
            val icon = painterResource(Res.drawable.ic_timer_tab)
            return remember {
                TabOptions(
                    index = 1u,
                    title = "Timer",
                    icon = icon
                )
            }
        }
}