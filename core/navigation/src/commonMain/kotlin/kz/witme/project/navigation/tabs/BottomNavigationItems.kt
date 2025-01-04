package kz.witme.project.navigation.tabs

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import org.jetbrains.compose.resources.painterResource
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.ic_home_selected
import witmekmp.core.common_ui.generated.resources.ic_profile_selected
import witmekmp.core.common_ui.generated.resources.ic_timer_tab

@Suppress("NonSkippableComposable")
@Composable
fun FabTimer(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    FloatingActionButton(
        shape = CircleShape,
        onClick = { tabNavigator.current = tab },
        backgroundColor = LocalWitMeTheme.colors.primary400
    ) {
        tab.options.icon?.let { painter ->
            Icon(
                painter = painter,
                contentDescription = tab.options.title,
                tint = LocalWitMeTheme.colors.white
            )
        }
    }
}

@Suppress("NonSkippableComposable")
@Composable
fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    BottomNavigationItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = {
            if (tabNavigator.current == tab) {
                Icon(
                    painter = painterResource(
                        when (tab.options.index) {
                            0u.toUShort() -> Res.drawable.ic_home_selected
                            2u.toUShort() -> Res.drawable.ic_profile_selected
                            else -> Res.drawable.ic_timer_tab
                        }
                    ),
                    tint = LocalWitMeTheme.colors.primary400,
                    contentDescription = tab.options.title
                )
            } else {
                tab.options.icon?.let {
                    Icon(
                        painter = it,
                        contentDescription = tab.options.title,
                        tint = LocalWitMeTheme.colors.secondary400
                    )
                }
            }
        },
        label = {
            Text(
                text = tab.options.title,
                style = LocalWitMeTheme.typography.regular12,
                color = if (tabNavigator.current == tab) {
                    LocalWitMeTheme.colors.primary400
                } else {
                    LocalWitMeTheme.colors.secondary400
                }
            )
        }
    )
}