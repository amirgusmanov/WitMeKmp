package kz.witme.project.profile.domain.repository

import kz.witme.project.data.network.DataError
import kz.witme.project.data.network.RequestResult

interface ProfileUpdateRepository {

    suspend fun uploadAvatar(
        avatar: ByteArray,
        username: String
    ): RequestResult<Unit, DataError.Remote>

    suspend fun deleteAccount(): RequestResult<Unit, DataError.Remote>
}