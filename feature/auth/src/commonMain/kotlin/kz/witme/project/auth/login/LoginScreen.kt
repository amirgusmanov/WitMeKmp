package kz.witme.project.auth.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import kz.witme.project.common_ui.base.BlurredGradientSphere
import kz.witme.project.common_ui.base.DefaultProgressButton
import kz.witme.project.common_ui.base.DefaultTextField
import kz.witme.project.common_ui.base.ErrorAlert
import kz.witme.project.common_ui.base.PasswordTextField
import kz.witme.project.common_ui.extension.clickableWithoutRipple
import kz.witme.project.common_ui.extension.collectAsStateWithLifecycle
import kz.witme.project.common_ui.extension.textBrush
import kz.witme.project.common_ui.theme.LinearGradient
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import kz.witme.project.navigation.Destination
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.auth
import witmekmp.core.common_ui.generated.resources.email
import witmekmp.core.common_ui.generated.resources.email_msg
import witmekmp.core.common_ui.generated.resources.have_no_account
import witmekmp.core.common_ui.generated.resources.ic_back
import witmekmp.core.common_ui.generated.resources.next
import witmekmp.core.common_ui.generated.resources.password

class LoginScreen : Screen {

    @Composable
    override fun Content() {
        val controller: LoginViewModel = koinScreenModel<LoginViewModel>()
        val uiState by controller.uiState.collectAsStateWithLifecycle()

        LoginScreenContent(
            uiState = uiState,
            controller = controller
        )
    }
}

@Composable
internal fun LoginScreenContent(
    uiState: LoginUiState,
    controller: LoginController
) {
    val navigator = LocalNavigator.current
    val registrationScreen = rememberScreen(provider = Destination.Registration)
    val dashboardScreen = rememberScreen(provider = Destination.Dashboard)

    LaunchedEffect(uiState.isLoginSuccess) {
        if (uiState.isLoginSuccess) {
            navigator?.replaceAll(dashboardScreen)
        }
    }
    if (uiState.loginErrorMessage.isNotEmpty()) {
        ErrorAlert(
            errorText = uiState.loginErrorMessage,
            onDismiss = controller::onLoginErrorDismiss
        )
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = LocalWitMeTheme.colors.white
    ) { contentPaddingValues ->
        Box(
            modifier = Modifier.padding(contentPaddingValues)
        ) {
            BlurredGradientSphere()
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(80.dp))
                Icon(
                    painter = painterResource(Res.drawable.ic_back),
                    contentDescription = null,
                    modifier = Modifier
                        .clickableWithoutRipple { navigator?.pop() }
                        .graphicsLayer(alpha = 0.99f)
                        .drawWithCache {
                            onDrawWithContent {
                                drawContent()
                                drawRect(LinearGradient, blendMode = BlendMode.SrcAtop)
                            }
                        }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(Res.string.auth),
                    style = LocalWitMeTheme.typography.medium20,
                    modifier = Modifier.textBrush(LinearGradient)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(Res.string.email_msg),
                    style = LocalWitMeTheme.typography.regular16,
                    color = LocalWitMeTheme.colors.secondary500
                )
                Spacer(modifier = Modifier.height(24.dp))
                DefaultTextField(
                    query = uiState.emailQuery,
                    textPlaceholder = stringResource(Res.string.email),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    onQueryChanged = controller::onEmailQueryChanged
                )
                Spacer(modifier = Modifier.height(16.dp))
                PasswordTextField(
                    query = uiState.passwordQuery,
                    textPlaceholder = stringResource(Res.string.password),
                    onQueryChanged = controller::onPasswordQueryChanged
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.weight(1f))
                    AnimatedVisibility(
                        visible = uiState.isLoginButtonLoading.not()
                    ) {
                        Text(
                            text = stringResource(Res.string.have_no_account),
                            style = LocalWitMeTheme.typography.regular12,
                            color = LocalWitMeTheme.colors.link200,
                            modifier = Modifier.clickableWithoutRipple {
                                navigator?.push(registrationScreen)
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .imePadding()
                ) {
                    DefaultProgressButton(
                        onClick = controller::onLoginClick,
                        text = stringResource(Res.string.next),
                        modifier = Modifier.fillMaxWidth(),
                        isEnabled = uiState.isLoginButtonEnabled,
                        isLoading = uiState.isLoginButtonLoading
                    )
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}