package kz.witme.project.common.extension

public inline fun tryToCall(block: () -> Unit) {
    try {
        block()
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}

public inline fun <T> tryToGet(block: () -> T?): T? {
    return try {
        block()
    } catch (e: Throwable) {
        e.printStackTrace()
        null
    }
}