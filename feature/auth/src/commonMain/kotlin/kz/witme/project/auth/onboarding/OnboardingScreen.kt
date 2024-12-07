//package kz.witme.project.auth.onboarding
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import cafe.adriel.voyager.core.registry.rememberScreen
//import cafe.adriel.voyager.core.screen.Screen
//import cafe.adriel.voyager.navigator.LocalNavigator
//import kz.witme.auth.R
//import kz.witme.common_ui.base.component.animation.BlurredGradientSphere
//import kz.witme.common_ui.base.component.button.DefaultProgressButton
//import kz.witme.common_ui.theme.DefaultRoundedShape
//import kz.witme.common_ui.theme.LocalWithMeTheme
//import kz.witme.common_ui.theme.WitMeTheme
//import kz.witme.navigation.destination.Destination
//
//class OnboardingScreen : Screen {
//
//    @Composable
//    override fun Content() {
//        OnboardingScreenContent()
//    }
//}
//
//@Composable
//internal fun OnboardingScreenContent() {
//    val authScreen = rememberScreen(provider = Destination.Auth)
//    val navigator = LocalNavigator.current
//
//    Scaffold(
//        modifier = Modifier.fillMaxSize(),
//        containerColor = LocalWithMeTheme.colors.background
//    ) { contentPaddingValues ->
//        Box(
//            modifier = Modifier.padding(contentPaddingValues)
//        ) {
//            BlurredGradientSphere()
//
//            Column(
//                modifier = Modifier.padding(horizontal = 16.dp)
//            ) {
//                Spacer(modifier = Modifier.height(80.dp))
//
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clip(DefaultRoundedShape)
//                        .background(LocalWithMeTheme.colors.white),
//                ) {
//                    Column(
//                        modifier = Modifier.padding(16.dp)
//                    ) {
//                        Text(
//                            text = stringResource(R.string.witbook),
//                            style = LocalWithMeTheme.typography.semiBold32,
//                            color = LocalWithMeTheme.colors.primary400
//                        )
//
//                        Spacer(modifier = Modifier.height(16.dp))
//
//                        Text(
//                            text = stringResource(id = R.string.onboarding_welcome_message).trimIndent(),
//                            style = LocalWithMeTheme.typography.regular16,
//                            color = LocalWithMeTheme.colors.secondary500
//                        )
//
//                        Spacer(modifier = Modifier.height(16.dp))
//
//                        Row(modifier = Modifier.fillMaxWidth()) {
//                            Icon(
//                                painter = painterResource(id = R.drawable.ic_check),
//                                tint = LocalWithMeTheme.colors.secondary400,
//                                contentDescription = null
//                            )
//
//                            Spacer(modifier = Modifier.weight(1f))
//
//                            Icon(
//                                painter = painterResource(id = R.drawable.ic_like),
//                                tint = LocalWithMeTheme.colors.secondary400,
//                                contentDescription = null
//                            )
//                        }
//                    }
//                }
//
//                Spacer(modifier = Modifier.weight(1f))
//
//                DefaultProgressButton(
//                    modifier = Modifier.fillMaxWidth(),
//                    onClick = {
//                        navigator?.push(authScreen)
//                    },
//                    text = stringResource(R.string.login_button_text),
//                )
//
//                Spacer(modifier = Modifier.height(40.dp))
//            }
//
//        }
//    }
//}
//
//@Preview
//@Composable
//private fun OnboardingScreenPreview() {
//    WitMeTheme {
//        OnboardingScreenContent()
//    }
//}
//
