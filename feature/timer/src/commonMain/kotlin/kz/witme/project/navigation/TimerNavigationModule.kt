package kz.witme.project.navigation

import cafe.adriel.voyager.core.registry.screenModule
import kz.witme.project.timer.TimerScreen
import kz.witme.project.timer_details.TimerDetailsScreen

val timerNavigationModule = screenModule {
    register<Destination.Timer> { TimerScreen() }
    register<Destination.TimerDetails> { TimerDetailsScreen() }
}