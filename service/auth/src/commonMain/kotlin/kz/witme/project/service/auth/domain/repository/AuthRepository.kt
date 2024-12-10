package kz.witme.project.service.auth.domain.repository

import kz.witme.project.data.network.DataError
import kz.witme.project.data.network.RequestResult

interface AuthRepository {

    suspend fun login(email: String, password: String): RequestResult<Unit, DataError.Remote>

    suspend fun register(email: String, password: String): RequestResult<Unit, DataError.Remote>

    suspend fun getMe(): RequestResult<Unit, DataError.Remote>

    suspend fun navigateUser(): RequestResult<Unit, DataError.Local>
}