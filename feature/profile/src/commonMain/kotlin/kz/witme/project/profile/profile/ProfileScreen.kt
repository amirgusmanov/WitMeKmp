package kz.witme.project.profile.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import kz.witme.project.common_ui.theme.TextBrush
import kz.witme.project.navigation.Destination
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.account_management_title
import witmekmp.core.common_ui.generated.resources.auth
import witmekmp.core.common_ui.generated.resources.delete_account
import witmekmp.core.common_ui.generated.resources.email
import witmekmp.core.common_ui.generated.resources.email_msg
import witmekmp.core.common_ui.generated.resources.have_no_account
import witmekmp.core.common_ui.generated.resources.ic_arrow_right
import witmekmp.core.common_ui.generated.resources.ic_back
import witmekmp.core.common_ui.generated.resources.ic_profile_placeholder
import witmekmp.core.common_ui.generated.resources.logout
import witmekmp.core.common_ui.generated.resources.next
import witmekmp.core.common_ui.generated.resources.password
import witmekmp.core.common_ui.generated.resources.privacy_policy
import witmekmp.core.common_ui.generated.resources.support_title

class ProfileScreen : Screen {

    @Composable
    override fun Content() {
        val controller: ProfileViewModel = koinScreenModel<ProfileViewModel>()
        val uiState by controller.uiState.collectAsStateWithLifecycle()

        ProfileScreenContent(
            controller = controller,
            uiState = uiState
        )
    }
}

@Composable
internal fun ProfileScreenContent(
    controller: ProfileController,
    uiState: ProfileUiState
) {
    Column(
        modifier = Modifier
            .padding(top = 64.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_profile_placeholder),
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                tint = LocalWitMeTheme.colors.primary400
            )

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Армар Сарсенов Агабекулы",
                style = LocalWitMeTheme.typography.semiBold20,
                color = LocalWitMeTheme.colors.primary400
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "adin.ara@gmail.com",
                style = LocalWitMeTheme.typography.regular18,
                color = LocalWitMeTheme.colors.secondary400
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(Res.string.support_title),
                style = LocalWitMeTheme.typography.medium16,
                color = LocalWitMeTheme.colors.secondary600,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /* Handle click */ }
            ) {
                Text(
                    text = stringResource(Res.string.privacy_policy),
                    style = LocalWitMeTheme.typography.regular16,
                    color = LocalWitMeTheme.colors.secondary600
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(Res.drawable.ic_arrow_right),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Divider(
                color = LocalWitMeTheme.colors.secondary300,
                thickness = 1.dp,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(Res.string.account_management_title),
                style = LocalWitMeTheme.typography.medium16,
                color = LocalWitMeTheme.colors.secondary600,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /* Handle click */ }
            ) {
                Text(
                    text = stringResource(Res.string.delete_account),
                    style = LocalWitMeTheme.typography.regular16,
                    color = LocalWitMeTheme.colors.secondary600
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(Res.drawable.ic_arrow_right),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Divider(
                color = LocalWitMeTheme.colors.secondary300,
                thickness = 1.dp,
                modifier = Modifier.padding(top = 8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /* Handle click */ }
            ) {
                Text(
                    text = stringResource(Res.string.logout),
                    style = LocalWitMeTheme.typography.regular16,
                    color = LocalWitMeTheme.colors.secondary600
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(Res.drawable.ic_arrow_right),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Divider(
                color = LocalWitMeTheme.colors.secondary300,
                thickness = 1.dp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}