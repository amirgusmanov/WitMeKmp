package kz.witme.project.book_details.details

import androidx.compose.animation.AnimatedContent
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
import androidx.compose.material.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import kz.witme.project.book_details.component.BookDataSectionView
import kz.witme.project.common_ui.base.TopCurvedCircle
import kz.witme.project.common_ui.extension.clickableWithPressedState
import kz.witme.project.common_ui.extension.collectAsStateWithLifecycle
import kz.witme.project.common_ui.screen.toolbarPaddings
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import org.jetbrains.compose.resources.painterResource
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.ic_back

class DetailsScreen(private val bookId: String) : Screen {

    @Composable
    override fun Content() {
        val controller: DetailsViewModel = koinScreenModel()
        val uiState: DetailsUiState by controller.uiState.collectAsStateWithLifecycle()

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
                tint = LocalWitMeTheme.colors.white,
                contentDescription = "back button"
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
            AnimatedContent(
                targetState = uiState
            ) { state ->
                when (state) {
                    DetailsUiState.Loading -> TODO()
                    is DetailsUiState.Data -> DetailsContent(
                        controller = controller,
                        uiState = state
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailsContent(
    controller: DetailsController,
    uiState: DetailsUiState.Data
) {
    val isDescriptionExpanded = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = uiState.name,
            style = LocalWitMeTheme.typography.semiBold32,
            color = LocalWitMeTheme.colors.white
        )
        Spacer(modifier = Modifier.height(24.dp))
        BookDataSectionView(
            modifier = Modifier.fillMaxWidth(),
            photo = uiState.photo,
            author = uiState.author,
            maxPages = uiState.maxPages
        )
        Spacer(modifier = Modifier.height(18.dp))
    }
}