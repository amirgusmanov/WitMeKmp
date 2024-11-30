package kz.witme.project.data.network

sealed interface RequestResult<out D, out E : Error> {
    data class Success<out D>(val data: D) : RequestResult<D, Nothing>
    data class Error<out E : kz.witme.project.data.network.Error>(val error: E) :
        RequestResult<Nothing, E>
}

inline fun <T, E : Error> RequestResult<T, E>.onSuccess(action: (T) -> Unit): RequestResult<T, E> =
    when (this) {
        is RequestResult.Error -> this
        is RequestResult.Success -> {
            action(data)
            this
        }
    }

inline fun <T, E : Error> RequestResult<T, E>.onError(action: (E) -> Unit): RequestResult<T, E> =
    when (this) {
        is RequestResult.Success -> this
        is RequestResult.Error -> {
            action(error)
            this
        }
    }
