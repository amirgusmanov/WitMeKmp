package kz.witme.project.auth.edit_profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.witme.project.auth.edit_profile.component.ProfilePhotoCard
import kz.witme.project.common.extension.isNotNull
import kz.witme.project.common_ui.base.BlurredGradientSphere
import kz.witme.project.common_ui.base.DefaultProgressButton
import kz.witme.project.common_ui.base.DefaultTextField
import kz.witme.project.common_ui.base.ErrorAlert
import kz.witme.project.common_ui.base.MessageAlert
import kz.witme.project.common_ui.base.PhotoPickerOptionBottomSheetScreen
import kz.witme.project.common_ui.camera.rememberCameraManager
import kz.witme.project.common_ui.extension.clickableWithoutRipple
import kz.witme.project.common_ui.extension.collectAsStateWithLifecycle
import kz.witme.project.common_ui.extension.textBrush
import kz.witme.project.common_ui.gallery.rememberGalleryManager
import kz.witme.project.common_ui.image.SharedImage
import kz.witme.project.common_ui.permission.PermissionCallback
import kz.witme.project.common_ui.permission.PermissionStatus
import kz.witme.project.common_ui.permission.PermissionType
import kz.witme.project.common_ui.permission.createPermissionsManager
import kz.witme.project.common_ui.theme.LinearGradient
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import org.jetbrains.compose.resources.stringResource
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.name
import witmekmp.core.common_ui.generated.resources.next
import witmekmp.core.common_ui.generated.resources.permission_message
import witmekmp.core.common_ui.generated.resources.skip_this_step
import witmekmp.core.common_ui.generated.resources.whats_your_name
import witmekmp.core.common_ui.generated.resources.your_profile

class EditProfileScreen : Screen {

    @Composable
    override fun Content() {
        val controller: EditProfileViewModel = koinScreenModel()
        val uiState by controller.uiState.collectAsStateWithLifecycle()

        EditProfileScreenContent(
            controller = controller,
            uiState = uiState
        )
    }
}

@Composable
internal fun EditProfileScreenContent(
    controller: EditProfileController,
    uiState: EditProfileUiState
) {
    val bottomSheetNavigator = LocalBottomSheetNavigator.current
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
                            PermissionType.Gallery -> controller.onGalleryLaunch()
                            PermissionType.Camera -> controller.onCameraLaunch()
                        }
                    }

                    else -> controller.onRationalDialogShow()
                }
            }
        }
    )
    val handleImage: (SharedImage?) -> Unit = { image ->
        coroutineScope.launch {
            val (bitmap, imageByteArray) = withContext(Dispatchers.Default) {
                val bitmap = image?.toImageBitmap()
                val byteArray = image?.toByteArray()

                bitmap to byteArray
            }
            if (bitmap != null && imageByteArray != null) {
                controller.onAvatarPick(image = bitmap, imageByteArray = imageByteArray)
            }
        }
    }
    val cameraManager = rememberCameraManager(handleImage)
    val galleryManager = rememberGalleryManager(handleImage)

    if (uiState.launchCamera) {
        if (permissionsManager.isPermissionGranted(PermissionType.Camera)) {
            cameraManager.launch()
        } else {
            permissionsManager.askPermission(PermissionType.Camera)
        }
        controller.onCameraPermissionAsk()
    }
    if (uiState.launchGallery) {
        if (permissionsManager.isPermissionGranted(PermissionType.Gallery)) {
            galleryManager.launch()
        } else {
            permissionsManager.askPermission(PermissionType.Gallery)
        }
        controller.onGalleryPermissionAsk()
    }
    if (uiState.launchSettings) {
        controller.onAvatarPickOptionBottomSheetDismiss()
        permissionsManager.launchSettings()
        controller.onSettingsLaunched()
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
    if (uiState.updateErrorMessage.isNotBlank()) {
        ErrorAlert(
            errorText = uiState.updateErrorMessage,
            onDismiss = controller::onErrorDismiss
        )
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
                    onGalleryOptionChoose = controller::onGalleryLaunch
                )
            )
        } else {
            bottomSheetNavigator.hide()
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = LocalWitMeTheme.colors.white
    ) { contentPaddingValues ->
        EditProfileContent(
            uiState = uiState,
            controller = controller,
            contentPaddingValues = contentPaddingValues,
            onAvatarAddClick = controller::onAvatarClick
        )
    }
}

@Composable
private fun EditProfileContent(
    uiState: EditProfileUiState,
    controller: EditProfileController,
    contentPaddingValues: PaddingValues,
    onAvatarAddClick: () -> Unit
) {
    val navigator = LocalNavigator.current
    val focusManager = LocalFocusManager.current
    Box(
        modifier = Modifier
            .padding(contentPaddingValues)
            .clickableWithoutRipple(onClick = focusManager::clearFocus)
    ) {
        BlurredGradientSphere()
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            ProfilePhotoCard(
                modifier = Modifier.padding(horizontal = 20.dp),
                chosenPhoto = remember(uiState.imageBitmap) {
                    uiState.imageBitmap
                },
                onClick = onAvatarAddClick,
                onLongClick = controller::onAvatarClear
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = stringResource(Res.string.your_profile),
                style = LocalWitMeTheme.typography.medium20,
                modifier = Modifier.textBrush(LinearGradient)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(Res.string.whats_your_name),
                style = LocalWitMeTheme.typography.regular16,
                color = LocalWitMeTheme.colors.secondary500
            )
            Spacer(modifier = Modifier.height(16.dp))
            DefaultTextField(
                query = uiState.nameQuery,
                textPlaceholder = stringResource(Res.string.name),
                onQueryChanged = controller::onNameQueryChange
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.weight(1f))
                if (uiState.isUpdateLoading.not()) {
                    Text(
                        text = stringResource(Res.string.skip_this_step),
                        style = LocalWitMeTheme.typography.regular12,
                        color = LocalWitMeTheme.colors.link200,
                        modifier = Modifier.clickableWithoutRipple(
                            onClick = controller::navigateToTabs
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                DefaultProgressButton(
                    onClick = controller::onNextButtonClick,
                    text = stringResource(Res.string.next),
                    modifier = Modifier.fillMaxWidth(),
                    isLoading = uiState.isUpdateLoading,
                    isEnabled = uiState.nameQuery.isNotBlank()
                            && uiState.imageBitmap.isNotNull()
                            && uiState.isUpdateButtonEnabled
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}