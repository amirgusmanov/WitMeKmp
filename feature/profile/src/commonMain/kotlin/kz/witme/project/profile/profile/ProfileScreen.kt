package kz.witme.project.profile.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import coil3.compose.AsyncImage
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.witme.project.common_ui.base.ErrorAlert
import kz.witme.project.common_ui.base.MessageAlert
import kz.witme.project.common_ui.base.PhotoPickerOptionBottomSheetScreen
import kz.witme.project.common_ui.camera.rememberCameraManager
import kz.witme.project.common_ui.extension.clickableWithPressedState
import kz.witme.project.common_ui.extension.clickableWithoutRipple
import kz.witme.project.common_ui.extension.collectAsStateWithLifecycle
import kz.witme.project.common_ui.image.SharedImage
import kz.witme.project.common_ui.permission.PermissionCallback
import kz.witme.project.common_ui.permission.PermissionStatus
import kz.witme.project.common_ui.permission.PermissionType
import kz.witme.project.common_ui.permission.createPermissionsManager
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import kz.witme.project.data.network.getImageUrl
import kz.witme.project.navigation.Destination
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.account_management_title
import witmekmp.core.common_ui.generated.resources.delete_account
import witmekmp.core.common_ui.generated.resources.delete_account_message
import witmekmp.core.common_ui.generated.resources.ic_arrow_right
import witmekmp.core.common_ui.generated.resources.ic_profile_placeholder
import witmekmp.core.common_ui.generated.resources.logout
import witmekmp.core.common_ui.generated.resources.logout_message
import witmekmp.core.common_ui.generated.resources.no
import witmekmp.core.common_ui.generated.resources.permission_message
import witmekmp.core.common_ui.generated.resources.privacy_policy
import witmekmp.core.common_ui.generated.resources.support_title
import witmekmp.core.common_ui.generated.resources.yes

class ProfileScreen : Screen {

    @Composable
    override fun Content() {
        val controller: ProfileViewModel = koinScreenModel<ProfileViewModel>()
        val uiState by controller.uiState.collectAsStateWithLifecycle()
        val bottomSheetNavigator = LocalBottomSheetNavigator.current
        val navigator = LocalNavigator.current
        val privacyPolicyTitle = stringResource(Res.string.privacy_policy)
        val coroutineScope = rememberCoroutineScope()
        val permissionsManager = createPermissionsManager(
            callback = object : PermissionCallback {
                override fun onPermissionStatus(
                    permissionType: PermissionType,
                    status: PermissionStatus
                ) {
                    when (status) {
                        PermissionStatus.Granted -> {
                            when (permissionType) {
                                PermissionType.Gallery -> Unit
                                PermissionType.Camera -> controller.onCameraLaunch()
                            }
                        }

                        else -> controller.onRationalDialogShow()
                    }
                }
            }
        )
        val photoPicker = rememberImagePickerLauncher(
            selectionMode = SelectionMode.Single,
            scope = coroutineScope,
            onResult = { byteArrays ->
                byteArrays.firstOrNull()?.let {
                    controller.onBookPhotoPicked(it)
                }
            }
        )
        val handleImage: (SharedImage?) -> Unit = { image ->
            coroutineScope.launch {
                val imageByteArray = withContext(Dispatchers.Default) {
                    image?.toByteArray()
                }
                if (imageByteArray != null) {
                    controller.onBookPhotoPicked(byteArray = imageByteArray)
                }
            }
        }
        val cameraManager = rememberCameraManager(handleImage)
        if (uiState.launchCamera) {
            if (permissionsManager.isPermissionGranted(PermissionType.Camera)) {
                cameraManager.launch()
            } else {
                permissionsManager.askPermission(PermissionType.Camera)
            }
            controller.onCameraPermissionAsk()
        }
        if (uiState.launchSettings) {
            controller.onAvatarPickOptionBottomSheetDismiss()
            permissionsManager.launchSettings()
            controller.onSettingsLaunched()
        }
        LaunchedEffect(bottomSheetNavigator.isVisible) {
            if (!bottomSheetNavigator.isVisible) {
                controller.onAvatarPickOptionBottomSheetDismiss()
            }
        }
        LaunchedEffect(uiState.isAvatarPickOptionBottomSheetVisible) {
            if (uiState.isAvatarPickOptionBottomSheetVisible) {
                bottomSheetNavigator.show(
                    PhotoPickerOptionBottomSheetScreen(
                        onCameraOptionChoose = controller::onCameraLaunch,
                        onGalleryOptionChoose = {
                            photoPicker.launch()
                        }
                    )
                )
            } else {
                bottomSheetNavigator.hide()
            }
        }
        LaunchedEffect(controller.responseEventFlow) {
            controller.responseEventFlow.collectLatest { event ->
                when (event) {
                    ProfileViewModel.ResponseEvent.NavigateToPrivacyPolicy -> {
                        ScreenRegistry.get(
                            Destination.WebViewScreen(
                                title = privacyPolicyTitle,
                                link = PRIVACY_POLICY_LINK
                            )
                        ).let {
                            navigator?.push(it)
                        }
                    }
                }
            }
        }
        if (uiState.showRationalDialog) {
            MessageAlert(
                subtitle = stringResource(Res.string.permission_message),
                onDismiss = controller::onRationalDialogDismiss,
                onConfirm = {
                    controller.onRationalDialogDismiss()
                    controller.onSettingsLaunch()
                }
            )
        }
        ProfileScreenContent(
            controller = controller,
            uiState = uiState
        )
    }

    companion object {
        private const val PRIVACY_POLICY_LINK =
            "https://drive.google.com/file/d/1AnusQDd2gSKDd6q2PMZUIRaWgdqZ1AY5/view?usp=sharing"
    }
}

@Composable
internal fun ProfileScreenContent(
    controller: ProfileController,
    uiState: ProfileUiState
) {
    if (uiState.errorMessage.isNotBlank()) {
        ErrorAlert(
            errorText = uiState.errorMessage,
            onDismiss = controller::onErrorDismiss
        )
    }
    if (uiState.showDeleteAccountAlert) {
        MessageAlert(
            subtitle = stringResource(Res.string.delete_account_message),
            onDismiss = controller::onDeleteAccountAlertDismiss,
            onConfirm = controller::onDeleteAccountAlertConfirm,
            negativeButtonText = stringResource(Res.string.no),
            positiveButtonText = stringResource(Res.string.yes)
        )
    }
    if (uiState.showLogoutAlert) {
        MessageAlert(
            subtitle = stringResource(Res.string.logout_message),
            onDismiss = controller::onExitAlertDismiss,
            onConfirm = controller::onExitAlertConfirm,
            negativeButtonText = stringResource(Res.string.no),
            positiveButtonText = stringResource(Res.string.yes)
        )
    }
    Column(
        modifier = Modifier
            .padding(top = 64.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.clickableWithPressedState(onClick = controller::onAvatarClick)
            ) {
                AsyncImage(
                    model = getImageUrl(uiState.avatar),
                    contentDescription = "avatar",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(Res.drawable.ic_profile_placeholder),
                    error = painterResource(Res.drawable.ic_profile_placeholder),
                    fallback = painterResource(Res.drawable.ic_profile_placeholder)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = uiState.username,
                style = LocalWitMeTheme.typography.semiBold20,
                color = LocalWitMeTheme.colors.primary400
            )
            if (uiState.email.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = uiState.email,
                    style = LocalWitMeTheme.typography.regular18,
                    color = LocalWitMeTheme.colors.secondary400
                )
            }
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
                    .clickableWithoutRipple(onClick = controller::onPrivacyPolicyClick)
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
                    .clickableWithoutRipple(onClick = controller::onDeleteAccountClick)
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
                    .clickableWithoutRipple(onClick = controller::onExitClick)
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