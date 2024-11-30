package kz.witme.project.data.network

sealed interface DataError : Error {
    enum class Remote : DataError {
        UNAUTHORIZED,
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER,
        SERIALIZATION,
        UNKNOWN,
        NPE
    }
}