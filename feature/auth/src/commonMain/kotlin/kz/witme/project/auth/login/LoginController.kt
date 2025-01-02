package kz.witme.project.auth.login

import androidx.compose.runtime.Stable

@Stable
internal interface LoginController {

    fun onEmailQueryChanged(query: String)

    fun onPasswordQueryChanged(query: String)

    fun onLoginClick()

    fun onLoginErrorDismiss()

    fun navigateToTabs()
}