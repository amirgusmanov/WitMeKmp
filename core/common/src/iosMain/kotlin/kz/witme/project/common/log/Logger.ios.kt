package kz.witme.project.common.log

import platform.Foundation.NSLog

actual object Logger {
    actual fun d(tag: String, message: String) {
        NSLog(
            "[DEBUG]",
            tag,
            message
        )
    }

    actual fun e(tag: String, message: String) {
        NSLog(
            "[ERROR]",
            tag,
            message
        )
    }
}