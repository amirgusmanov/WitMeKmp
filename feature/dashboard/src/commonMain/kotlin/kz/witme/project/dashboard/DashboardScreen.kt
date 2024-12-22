package kz.witme.project.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.collections.immutable.toImmutableList
import kz.witme.project.common_ui.base.BlurredGradientSphere
import kz.witme.project.common_ui.base.DefaultToolbar
import kz.witme.project.common_ui.base.PiggySmileView
import kz.witme.project.common_ui.base.TopCurvedCircle
import kz.witme.project.common_ui.screen.toolbarPaddings
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import kz.witme.project.dashboard.component.BookPager
import kz.witme.project.dashboard.component.ToReadBooksView
import org.jetbrains.compose.resources.stringResource
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.currently_reading
import witmekmp.core.common_ui.generated.resources.to_read

class DashboardScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel: DashboardViewModel = koinScreenModel()

        DashboardScreenContent()
    }
}

@Composable
internal fun DashboardScreenContent() {
    val navigator = LocalNavigator.current
    val scrollState = rememberScrollState()
//    val createBookScreen = rememberScreen(Destination.CreateBook)
    val pagerState = rememberPagerState {
        MockBooksProvider().mockBooks.size
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
            BlurredGradientSphere()
            Column(
                modifier = Modifier.verticalScroll(scrollState)
            ) {
                PiggySmileView(modifier = Modifier.padding(vertical = 16.dp))
                Spacer(modifier = Modifier.height(8.dp))
                BookPager(
                    modifier = Modifier.fillMaxWidth(),
                    books = MockBooksProvider().mockBooks,
                    pagerState = pagerState
                )
                Spacer(modifier = Modifier.height(60.dp))
                Text(
                    text = stringResource(Res.string.to_read),
                    color = LocalWitMeTheme.colors.secondary500,
                    style = LocalWitMeTheme.typography.semiBold20,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                ToReadBooksView(
                    bookEntries = MockBooksProvider().mockBooks
                        .take(4)
                        .toEntries()
                        .toImmutableList(),
                    modifier = Modifier.fillMaxWidth(),
                    onBookClick = {},
                    onEmptyClick = {
//                        navigator?.push(createBookScreen)
                    }
                )
            }
        }
    }
}