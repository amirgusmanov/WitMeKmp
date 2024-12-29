package kz.witme.project.create_book.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.witme.project.common.extension.isNotNull
import kz.witme.project.common_ui.base.DefaultAddCardView
import kz.witme.project.common_ui.base.DefaultButton
import kz.witme.project.common_ui.base.DefaultToolbar
import kz.witme.project.common_ui.base.InputSectionView
import kz.witme.project.common_ui.base.PhotoPickerOptionBottomSheetScreen
import kz.witme.project.common_ui.base.PiggySmileView
import kz.witme.project.common_ui.base.TopCurvedCircle
import kz.witme.project.common_ui.camera.rememberCameraManager
import kz.witme.project.common_ui.extension.clickableWithoutRipple
import kz.witme.project.common_ui.extension.collectAsStateWithLifecycle
import kz.witme.project.common_ui.gallery.rememberGalleryManager
import kz.witme.project.common_ui.image.SharedImage
import kz.witme.project.common_ui.permission.PermissionCallback
import kz.witme.project.common_ui.permission.PermissionStatus
import kz.witme.project.common_ui.permission.PermissionType
import kz.witme.project.common_ui.permission.createPermissionsManager
import kz.witme.project.common_ui.screen.toolbarPaddings
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import kz.witme.project.navigation.Destination
import org.jetbrains.compose.resources.stringResource
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.add_book
import witmekmp.core.common_ui.generated.resources.author_placeholder
import witmekmp.core.common_ui.generated.resources.author_title
import witmekmp.core.common_ui.generated.resources.book_lists_count
import witmekmp.core.common_ui.generated.resources.book_name
import witmekmp.core.common_ui.generated.resources.next
import witmekmp.core.common_ui.generated.resources.number_example
import witmekmp.core.common_ui.generated.resources.text_placeholder

class CreateBookScreen : Screen {

    @Composable
    override fun Content() {
        val controller: CreateBookViewModel = koinScreenModel()
        val uiState by controller.uiState.collectAsStateWithLifecycle()

        CreateBookScreenContent(
            controller = controller,
            uiState = uiState
        )
    }
}

@Composable
internal fun CreateBookScreenContent(
    controller: CreateBookController,
    uiState: CreateBookMainUiState
) {
    val navigator = LocalNavigator.current
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
                controller.onBookPhotoPicked(image = bitmap, byteArray = imageByteArray)
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
    LaunchedEffect(Unit) {
        controller.createBookNavigateFlow.collectLatest { result ->
            when (result) {
                is CreateBookViewModel.CreateBookNavigateChannel.NavigateToCreateStatus -> {
                    ScreenRegistry.get(Destination.CreateStatusBook(result.args)).let {
                        navigator?.push(it)
                    }
                }
            }
        }
    }
    CreateBookContent(
        controller = controller,
        uiState = uiState,
        onBackClick = {
            navigator?.pop()
        },
        onAddPhotoClick = controller::onAvatarClick
    )
}

@Composable
private fun CreateBookContent(
    controller: CreateBookController,
    uiState: CreateBookMainUiState,
    onBackClick: () -> Unit,
    onAddPhotoClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.statusBars,
        containerColor = LocalWitMeTheme.colors.background,
        topBar = {
            DefaultToolbar(
                modifier = Modifier.toolbarPaddings(),
                toolbarTitle = stringResource(Res.string.add_book),
                onBackClick = onBackClick
            )
        }
    ) { contentPaddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPaddingValues)
                .clickableWithoutRipple(onClick = focusManager::clearFocus)
        ) {
            TopCurvedCircle(
                yOffset = 0f,
                radiusDivisionNumber = 1.2f,
                modifier = Modifier.graphicsLayer {
                    translationY = -scrollState.value.toFloat()
                }
            )
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(horizontal = 16.dp)
            ) {
                PiggySmileView(
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                DefaultAddCardView(
                    modifier = Modifier.heightIn(min = 210.dp, max = 215.dp),
                    photo = remember(uiState.photo) {
                        uiState.photo
                    },
                    onClick = {
                        focusManager.clearFocus()
                        onAddPhotoClick()
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))
                InputSectionView(
                    hintText = stringResource(Res.string.text_placeholder),
                    titleText = stringResource(Res.string.book_name),
                    query = uiState.bookName,
                    onQueryChanged = controller::onBookNameQueryChanged
                )
                Spacer(modifier = Modifier.height(16.dp))
                InputSectionView(
                    hintText = stringResource(Res.string.author_placeholder),
                    titleText = stringResource(Res.string.author_title),
                    query = uiState.authorName,
                    onQueryChanged = controller::onAuthorQueryChanged
                )
                Spacer(modifier = Modifier.height(16.dp))
                InputSectionView(
                    hintText = stringResource(Res.string.number_example),
                    titleText = stringResource(Res.string.book_lists_count),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    query = uiState.bookListCount?.toString().orEmpty(),
                    onQueryChanged = controller::onBookListCountQueryChanged
                )
            }
            DefaultButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 24.dp)
                    .align(Alignment.BottomCenter),
                onClick = controller::onNextButtonClick,
                text = stringResource(Res.string.next),
                isEnabled = with(uiState) {
                    bookName.isNotBlank()
                            && authorName.isNotBlank()
                            && bookListCount.isNotNull()
                }
            )
        }
    }
}