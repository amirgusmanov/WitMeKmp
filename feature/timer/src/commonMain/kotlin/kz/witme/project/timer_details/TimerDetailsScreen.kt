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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import kz.witme.project.common_ui.extension.clickableWithPressedState
import kz.witme.project.common_ui.screen.toolbarPaddings
import kz.witme.project.common_ui.theme.LocalWitMeTheme
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
import witmekmp.core.common_ui.generated.resources.save_reading_session
import witmekmp.core.common_ui.generated.resources.session_time
import witmekmp.core.common_ui.generated.resources.status
import witmekmp.core.common_ui.generated.resources.str
import witmekmp.core.common_ui.generated.resources.timer_details_message
import witmekmp.core.common_ui.generated.resources.total_read

class TimerDetailsScreen : Screen {

    @Composable
    override fun Content() {
        TimerDetailsScreenContent(
            bookName = "Some book interesting book name"
        )
    }
}

@Composable
internal fun TimerDetailsScreenContent(
    bookName: String
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Icon(
                modifier = Modifier
                    .toolbarPaddings()
                    .padding(top = 16.dp)
                    .clickableWithPressedState(
                        onClick = {

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
                        append(stringResource(Res.string.timer_details_message))
                        append(" $bookName")
                    },
                    style = LocalWitMeTheme.typography.regular16,
                    color = LocalWitMeTheme.colors.secondary500
                )
                Spacer(modifier = Modifier.height(26.dp))
                Text(
                    text = stringResource(Res.string.good_boy, 20),
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
                            onClick = {},
                            title = stringResource(Res.string.session_time),
                            subtitle = "00:10:03",
                            icon = painterResource(Res.drawable.ic_timer_btn)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        InfoCard(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {},
                            title = stringResource(Res.string.total_read),
                            subtitle = "${stringResource(Res.string.str)} 40/304",
                            icon = painterResource(Res.drawable.ic_note)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        InfoCard(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {},
                            title = stringResource(Res.string.status),
                            subtitle = "Читаю",
                            icon = painterResource(Res.drawable.ic_book_status)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    ConfirmButton(
                        modifier = Modifier
                            .weight(0.25f)
                            .fillMaxHeight(),
                        onClick = {

                        }
                    )
                }
            }
        }
    }
}