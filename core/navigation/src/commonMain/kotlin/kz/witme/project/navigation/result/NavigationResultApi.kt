package kz.witme.project.navigation.result

import cafe.adriel.voyager.navigator.Navigator

private val screenResults = hashMapOf<String, Any?>()

fun <T: Any> Navigator.getScreenResult(key: String): T? {
    val result = screenResults[key]
    screenResults[key] = null

    return result as? T
}

fun Navigator.setScreenResult(key: String, result: Any) {
    screenResults[key] = result
}