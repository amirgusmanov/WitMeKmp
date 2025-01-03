package kz.witme.project.tabs

import kotlinx.coroutines.flow.StateFlow

interface NavigatorEntryProvider {

    fun isFirstInStack(): StateFlow<Boolean>
}