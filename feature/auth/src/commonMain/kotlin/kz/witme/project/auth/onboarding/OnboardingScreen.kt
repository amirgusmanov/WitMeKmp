package kz.witme.project.auth.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import kz.witme.project.common_ui.base.BlurredGradientSphere
import kz.witme.project.common_ui.base.DefaultProgressButton
import kz.witme.project.common_ui.theme.DefaultRoundedShape
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import kz.witme.project.navigation.Destination
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.ic_check
import witmekmp.core.common_ui.generated.resources.ic_like
import witmekmp.core.common_ui.generated.resources.login_button_text
import witmekmp.core.common_ui.generated.resources.onboarding_welcome_message
import witmekmp.core.common_ui.generated.resources.witbook

class OnboardingScreen : Screen {

    @Composable
    override fun Content() {
        OnboardingScreenContent()
    }
}

@Composable
internal fun OnboardingScreenContent() {
    val authScreen = rememberScreen(provider = Destination.Login)
    val navigator = LocalNavigator.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = LocalWitMeTheme.colors.background,
    ) { contentPaddingValues ->
        Box(
            modifier = Modifier.padding(contentPaddingValues)
        ) {
            BlurredGradientSphere()
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(80.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(DefaultRoundedShape)
                        .background(LocalWitMeTheme.colors.white),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = stringResource(Res.string.witbook),
                            style = LocalWitMeTheme.typography.semiBold32,
                            color = LocalWitMeTheme.colors.primary400
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = stringResource(Res.string.onboarding_welcome_message).trimIndent(),
                            style = LocalWitMeTheme.typography.regular16,
                            color = LocalWitMeTheme.colors.secondary500
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Icon(
                                painter = painterResource(Res.drawable.ic_check),
                                tint = LocalWitMeTheme.colors.secondary400,
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                painter = painterResource(Res.drawable.ic_like),
                                tint = LocalWitMeTheme.colors.secondary400,
                                contentDescription = null
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                DefaultProgressButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        navigator?.push(authScreen)
                    },
                    text = stringResource(Res.string.login_button_text),
                )
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}
