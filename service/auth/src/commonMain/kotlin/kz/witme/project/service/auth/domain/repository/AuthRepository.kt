package kz.witme.project.service.auth.domain.repository

import kotlinx.coroutines.flow.Flow
import kz.witme.project.data.network.DataError
import kz.witme.project.data.network.RequestResult
import kz.witme.project.profile_runtime_storage.domain.model.UserInfo

interface AuthRepository {

    suspend fun login(email: String, password: String): RequestResult<Unit, DataError.Remote>

    suspend fun register(email: String, password: String): RequestResult<Unit, DataError.Remote>

    suspend fun navigateUser(): RequestResult<UserInfo, DataError.Remote>

    suspend fun getUserInfoFlow(): Flow<UserInfo?>

    suspend fun logout()
}