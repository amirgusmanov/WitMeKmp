package kz.witme.project

import androidx.compose.ui.window.ComposeUIViewController
import kz.witme.project.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) {
    App()
}