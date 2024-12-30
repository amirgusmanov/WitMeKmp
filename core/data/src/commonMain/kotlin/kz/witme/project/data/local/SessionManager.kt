package kz.witme.project.data.local

expect class SessionManager {

    fun getAccessToken(): String

    fun setAccessToken(token: String)

    fun getRefreshToken(): String

    fun setRefreshToken(token: String)

    fun clearAccessToken()

    fun clearRefreshToken()
}