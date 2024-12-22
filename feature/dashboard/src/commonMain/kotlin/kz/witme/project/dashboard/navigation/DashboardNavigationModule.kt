package kz.witme.project.dashboard.navigation

import cafe.adriel.voyager.core.registry.screenModule
import kz.witme.project.dashboard.DashboardScreen
import kz.witme.project.navigation.Destination

val dashboardNavigationModule = screenModule {
    register<Destination.Dashboard> { DashboardScreen() }
}