package kz.witme.project.navigation.result

import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator

interface AppScreen : Screen {
    fun <T> onResult(obj: T)
}

fun <T> Navigator.popWithResult(obj: T) {
    val prev = if (items.size < 2) null else items[items.size - 2] as? AppScreen
    prev?.onResult(obj)
    pop()
}

fun <T> Navigator.popUntilRootWithResult(obj: T) {
    // Get the root screen (the first screen in the stack)
    val rootScreen = items.firstOrNull() as? AppScreen
    rootScreen?.onResult(obj)
    popUntilRoot()
}