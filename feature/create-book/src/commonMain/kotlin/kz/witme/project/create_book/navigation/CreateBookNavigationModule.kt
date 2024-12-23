package kz.witme.project.create_book.navigation

import cafe.adriel.voyager.core.registry.screenModule
import kz.witme.project.create_book.main.CreateBookScreen
import kz.witme.project.create_book.main_status.CreateBookStatusScreen
import kz.witme.project.navigation.Destination

val createBookNavigationModule = screenModule {
    register<Destination.CreateBook> { CreateBookScreen() }
    register<Destination.CreateStatusBook> { CreateBookStatusScreen() }
}