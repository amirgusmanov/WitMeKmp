package kz.witme.project.web_view.navigation

import cafe.adriel.voyager.core.registry.screenModule
import kz.witme.project.navigation.Destination
import kz.witme.project.web_view.screen.WebViewScreen

val webViewNavigationModule = screenModule {
    register<Destination.WebViewScreen> { provider ->
        WebViewScreen(
            title = provider.title,
            url = provider.link
        )
    }
}