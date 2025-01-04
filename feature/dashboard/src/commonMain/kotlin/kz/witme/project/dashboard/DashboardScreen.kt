package kz.witme.project.dashboard

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.coroutines.flow.collectLatest
import kz.witme.project.common_ui.base.DefaultToolbar
import kz.witme.project.common_ui.base.ErrorAlert
import kz.witme.project.common_ui.base.PiggySmileView
import kz.witme.project.common_ui.base.TopCurvedCircle
import kz.witme.project.common_ui.extension.collectAsStateWithLifecycle
import kz.witme.project.common_ui.screen.toolbarPaddings
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import kz.witme.project.navigation.Destination
import org.jetbrains.compose.resources.stringResource
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.currently_reading

class DashboardScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel: DashboardViewModel = koinScreenModel()
        val uiState: DashboardUiState by viewModel.uiState.collectAsStateWithLifecycle()

        DashboardScreenContent(
            controller = viewModel,
            uiState = uiState
        )
    }
}

@Composable
internal fun DashboardScreenContent(
    controller: DashboardController,
    uiState: DashboardUiState
) {
    val navigator = LocalNavigator.current
    val scrollState = rememberScrollState()
    val createBookScreen = rememberScreen(Destination.CreateBook)
    val currentlyReadingBooksPager = rememberPagerState(
        pageCount = uiState.currentlyReadingBooks::size
    )
    if (uiState.errorMessage.isNotBlank()) {
        ErrorAlert(
            errorText = uiState.errorMessage,
            onDismiss = controller::onErrorDismiss
        )
    }
    LaunchedEffect(controller.responseEvent) {
        controller.responseEvent.collectLatest { event ->
            when (event) {
                is DashboardViewModel.DashboardResponseEvent.NavigateToTimer -> {
                    ScreenRegistry.get(Destination.Timer(event.bookId)).let {
                        navigator?.push(it)
                    }
                }

                DashboardViewModel.DashboardResponseEvent.NavigateToCreateBook -> {
                    navigator?.push(createBookScreen)
                }

                is DashboardViewModel.DashboardResponseEvent.NavigateToDetails -> {
                    ScreenRegistry.get(Destination.BookDetails(event.book)).let {
                        navigator?.push(it)
                    }
                }
            }
        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            DefaultToolbar(
                toolbarTitle = stringResource(Res.string.currently_reading),
                modifier = Modifier.toolbarPaddings()
            )
        },
        contentWindowInsets = WindowInsets.statusBars,
        containerColor = LocalWitMeTheme.colors.white
    ) { contentPaddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPaddingValues)
        ) {
            TopCurvedCircle()
            Column(
                modifier = Modifier.verticalScroll(scrollState)
            ) {
                PiggySmileView(modifier = Modifier.padding(vertical = 16.dp))
                Spacer(modifier = Modifier.height(8.dp))
                AnimatedContent(
                    targetState = uiState.isLoading
                ) { isLoading ->
                    if (isLoading) {
                        DashboardLoadingContent(modifier = Modifier.fillMaxSize())
                    } else {
                        DashboardContent(
                            currentlyReadingBooks = uiState.currentlyReadingBooks,
                            toReadBooks = uiState.toReadBooks,
                            finishedReadingBooks = uiState.finishedReadingBooks,
                            currentlyReadingBooksPager = currentlyReadingBooksPager,
                            onEmptyClick = controller::onEmptyClick,
                            onBookClick = controller::onBookClick,
                            onTimerClick = controller::onTimerClick
                        )
                    }
                }
            }
        }
    }
}