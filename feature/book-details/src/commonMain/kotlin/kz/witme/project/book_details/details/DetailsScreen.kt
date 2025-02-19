package kz.witme.project.book_details.details

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.collectLatest
import kz.witme.project.book.domain.model.GetBook
import kz.witme.project.book.domain.model.GetBookSessionDetails
import kz.witme.project.book_details.component.BookDataSectionView
import kz.witme.project.book_details.details.model.SessionItem
import kz.witme.project.common_ui.base.ErrorAlert
import kz.witme.project.common_ui.base.TopCurvedCircle
import kz.witme.project.common_ui.extension.clickableWithPressedState
import kz.witme.project.common_ui.extension.clickableWithoutRipple
import kz.witme.project.common_ui.extension.collectAsStateWithLifecycle
import kz.witme.project.common_ui.theme.DefaultRoundedShape
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import kz.witme.project.navigation.Destination
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.core.parameter.parametersOf
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.day
import witmekmp.core.common_ui.generated.resources.description
import witmekmp.core.common_ui.generated.resources.ic_arrow_right
import witmekmp.core.common_ui.generated.resources.ic_back
import witmekmp.core.common_ui.generated.resources.ic_book_session
import witmekmp.core.common_ui.generated.resources.min

class DetailsScreen(private val book: GetBook) : Screen {

    @Composable
    override fun Content() {
        val controller: DetailsViewModel = koinScreenModel {
            parametersOf(book)
        }
        val uiState: DetailsUiState by controller.uiState.collectAsStateWithLifecycle()
        val navigator = LocalNavigator.current

        LaunchedEffect(controller.responseEventFlow) {
            controller.responseEventFlow.collectLatest { event ->
                when (event) {
                    DetailsViewModel.ResponseEvent.NavigateBack -> {
                        navigator?.pop()
                    }

                    is DetailsViewModel.ResponseEvent.NavigateToSessionDetails -> {
                        ScreenRegistry.get(
                            Destination.BookSessionDetails(
                                bookSessionDetails = event.session,
                                bookName = event.bookName
                            )
                        ).let {
                            navigator?.push(it)
                        }
                    }
                }
            }
        }
        DetailsScreenContent(
            controller = controller,
            uiState = uiState
        )
    }
}

@Composable
internal fun DetailsScreenContent(
    controller: DetailsController,
    uiState: DetailsUiState
) {
    val navigator = LocalNavigator.current
    val scrollState = rememberScrollState(initial = 0)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = LocalWitMeTheme.colors.white
    ) { contentPaddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPaddingValues)
        ) {
            TopCurvedCircle(
                modifier = Modifier.graphicsLayer {
                    translationY = -scrollState.value.toFloat()
                }
            )
            AnimatedContent(
                targetState = uiState
            ) { state ->
                when (state) {
                    DetailsUiState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(40.dp).align(Alignment.Center),
                                color = LocalWitMeTheme.colors.primary400
                            )
                        }
                    }

                    is DetailsUiState.Error -> {
                        ErrorAlert(
                            errorText = state.message,
                            onDismiss = controller::onErrorDismiss
                        )
                    }

                    is DetailsUiState.Data -> {
                        DetailsContent(
                            controller = controller,
                            uiState = state,
                            scrollState = scrollState
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clickableWithPressedState(
                        onClick = {
                            navigator?.pop()
                        }
                    ),
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clip(DefaultRoundedShape)
                        .background(
                            color = if (scrollState.value != 0) {
                                LocalWitMeTheme.colors.primary400
                            } else {
                                Color.Transparent
                            }
                        )
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.Center),
                        painter = painterResource(Res.drawable.ic_back),
                        tint = LocalWitMeTheme.colors.white,
                        contentDescription = "back button"
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailsContent(
    controller: DetailsController,
    uiState: DetailsUiState.Data,
    scrollState: ScrollState
) {
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = uiState.name,
            style = LocalWitMeTheme.typography.semiBold32,
            color = LocalWitMeTheme.colors.white
        )
        Spacer(modifier = Modifier.height(24.dp))
        BookDataSectionView(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            photo = uiState.photo,
            author = uiState.author,
            maxPages = uiState.maxPages,
            name = uiState.name
        )
        Spacer(modifier = Modifier.height(18.dp))
        DescriptionSectionView(bookDescription = uiState.description)
        Spacer(modifier = Modifier.height(16.dp))
        if (uiState.details.isNotEmpty()) {
            SessionsView(
                sessions = uiState.details,
                onSessionClick = controller::onSessionClick
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun DescriptionSectionView(
    modifier: Modifier = Modifier,
    bookDescription: String
) {
    val isDescriptionVisible = remember { mutableStateOf(false) }
    ElevatedCard(
        modifier = modifier
            .animateContentSize()
            .padding(vertical = 4.dp, horizontal = 2.dp)
            .clickableWithoutRipple {
                isDescriptionVisible.value = !isDescriptionVisible.value
            },
        colors = CardDefaults.cardColors(containerColor = LocalWitMeTheme.colors.white),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = DefaultRoundedShape,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.description),
                    style = LocalWitMeTheme.typography.semiBold16,
                    color = LocalWitMeTheme.colors.secondary500
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    modifier = Modifier.rotate(if (isDescriptionVisible.value) -90f else 90f),
                    painter = painterResource(Res.drawable.ic_arrow_right),
                    tint = LocalWitMeTheme.colors.secondary400,
                    contentDescription = "open or close description"
                )
            }
            if (isDescriptionVisible.value) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = bookDescription,
                    style = LocalWitMeTheme.typography.regular16,
                    color = LocalWitMeTheme.colors.secondary500
                )
            }
        }
    }
}

//todo rewrite it to lazy column with headers
@Composable
private fun SessionsView(
    modifier: Modifier = Modifier,
    sessions: ImmutableList<SessionItem>,
    onSessionClick: (GetBookSessionDetails) -> Unit
) {
    ElevatedCard(
        modifier = modifier
            .animateContentSize()
            .padding(vertical = 4.dp, horizontal = 2.dp),
        colors = CardDefaults.cardColors(containerColor = LocalWitMeTheme.colors.white),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = DefaultRoundedShape,
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            sessions.forEach { session ->
                when (session) {
                    is SessionItem.BookSessionDetails -> SessionItem(
                        session.details,
                        onSessionClick = {
                            onSessionClick(session.details)
                        }
                    )

                    is SessionItem.Date -> SessionDate(session)
                }
            }
        }
    }
}

@Composable
private fun SessionDate(
    sessionDate: SessionItem.Date
) {
    Row(
        modifier = Modifier.padding(vertical = 9.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(Res.string.day, sessionDate.day),
            style = LocalWitMeTheme.typography.semiBold16,
            color = LocalWitMeTheme.colors.secondary400
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = sessionDate.date,
            style = LocalWitMeTheme.typography.regular16,
            color = LocalWitMeTheme.colors.secondary400
        )
    }
}

@Composable
private fun SessionItem(
    session: GetBookSessionDetails,
    onSessionClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(vertical = 9.dp)
            .clickableWithoutRipple(onClick = onSessionClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            if (session.endSessionTime.isNotBlank()) {
                Text(
                    text = session.endSessionTime,
                    style = LocalWitMeTheme.typography.regular12,
                    color = LocalWitMeTheme.colors.secondary400
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_book_session),
                    contentDescription = null,
                    tint = LocalWitMeTheme.colors.primary400,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = session.fromPageToPage,
                    style = LocalWitMeTheme.typography.regular16,
                    color = LocalWitMeTheme.colors.primary400
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "(${session.getMinutes()} ${stringResource(Res.string.min)})",
                    style = LocalWitMeTheme.typography.regular12,
                    color = LocalWitMeTheme.colors.secondary400
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(Res.drawable.ic_arrow_right),
            modifier = Modifier.size(24.dp),
            tint = LocalWitMeTheme.colors.primary400,
            contentDescription = "Go to session details"
        )
    }
}