package kz.witme.project.book_details.details_session

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import kz.witme.project.book.domain.model.GetBookSessionDetails
import kz.witme.project.common_ui.base.TopCurvedCircle
import kz.witme.project.common_ui.extension.clickableWithPressedState
import kz.witme.project.common_ui.extension.collectAsStateWithLifecycle
import kz.witme.project.common_ui.screen.toolbarPaddings
import kz.witme.project.common_ui.theme.DefaultRoundedShape
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource
import org.koin.core.parameter.parametersOf
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.ic_back
import witmekmp.core.common_ui.generated.resources.ic_timer
import witmekmp.core.common_ui.generated.resources.minutes_pl
import witmekmp.core.common_ui.generated.resources.no_notes
import witmekmp.core.common_ui.generated.resources.notes
import witmekmp.core.common_ui.generated.resources.total_read_time

class SessionDetailsInfoScreen(
    private val session: GetBookSessionDetails,
    private val bookName: String
) : Screen {

    @Composable
    override fun Content() {
        val controller: SessionDetailsInfoViewModel = koinScreenModel {
            parametersOf(session, bookName)
        }
        val uiState by controller.uiState.collectAsStateWithLifecycle()
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
            containerColor = LocalWitMeTheme.colors.white
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                TopCurvedCircle()
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Spacer(modifier = Modifier.height(48.dp))
                    //todo localize date with date utils
                    Text(
                        text = uiState.date,
                        style = LocalWitMeTheme.typography.bold24,
                        color = LocalWitMeTheme.colors.white
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = uiState.bookName,
                        style = LocalWitMeTheme.typography.regular16,
                        color = LocalWitMeTheme.colors.white
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    ElevatedCard(
                        modifier = Modifier.padding(4.dp),
                        colors = CardDefaults.cardColors(containerColor = LocalWitMeTheme.colors.white),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        shape = DefaultRoundedShape,
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.weight(1f))
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = stringResource(Res.string.total_read_time),
                                    style = LocalWitMeTheme.typography.regular16,
                                    color = LocalWitMeTheme.colors.secondary400,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = pluralStringResource(
                                        Res.plurals.minutes_pl,
                                        quantity = uiState.elapsedTime,
                                        uiState.elapsedTime
                                    ),
                                    style = LocalWitMeTheme.typography.semiBold24,
                                    color = LocalWitMeTheme.colors.primary400,
                                    textAlign = TextAlign.Center
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                painter = painterResource(Res.drawable.ic_timer),
                                tint = LocalWitMeTheme.colors.primary400,
                                contentDescription = null,
                                modifier = Modifier.size(58.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        text = if (uiState.notes.isNotEmpty()) {
                            stringResource(Res.string.notes)
                        } else {
                            stringResource(Res.string.no_notes)
                        },
                        style = LocalWitMeTheme.typography.semiBold24,
                        color = LocalWitMeTheme.colors.secondary500,
                        modifier = Modifier.padding(start = 2.dp)
                    )
                    Spacer(modifier = Modifier.height(22.dp))
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(items = uiState.notes) { note ->
                            ElevatedCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .padding(horizontal = 2.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = LocalWitMeTheme.colors.white
                                ),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                shape = DefaultRoundedShape,
                            ) {
                                Text(
                                    text = "\"$note\"",
                                    style = LocalWitMeTheme.typography.regular16,
                                    color = LocalWitMeTheme.colors.secondary500,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}