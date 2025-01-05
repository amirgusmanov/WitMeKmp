package kz.witme.project.create_book.main_status

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collectLatest
import kz.witme.project.book.domain.model.ReadingStatus
import kz.witme.project.common.extension.isNotNull
import kz.witme.project.common_ui.base.DefaultProgressButton
import kz.witme.project.common_ui.base.DefaultToolbar
import kz.witme.project.common_ui.base.ErrorAlert
import kz.witme.project.common_ui.base.InputSectionView
import kz.witme.project.common_ui.base.TitledSelector
import kz.witme.project.common_ui.extension.collectAsStateWithLifecycle
import kz.witme.project.common_ui.screen.toolbarPaddings
import kz.witme.project.common_ui.theme.LinearGradient
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import kz.witme.project.create_book.component.EmojiBottomSheetScreen
import kz.witme.project.create_book.component.StarRating
import kz.witme.project.navigation.CreateBookArgs
import kz.witme.project.navigation.result.ResultConstants
import kz.witme.project.navigation.result.setScreenResult
import org.jetbrains.compose.resources.stringResource
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.add_book
import witmekmp.core.common_ui.generated.resources.book_description
import witmekmp.core.common_ui.generated.resources.book_description_example
import witmekmp.core.common_ui.generated.resources.book_status
import witmekmp.core.common_ui.generated.resources.next
import witmekmp.core.common_ui.generated.resources.star_rating

class CreateBookStatusScreen(private val args: CreateBookArgs) : Screen {

    @Composable
    override fun Content() {
        val viewModel: CreateBookStatusViewModel = koinScreenModel()
        val uiState: CreateBookStatusUiState by viewModel.uiState.collectAsStateWithLifecycle()

        LaunchedEffect(args) {
            viewModel.onLaunched(args)
        }
        CreateBookStatusScreenContent(
            controller = viewModel,
            uiState = uiState
        )
    }
}

@Composable
internal fun CreateBookStatusScreenContent(
    controller: CreateBookStatusController,
    uiState: CreateBookStatusUiState
) {
    val navigator = LocalNavigator.current
    val bottomSheetNavigator = LocalBottomSheetNavigator.current
    val scrollState = rememberScrollState()

    if (uiState.errorMessage.isNotEmpty()) {
        ErrorAlert(
            errorText = uiState.errorMessage,
            onDismiss = controller::onErrorDismiss
        )
    }
    LaunchedEffect(Unit) {
        controller.navigateFlow.collectLatest { result ->
            when (result) {
                CreateBookStatusViewModel.NavigateResult.NavigateToDashboard -> {
                    navigator?.setScreenResult(
                        ResultConstants.CREATE_BOOK_SUCCESS,
                        true
                    )
                    navigator?.popUntilRoot()
                }
            }
        }
    }
    LaunchedEffect(uiState.isPickEmojiBottomSheetVisible) {
        if (uiState.isPickEmojiBottomSheetVisible) {
            bottomSheetNavigator.show(
                EmojiBottomSheetScreen(
                    selectedEmoji = uiState.selectedEmoji,
                    onEmojiSelected = controller::onEmojiPicked
                )
            )
        } else {
            bottomSheetNavigator.hide()
        }
    }
    LaunchedEffect(bottomSheetNavigator.isVisible) {
        if (!bottomSheetNavigator.isVisible) {
            controller.onEmojiBottomSheetDismiss()
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.statusBars,
        containerColor = LocalWitMeTheme.colors.white,
        topBar = {
            DefaultToolbar(
                modifier = Modifier.toolbarPaddings(),
                toolbarTitle = stringResource(Res.string.add_book),
                onBackClick = {
                    navigator?.pop()
                },
                titleGradient = LinearGradient,
                iconGradient = LinearGradient,
                titleStyle = LocalWitMeTheme.typography.medium20
            )
        }
    ) { contentPaddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPaddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .animateContentSize()
            ) {
                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .padding(horizontal = 16.dp)
                ) {
                    Spacer(modifier = Modifier.height(40.dp))
                    InputSectionView(
                        hintText = stringResource(Res.string.book_description_example),
                        titleText = stringResource(Res.string.book_description),
                        query = uiState.bookDescription,
                        maxLines = 3,
                        singleLine = false,
                        onQueryChanged = controller::onBookDescriptionQueryChanged
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    TitledSelector(
                        titleText = stringResource(Res.string.book_status),
                        enumValues = ReadingStatus.entries.toImmutableList(),
                        getDisplayName = ReadingStatus::displayName,
                        onValueSelected = controller::onBookStatusSelected,
                        selectedValue = uiState.selectedBookStatus
                    )
                    AnimatedContent(
                        targetState = uiState.selectedBookStatus,
                        label = "Book status change animation"
                    ) { status ->
                        Column {
                            when (status) {
                                ReadingStatus.ReadingNow, ReadingStatus.GoingToRead, null -> Unit
                                ReadingStatus.FinishedReading -> {
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Text(
                                        text = stringResource(Res.string.star_rating),
                                        style = LocalWitMeTheme.typography.semiBold16
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    StarRating(
                                        modifier = Modifier.fillMaxWidth(),
                                        rating = uiState.currentRating,
                                        onStarClick = controller::onStarClick
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    DefaultProgressButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(Res.string.next),
                        onClick = controller::onNextClick,
                        isLoading = uiState.isCreateButtonLoading,
                        isEnabled = with(uiState) {
                            when (selectedBookStatus) {
                                ReadingStatus.FinishedReading -> currentRating.isNotNull()
                                else -> true
                            } && bookDescription.isNotBlank()
                                    && selectedBookStatus.isNotNull()
                                    && isCreateButtonLoading.not()
                        }
                    )
                }
            }
        }
    }
}