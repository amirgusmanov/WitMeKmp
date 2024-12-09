package kz.witme.project.auth.registration

import androidx.compose.runtime.Stable

@Stable
internal interface RegistrationController {

    fun onEmailQueryChanged(query: String)

    fun onPasswordQueryChanged(query: String)

    fun onRegisterClick()

    fun onRegistrationErrorDismiss()
}