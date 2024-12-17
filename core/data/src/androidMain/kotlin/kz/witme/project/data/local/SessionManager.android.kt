package kz.witme.project.data.local

import android.content.Context
import kz.witme.project.data.util.Constants.ACCESS
import kz.witme.project.data.util.Constants.REFRESH

actual class SessionManager(
    context: Context
) {
    private val sp = context.getSharedPreferences("session manager", Context.MODE_PRIVATE)
    private val editor = sp.edit()

    actual fun getAccessToken(): String = sp.getString(ACCESS, "") ?: ""

    actual fun setAccessToken(token: String) {
        editor.putString(ACCESS, token).apply()
    }

    actual fun getRefreshToken(): String = sp.getString(REFRESH, "") ?: ""

    actual fun setRefreshToken(token: String) {
        editor.putString(REFRESH, token).apply()
    }

    actual fun clearAccessToken() {
        editor.remove(ACCESS).apply()
    }

    actual fun clearRefreshToken() {
        editor.remove(REFRESH).apply()
    }
}