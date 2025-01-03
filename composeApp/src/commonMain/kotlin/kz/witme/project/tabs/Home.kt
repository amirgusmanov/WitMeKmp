package kz.witme.project.tabs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kz.witme.project.common.extension.tryToUpdate
import kz.witme.project.navigation.Destination
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import witmekmp.composeapp.generated.resources.Res
import witmekmp.composeapp.generated.resources.home
import witmekmp.composeapp.generated.resources.ic_home

object Home : Tab, NavigatorEntryProvider {

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

    private val canPopFlow: StateFlow<Boolean> = MutableStateFlow(false)

    @Composable
    override fun Content() {
        Navigator(rememberScreen(Destination.Dashboard)) { navigator ->
            LaunchedEffect(navigator.canPop) {
                canPopFlow.tryToUpdate {
                    navigator.canPop
                }
            }
            SlideTransition(navigator)
        }
    }

    override fun isFirstInStack(): StateFlow<Boolean> = canPopFlow
}