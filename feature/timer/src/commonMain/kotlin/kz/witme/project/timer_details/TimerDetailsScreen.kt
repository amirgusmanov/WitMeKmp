package kz.witme.project.timer_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.ic_back
import witmekmp.core.common_ui.generated.resources.save_reading_session
import witmekmp.core.common_ui.generated.resources.timer_details_message

class TimerDetailsScreen : Screen {

    @Composable
    override fun Content() {

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
                tint = LocalWitMeTheme.colors.white,
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
        }
    }
}