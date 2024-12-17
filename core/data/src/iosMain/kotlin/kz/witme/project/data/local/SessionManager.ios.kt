package kz.witme.project.data.local

import kz.witme.project.data.util.Constants.ACCESS
import kz.witme.project.data.util.Constants.REFRESH
import platform.Foundation.NSUserDefaults

//todo try russolf library that already has interop with objective-c keychain platform
actual class SessionManager {

    private val userDefaults: NSUserDefaults = NSUserDefaults.standardUserDefaults

    actual fun getAccessToken(): String = userDefaults.stringForKey(ACCESS) ?: ""

    actual fun setAccessToken(token: String) {
        with(userDefaults) {
            setObject(token, ACCESS)
            synchronize()
        }
    }

    actual fun getRefreshToken(): String = userDefaults.stringForKey(REFRESH) ?: ""

    actual fun setRefreshToken(token: String) {
        with(userDefaults) {
            setObject(token, REFRESH)
            synchronize()
        }
    }

    actual fun clearAccessToken() {
        with(userDefaults) {
            removeObjectForKey(ACCESS)
            synchronize()
        }
    }

    actual fun clearRefreshToken() {
        with(userDefaults) {
            removeObjectForKey(REFRESH)
            synchronize()
        }
    }
}