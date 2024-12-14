package kz.witme.project.data.network

import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kz.witme.project.common.log.Logger

suspend inline fun <reified T> safeCall(
    execute: () -> T
): RequestResult<T, DataError.Remote> = try {
    RequestResult.Success(execute())
} catch (e: ClientRequestException) { // Handles 4xx responses
    handleHttpException(e.response)
} catch (e: ServerResponseException) { // Handles 5xx responses
    RequestResult.Error(DataError.Remote.SERVER)
} catch (e: NullPointerException) {
    Logger.e(message = e.toString())
    RequestResult.Error(DataError.Remote.NPE)
} catch (e: SocketTimeoutException) {
    Logger.e(message = e.toString())
    RequestResult.Error(DataError.Remote.REQUEST_TIMEOUT)
} catch (e: UnresolvedAddressException) {
    Logger.e(message = e.toString())
    RequestResult.Error(DataError.Remote.NO_INTERNET)
} catch (e: NoTransformationFoundException) {
    RequestResult.Error(DataError.Remote.SERIALIZATION)
} catch (e: Throwable) {
    Logger.e(message = e.toString())
    RequestResult.Error(DataError.Remote.UNKNOWN)
}

fun handleHttpException(response: HttpResponse): RequestResult.Error<DataError.Remote> {
    return when (response.status.value) {
        401 -> RequestResult.Error(DataError.Remote.UNAUTHORIZED)
        408 -> RequestResult.Error(DataError.Remote.REQUEST_TIMEOUT)
        429 -> RequestResult.Error(DataError.Remote.TOO_MANY_REQUESTS)
        else -> RequestResult.Error(DataError.Remote.UNKNOWN)
    }
}


/**
 * to display error text in snackbar or error dialog
 */
fun DataError.getMessage(): String = when (this) {
    DataError.Remote.REQUEST_TIMEOUT -> ERROR_REQUEST_TIMEOUT
    DataError.Remote.TOO_MANY_REQUESTS -> ERROR_TOO_MANY_REQUESTS
    DataError.Remote.NO_INTERNET -> ERROR_NO_INTERNET
    DataError.Remote.SERIALIZATION -> ERROR_SERIALIZATION
    else -> ERROR_UNKNOWN
}

//todo: separate this into strings.xml and observe on each string Res into to display it
private const val ERROR_UNKNOWN = "Упс, что-то пошло не так"
private const val ERROR_REQUEST_TIMEOUT = "Время запроса истекло"
private const val ERROR_NO_INTERNET = "Не удалось соединиться с сервером, проверьте подключение"
private const val ERROR_TOO_MANY_REQUESTS = "Слишком много запросов"
private const val ERROR_SERIALIZATION = "Не удалось обработать данные"