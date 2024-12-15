package kz.witme.project.profile.profile

import androidx.compose.runtime.Stable

@Stable
internal interface ProfileController {

    fun onPrivacyPolicyClick()
    fun onDeleteAccountClick()
    fun onExitClick()
}