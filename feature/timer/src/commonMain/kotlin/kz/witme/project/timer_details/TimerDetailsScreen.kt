package kz.witme.project.timer_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collectLatest
import kz.witme.project.book.domain.model.GetBook
import kz.witme.project.book.domain.model.ReadingStatus
import kz.witme.project.common_ui.base.BlurredGradientSphere
import kz.witme.project.common_ui.base.DefaultButton
import kz.witme.project.common_ui.base.ErrorAlert
import kz.witme.project.common_ui.extension.clickableWithPressedState
import kz.witme.project.common_ui.extension.collectAsStateWithLifecycle
import kz.witme.project.common_ui.screen.toolbarPaddings
import kz.witme.project.common_ui.spinner.Picker
import kz.witme.project.common_ui.spinner.rememberPickerState
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import kz.witme.project.component.BaseTimerBottomSheet
import kz.witme.project.navigation.result.ResultConstants
import kz.witme.project.navigation.result.setScreenResult
import kz.witme.project.timer.model.TimerHelperModel
import kz.witme.project.timer_details.component.ConfirmButton
import kz.witme.project.timer_details.component.InfoCard
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.good_boy
import witmekmp.core.common_ui.generated.resources.ic_back
import witmekmp.core.common_ui.generated.resources.ic_book_status
import witmekmp.core.common_ui.generated.resources.ic_note
import witmekmp.core.common_ui.generated.resources.ic_timer_btn
import witmekmp.core.common_ui.generated.resources.page
import witmekmp.core.common_ui.generated.resources.save
import witmekmp.core.common_ui.generated.resources.save_reading_session
import witmekmp.core.common_ui.generated.resources.session_time
import witmekmp.core.common_ui.generated.resources.status
import witmekmp.core.common_ui.generated.resources.str
import witmekmp.core.common_ui.generated.resources.timer_details_message
import witmekmp.core.common_ui.generated.resources.total_read
import witmekmp.core.common_ui.generated.resources.what_page_you_ended

class TimerDetailsScreen(
    private val book: GetBook,
    private val seconds: Long,
    private val notes: ImmutableList<String>
) : Screen {

    @Composable
    override fun Content() {
        val viewModel: TimerDetailsViewModel = koinScreenModel()
        val uiState: TimerDetailsUiState by viewModel.uiState.collectAsStateWithLifecycle()
        val bottomSheetNavigator = LocalBottomSheetNavigator.current
        val navigator = LocalNavigator.current

        val readBookBottomSheet = @Composable {
            ReadPageBottomSheet(
                previousPage = uiState.previousPage,
                maxPages = uiState.maxPages,
                currentPage = uiState.currentPage.takeIf { it != 0 },
                onSaveClick = viewModel::onCurrentPageSelect
            )
        }
        LaunchedEffect(Unit) {
            viewModel.initState(
                book = book,
                elapsedSeconds = seconds,
                notes = notes
            )
            viewModel.onPagesCountClick()
        }
        LaunchedEffect(viewModel.responseEvent) {
            viewModel.responseEvent.collectLatest { event ->
                when (event) {
                    TimerDetailsViewModel.ResponseEvent.HidePagePicker -> bottomSheetNavigator.hide()
                    TimerDetailsViewModel.ResponseEvent.NavigateToDashboard -> {
                        navigator?.setScreenResult(
                            ResultConstants.CREATE_TIMER_SESSION_SUCCESS,
                            true
                        )
                        navigator?.popUntilRoot()
                    }
                    TimerDetailsViewModel.ResponseEvent.ShowPagePicker -> {
                        bottomSheetNavigator.show(
                            BaseTimerBottomSheet(
                                titleId = Res.string.what_page_you_ended,
                                content = readBookBottomSheet
                            )
                        )
                    }
                }
            }
        }
        if (uiState.errorMessage.isNotBlank()) {
            ErrorAlert(
                errorText = uiState.errorMessage,
                onDismiss = viewModel::onErrorDismiss
            )
        }
        TimerDetailsScreenContent(
            controller = viewModel,
            uiState = uiState
        )
    }
}

@Composable
internal fun TimerDetailsScreenContent(
    controller: TimerDetailsController,
    uiState: TimerDetailsUiState
) {
    val navigator = LocalNavigator.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Icon(
                modifier = Modifier
                    .toolbarPaddings()
                    .padding(top = 16.dp)
                    .clickableWithPressedState(
                        onClick = {
                            navigator?.pop()
                        }
                    ),
                painter = painterResource(Res.drawable.ic_back),
                tint = LocalWitMeTheme.colors.primary400,
                contentDescription = "back button"
            )
        },
        contentColor = LocalWitMeTheme.colors.white
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            BlurredGradientSphere()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = stringResource(Res.string.save_reading_session),
                    style = LocalWitMeTheme.typography.semiBold32,
                    color = LocalWitMeTheme.colors.primary500
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = LocalWitMeTheme.colors.secondary500
                            )
                        ) {
                            //todo make it clickable and show book picker bottomsheet if navigated from bottom nav
                            append("${stringResource(Res.string.timer_details_message)} ")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = LocalWitMeTheme.colors.primary400,
                                textDecoration = TextDecoration.Underline
                            )
                        ) {
                            append(uiState.book?.name)
                        }
                    },
                    style = LocalWitMeTheme.typography.regular16
                )
                Spacer(modifier = Modifier.height(26.dp))
                Text(
                    text = stringResource(
                        Res.string.good_boy,
                        "${uiState.currentPage - uiState.previousPage}"
                    ),
                    style = LocalWitMeTheme.typography.regular16,
                    color = LocalWitMeTheme.colors.secondary500
                )
                Spacer(modifier = Modifier.height(26.dp))
                Row(
                    modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)
                ) {
                    Column(
                        modifier = Modifier.weight(0.75f)
                    ) {
                        InfoCard(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = controller::onTimerClick,
                            title = stringResource(Res.string.session_time),
                            subtitle = with(
                                TimerHelperModel.getLeftTimerHelperModel(uiState.timerSeconds)
                            ) {
                                "$hours:$minutes:$seconds"
                            },
                            icon = painterResource(Res.drawable.ic_timer_btn)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        InfoCard(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = controller::onPagesCountClick,
                            title = stringResource(Res.string.total_read),
                            subtitle = "${stringResource(Res.string.str)} ${uiState.currentPage}/${uiState.maxPages}",
                            icon = painterResource(Res.drawable.ic_note)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        InfoCard(
                            modifier = Modifier.fillMaxWidth(),
                            title = stringResource(Res.string.status),
                            subtitle = (uiState.book?.readingStatus
                                ?: ReadingStatus.ReadingNow).displayName,
                            icon = painterResource(Res.drawable.ic_book_status)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    ConfirmButton(
                        modifier = Modifier
                            .weight(0.25f)
                            .fillMaxHeight(),
                        onClick = controller::onConfirmClick,
                        isLoading = uiState.isLoading
                    )
                }
            }
        }
    }
}

@Composable
private fun ReadPageBottomSheet(
    onSaveClick: (chosenPage: String) -> Unit,
    currentPage: Int?,
    previousPage: Int,
    maxPages: Int
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val items = remember {
            (previousPage..maxPages).toList()
        }
        val pickerState = rememberPickerState()
        Picker(
            items = items
                .map { "$it ${stringResource(Res.string.page)}" }
                .toImmutableList(),
            state = pickerState,
            visibleItemsCount = 7,
            startIndex = items.indexOf(
                items.find { it == currentPage }
            )
        )
        Spacer(modifier = Modifier.height(24.dp))
        DefaultButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            onClick = {
                onSaveClick(pickerState.selectedItem)
            },
            text = stringResource(Res.string.save)
        )
    }
}