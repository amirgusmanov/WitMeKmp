package kz.witme.project.create_book.main

import androidx.compose.ui.graphics.ImageBitmap
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kz.witme.project.book.domain.repository.CreateBookRepository
import kz.witme.project.common.extension.tryToGet
import kz.witme.project.common.extension.tryToUpdate
import kz.witme.project.navigation.CreateBookArgs

internal class CreateBookViewModel(
    private val createBookRepository: CreateBookRepository
) : ScreenModel, CreateBookController {

    val uiState: StateFlow<CreateBookMainUiState> = MutableStateFlow(CreateBookMainUiState())

    private val _createBookNavigationChannel = Channel<CreateBookNavigateChannel>()
    override val createBookNavigateFlow = _createBookNavigationChannel.receiveAsFlow()

    private var imageByteArray: ByteArray? = null

    override fun onAuthorQueryChanged(query: String) {
        uiState.tryToUpdate { state ->
            state.copy(
                authorName = query
            )
        }
    }

    override fun onBookNameQueryChanged(query: String) {
        uiState.tryToUpdate { state ->
            state.copy(
                bookName = query
            )
        }
    }

    override fun onBookListCountQueryChanged(query: String) {
        uiState.tryToUpdate { state ->
            state.copy(
                bookListCount = tryToGet {
                    query.toInt()
                }
            )
        }
    }

    override fun onSettingsLaunch() {
        uiState.tryToUpdate { state ->
            state.copy(launchSettings = true)
        }
    }

    override fun onCameraLaunch() {
        uiState.tryToUpdate { state ->
            state.copy(
                launchCamera = true,
                isAvatarPickOptionBottomSheetVisible = false
            )
        }
    }

    override fun onGalleryLaunch() {
        uiState.tryToUpdate { state ->
            state.copy(
                launchGallery = true,
                isAvatarPickOptionBottomSheetVisible = false
            )
        }
    }

    override fun onSettingsLaunched() {
        uiState.tryToUpdate { state ->
            state.copy(launchSettings = false)
        }
    }

    override fun onRationalDialogShow() {
        uiState.tryToUpdate { state ->
            state.copy(showRationalDialog = true)
        }
    }

    override fun onRationalDialogDismiss() {
        uiState.tryToUpdate { state ->
            state.copy(showRationalDialog = false)
        }
    }

    override fun onAvatarClick() {
        uiState.tryToUpdate { state ->
            state.copy(isAvatarPickOptionBottomSheetVisible = true)
        }
    }

    override fun onAvatarPickOptionBottomSheetDismiss() {
        uiState.tryToUpdate { state ->
            state.copy(isAvatarPickOptionBottomSheetVisible = false)
        }
    }

    override fun onCameraPermissionAsk() {
        uiState.tryToUpdate { state ->
            state.copy(launchCamera = false)
        }
    }

    override fun onGalleryPermissionAsk() {
        uiState.tryToUpdate { state ->
            state.copy(launchGallery = false)
        }
    }

    override fun onBookPhotoPicked(image: ImageBitmap, byteArray: ByteArray) {
        imageByteArray = byteArray
        uiState.tryToUpdate { state ->
            state.copy(
                photo = image
            )
        }
    }

    override fun onNextButtonClick() {
        screenModelScope.launch {
            createBookRepository.saveTempImage(imageByteArray)
            _createBookNavigationChannel.send(
                CreateBookNavigateChannel.NavigateToCreateStatus(
                    args = uiState.value.toArgs()
                )
            )
        }
    }

    private fun CreateBookMainUiState.toArgs(): CreateBookArgs =
        CreateBookArgs(
            bookName = bookName,
            authorName = authorName,
            bookListCount = bookListCount ?: 0
        )

    internal sealed interface CreateBookNavigateChannel {
        data class NavigateToCreateStatus(val args: CreateBookArgs) : CreateBookNavigateChannel
    }
}
        