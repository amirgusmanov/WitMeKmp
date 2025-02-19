package kz.witme.project.profile_runtime_storage.data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kz.witme.project.common.extension.tryToUpdate
import kz.witme.project.profile_runtime_storage.domain.model.UserInfo

class UserProfileRuntimeStorage {

    private val _userProfile = MutableStateFlow<UserInfo?>(null)
    private val userProfile: StateFlow<UserInfo?> = _userProfile.asStateFlow()

    fun updateUserProfile(userInfo: UserInfo) {
        _userProfile.tryToUpdate {
            userInfo
        }
    }

    fun getUserProfileFlow(): Flow<UserInfo?> = userProfile
}