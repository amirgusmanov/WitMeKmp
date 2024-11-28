package kz.witme.project.common.extension

public inline fun <reified T : Enum<T>> String?.toEnum(): T? =
    if (this.isNullOrBlank()) {
        null
    } else {
        tryToGet { enumValueOf<T>(this) }
    }