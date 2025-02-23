@file:OptIn(InternalVoyagerApi::class)

package kz.witme.project.timer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import cafe.adriel.voyager.navigator.internal.BackHandler
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.collectLatest
import kz.witme.project.book.domain.model.GetBook
import kz.witme.project.common_ui.base.DefaultButton
import kz.witme.project.common_ui.extension.clickableWithPressedState
import kz.witme.project.common_ui.extension.clickableWithoutRipple
import kz.witme.project.common_ui.extension.collectAsStateWithLifecycle
import kz.witme.project.common_ui.screen.toolbarPaddings
import kz.witme.project.common_ui.shimmer.ShimmerView
import kz.witme.project.common_ui.theme.LinearGradient
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import kz.witme.project.component.BaseTimerBottomSheet
import kz.witme.project.component.TimerAlert
import kz.witme.project.navigation.Destination
import kz.witme.project.navigation.result.ResultConstants
import kz.witme.project.navigation.result.getScreenResult
import kz.witme.project.navigation.tabs.Home
import kz.witme.project.timer.component.PlayButton
import kz.witme.project.timer.component.TimerButton
import kz.witme.project.timer.component.TimerView
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.add_note
import witmekmp.core.common_ui.generated.resources.chevron_right
import witmekmp.core.common_ui.generated.resources.end_session
import witmekmp.core.common_ui.generated.resources.ic_back
import witmekmp.core.common_ui.generated.resources.no_books
import witmekmp.core.common_ui.generated.resources.save_note
import witmekmp.core.common_ui.generated.resources.timer_alert_message
import witmekmp.core.common_ui.generated.resources.what_book_you_read

class TimerScreen(
    private val bookId: String? = null,
    private val isNavigatedFromTabs: Boolean
) : Screen {

    @Composable
    override fun Content() {
        val viewModel: TimerViewModel = koinScreenModel()
        val uiState: TimerUiState by viewModel.uiState.collectAsStateWithLifecycle()
        val bottomSheetNavigator = LocalBottomSheetNavigator.current
        val navigator = LocalNavigator.current
        val tabsNavigator = LocalTabNavigator.current
        val hapticFeedback = LocalHapticFeedback.current
        val noteBottomSheet = @Composable {
            NoteBottomSheet(
                onQueryChanged = viewModel::onNoteChanged,
                onSaveNoteClick = {
                    viewModel.onNoteAdded(uiState.tempNote)
                    if (uiState.tempNote.isNotBlank()) {
                        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                        bottomSheetNavigator.hide()
                    }
                },
                noteText = uiState.tempNote
            )
        }
        val booksBottomSheet = @Composable {
            BooksBottomSheet(
                books = uiState.books,
                selectedBookId = uiState.selectedBookId,
                areBooksLoading = uiState.areBooksLoading,
                onBookClick = {
                    viewModel.onBookChoose(it)
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                }
            )
        }
        fun onBackEvent() {
            if (viewModel.elapsedSeconds != 0L) {
                viewModel.handleAlert(true)
                return
            }
            viewModel.restartTimer()
            if (isNavigatedFromTabs) tabsNavigator.current = Home else navigator?.pop()
        }
        LaunchedEffect(Unit) {
            if (navigator?.getScreenResult<Boolean>(ResultConstants.CREATE_TIMER_SESSION_SUCCESS) == true) {
                viewModel.restartTimer()
            }
        }
        LaunchedEffect(viewModel.responseEvent) {
            viewModel.responseEvent.collectLatest { event ->
                when (event) {
                    TimerViewModel.ResponseEvent.NavigateToDetails -> {
                        if (viewModel.elapsedSeconds == 0L) return@collectLatest
                        if (bookId == null && uiState.selectedBookId.isBlank()) {
                            bottomSheetNavigator.show(
                                BaseTimerBottomSheet(
                                    titleId = Res.string.what_book_you_read,
                                    content = booksBottomSheet
                                )
                            )
                        } else {
                            bottomSheetNavigator.hide()
                            viewModel.getSelectedBook(bookId ?: uiState.selectedBookId)?.let {
                                ScreenRegistry.get(
                                    Destination.TimerDetails(
                                        book = it,
                                        seconds = viewModel.elapsedSeconds,
                                        notes = viewModel.notesList,
                                        isNavigatedFromTabs = isNavigatedFromTabs
                                    )
                                ).let { screen ->
                                    navigator?.push(screen)
                                }
                            }
                        }
                    }

                    TimerViewModel.ResponseEvent.ShowBookNoteSheet -> {
                        bottomSheetNavigator.show(
                            BaseTimerBottomSheet(
                                titleId = Res.string.add_note,
                                content = noteBottomSheet
                            )
                        )
                    }
                }
            }
        }
        if (uiState.isTimerAlertVisible) {
            TimerAlert(
                errorText = stringResource(Res.string.timer_alert_message),
                onDismissRequest = {
                    viewModel.handleAlert(false)
                },
                onButtonClick = {
                    viewModel.handleAlert(false)
                    viewModel.restartTimer()
                    onBackEvent()
                }
            )
        }
        BackHandler(enabled = true, onBack = ::onBackEvent)
        TimerScreenContent(
            controller = viewModel,
            uiState = uiState,
            onBackClick = ::onBackEvent
        )
    }
}

@Composable
internal fun TimerScreenContent(
    controller: TimerController,
    uiState: TimerUiState,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = LinearGradient)
    ) {
        Icon(
            modifier = Modifier
                .toolbarPaddings()
                .padding(top = 16.dp)
                .align(Alignment.TopStart)
                .clickableWithPressedState(onClick = onBackClick),
            painter = painterResource(Res.drawable.ic_back),
            tint = LocalWitMeTheme.colors.white,
            contentDescription = "back button"
        )
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PlayButton(
                isRunning = uiState.isRunning,
                onTimerClick = controller::onTimerClick
            )
            Spacer(modifier = Modifier.height(10.dp))
            TimerView(
                hours = uiState.timer.hours,
                minutes = uiState.timer.minutes,
                seconds = uiState.timer.seconds
            )
            Spacer(modifier = Modifier.height(40.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                TimerButton(
                    modifier = Modifier.weight(1f),
                    buttonText = stringResource(Res.string.end_session),
                    onClick = controller::onEndSessionClick
                )
                Spacer(modifier = Modifier.width(12.dp))
                TimerButton(
                    modifier = Modifier.weight(1f),
                    buttonText = stringResource(Res.string.add_note),
                    onClick = controller::onAddNoteClick
                )
            }
        }
    }
}

@Composable
private fun NoteBottomSheet(
    noteText: String,
    onQueryChanged: (String) -> Unit,
    onSaveNoteClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = noteText,
            onValueChange = onQueryChanged,
            placeholder = {
                Text(
                    text = noteText,
                    style = LocalWitMeTheme.typography.regular16,
                    color = LocalWitMeTheme.colors.secondary300
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            textStyle = LocalWitMeTheme.typography.regular16.copy(
                color = LocalWitMeTheme.colors.black
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = LocalWitMeTheme.colors.white,
                focusedLabelColor = LocalWitMeTheme.colors.white,
                focusedIndicatorColor = LocalWitMeTheme.colors.white,
                unfocusedIndicatorColor = LocalWitMeTheme.colors.white,
                cursorColor = LocalWitMeTheme.colors.black,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        DefaultButton(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(bottom = 16.dp)
                .imePadding(),
            onClick = onSaveNoteClick,
            text = stringResource(Res.string.save_note)
        )
    }
}


@Composable
private fun BooksBottomSheet(
    books: ImmutableList<GetBook>,
    selectedBookId: String?,
    areBooksLoading: Boolean,
    onBookClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().navigationBarsPadding()
    ) {
        if (books.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(Res.string.no_books),
                    style = LocalWitMeTheme.typography.regular16,
                    color = LocalWitMeTheme.colors.secondary400,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().height(200.dp)
            ) {
                if (areBooksLoading) {
                    items(
                        count = 7
                    ) {
                        ShimmerView(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .height(40.dp)
                        )
                    }
                } else {
                    items(
                        items = books,
                        key = GetBook::id
                    ) { book ->
                        BookItem(
                            book = book,
                            isSelected = book.id == selectedBookId,
                            onBookClick = onBookClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BookItem(
    book: GetBook,
    isSelected: Boolean,
    onBookClick: (String) -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = if (isSelected) {
                        LocalWitMeTheme.colors.primary400
                    } else {
                        LocalWitMeTheme.colors.white
                    }
                )
                .padding(12.dp)
                .clickableWithoutRipple { onBookClick(book.id) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = book.name,
                style = LocalWitMeTheme.typography.regular16,
                color = if (isSelected) {
                    LocalWitMeTheme.colors.white
                } else {
                    LocalWitMeTheme.colors.black
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(Res.drawable.chevron_right),
                contentDescription = null,
                tint = if (isSelected) {
                    LocalWitMeTheme.colors.white
                } else {
                    LocalWitMeTheme.colors.black
                }
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(LocalWitMeTheme.colors.secondary100)
        )
    }
}