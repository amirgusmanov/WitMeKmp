package kz.witme.project.navigation.tabs

import kotlinx.coroutines.flow.StateFlow

interface NavigatorEntryProvider {

    fun isFirstInStack(): StateFlow<Boolean>
}