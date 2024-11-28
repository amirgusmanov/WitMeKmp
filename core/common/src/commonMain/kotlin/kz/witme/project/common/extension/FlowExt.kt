package kz.witme.project.common.extension

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

fun <T> StateFlow<T>.tryToUpdate(function: (T) -> T) {
    if (this is MutableStateFlow<T>) {
        this.update(function)
    }
}

fun <T> StateFlow<T>.tryToUpdate(value: T) {
    if (this is MutableStateFlow<T>) {
        this.update { value }
    }
}