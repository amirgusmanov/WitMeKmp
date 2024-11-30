package kz.witme.project.data.network

import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kz.witme.project.common.log.Logger

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): RequestResult<T, DataError.Remote> {
    val response = try {
        execute()
    } catch (npe: NullPointerException) {
        Logger.e(message = npe.toString())
        return RequestResult.Error(DataError.Remote.NPE)
    } catch (e: SocketTimeoutException) {
        Logger.e(message = e.toString())
        return RequestResult.Error(DataError.Remote.REQUEST_TIMEOUT)
    } catch (e: UnresolvedAddressException) {
        Logger.e(message = e.toString())
        return RequestResult.Error(DataError.Remote.NO_INTERNET)
    } catch (throwable: Throwable) {
        Logger.e(message = throwable.toString())
        return RequestResult.Error(DataError.Remote.UNKNOWN)
    }

    return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): RequestResult<T, DataError.Remote> = when (response.status.value) {
    in 200..299 -> {
        try {
            RequestResult.Success(response.body<T>())
        } catch (e: NoTransformationFoundException) {
            RequestResult.Error(DataError.Remote.SERIALIZATION)
        }
    }

    401 -> RequestResult.Error(DataError.Remote.UNAUTHORIZED)
    408 -> RequestResult.Error(DataError.Remote.REQUEST_TIMEOUT)
    429 -> RequestResult.Error(DataError.Remote.TOO_MANY_REQUESTS)
    in 500..599 -> RequestResult.Error(DataError.Remote.SERVER)
    else -> RequestResult.Error(DataError.Remote.UNKNOWN)
}

/**
 * to display error text in snackbar or error dialog
 */
fun DataError.toText(): String = when (this) {
    DataError.Remote.REQUEST_TIMEOUT -> ERROR_REQUEST_TIMEOUT
    DataError.Remote.TOO_MANY_REQUESTS -> ERROR_TOO_MANY_REQUESTS
    DataError.Remote.NO_INTERNET -> ERROR_NO_INTERNET
    DataError.Remote.SERIALIZATION -> ERROR_SERIALIZATION
    else -> ERROR_UNKNOWN
}

//todo: separate this into strings.xml
private const val ERROR_UNKNOWN = "Упс, что-то пошло не так"
private const val ERROR_REQUEST_TIMEOUT = "Время запроса истекло"
private const val ERROR_NO_INTERNET = "Не удалось соединиться с сервером, проверьте подключение"
private const val ERROR_TOO_MANY_REQUESTS = "Слишком много запросов"
private const val ERROR_SERIALIZATION = "Не удалось обработать данные"