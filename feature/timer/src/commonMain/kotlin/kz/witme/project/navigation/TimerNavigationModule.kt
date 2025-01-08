package kz.witme.project.navigation

import cafe.adriel.voyager.core.registry.screenModule
import kotlinx.collections.immutable.toImmutableList
import kz.witme.project.timer.TimerScreen
import kz.witme.project.timer_details.TimerDetailsScreen

val timerNavigationModule = screenModule {
    register<Destination.Timer> { destination ->
        TimerScreen(
            bookId = destination.bookId,
            isNavigatedFromTabs = destination.isNavigatedFromTabs
        )
    }
    register<Destination.TimerDetails> { provider ->
        TimerDetailsScreen(
            book = provider.book,
            seconds = provider.seconds,
            notes = provider.notes
        )
    }
}