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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import kotlinx.coroutines.flow.collectLatest
import kz.witme.project.common.extension.isNull
import kz.witme.project.common_ui.base.DefaultButton
import kz.witme.project.common_ui.extension.clickableWithPressedState
import kz.witme.project.common_ui.extension.collectAsStateWithLifecycle
import kz.witme.project.common_ui.screen.toolbarPaddings
import kz.witme.project.common_ui.theme.LinearGradient
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import kz.witme.project.component.BaseTimerBottomSheet
import kz.witme.project.navigation.Destination
import kz.witme.project.timer.component.PlayButton
import kz.witme.project.timer.component.TimerButton
import kz.witme.project.timer.component.TimerView
import kz.witme.project.timer.model.TimerViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.add_note
import witmekmp.core.common_ui.generated.resources.end_session
import witmekmp.core.common_ui.generated.resources.ic_back
import witmekmp.core.common_ui.generated.resources.save_note
import witmekmp.core.common_ui.generated.resources.what_book_you_read

class TimerScreen(private val bookId: String? = null) : Screen {

    @Composable
    override fun Content() {
        val viewModel: TimerViewModel = koinScreenModel()
        val uiState: TimerUiState by viewModel.uiState.collectAsStateWithLifecycle()
        val bottomSheetNavigator = LocalBottomSheetNavigator.current
        val navigator = LocalNavigator.current
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
        val booksBottomSheet = @Composable { BooksBottomSheet() }
        val detailsScreen = rememberScreen(Destination.TimerDetails)
        LaunchedEffect(viewModel.responseEvent) {
            viewModel.responseEvent.collectLatest { event ->
                when (event) {
                    TimerViewModel.ResponseEvent.NavigateToDetails -> {
                        if (bookId.isNull()) {
                            bottomSheetNavigator.show(
                                BaseTimerBottomSheet(
                                    titleId = Res.string.what_book_you_read,
                                    content = booksBottomSheet
                                )
                            )
                        } else {
                            navigator?.push(detailsScreen)
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
        TimerScreenContent(
            controller = viewModel,
            uiState = uiState
        )
    }
}

@Composable
internal fun TimerScreenContent(
    controller: TimerController,
    uiState: TimerUiState
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
                .clickableWithPressedState(
                    onClick = {

                    }
                ),
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
                .padding(bottom = 16.dp)
                .imePadding(),
            onClick = onSaveNoteClick,
            text = stringResource(Res.string.save_note)
        )
    }
}


@Composable
private fun BooksBottomSheet() {

}