package kz.witme.project.profile.profile

import androidx.compose.runtime.Stable

@Stable
internal interface ProfileController {

    fun onPrivacyPolicyClick()

    fun onDeleteAccountClick()

    fun onDeleteAccountAlertDismiss()

    fun onDeleteAccountAlertConfirm()

    fun onExitClick()

    fun onExitAlertDismiss()

    fun onExitAlertConfirm()

    fun onAvatarClick()

    fun onErrorDismiss()
}