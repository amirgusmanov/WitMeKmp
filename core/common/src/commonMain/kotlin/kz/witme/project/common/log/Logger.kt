package kz.witme.project.common.log

const val EXCEPTION_TAG = "exception_tag"

expect object Logger {
    fun d(tag: String, message: String)
    fun e(tag: String = EXCEPTION_TAG, message: String)
}