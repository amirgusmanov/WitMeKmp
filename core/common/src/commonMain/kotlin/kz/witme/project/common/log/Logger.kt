package kz.witme.project.common.log

expect object Logger {
    fun d(tag: String, message: String)
    fun e(tag: String, message: String)
}