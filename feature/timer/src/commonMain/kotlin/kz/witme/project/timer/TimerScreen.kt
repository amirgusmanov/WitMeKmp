package kz.witme.project.timer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import kz.witme.project.common_ui.extension.clickableWithPressedState
import kz.witme.project.common_ui.screen.toolbarPaddings
import kz.witme.project.common_ui.theme.LinearGradient
import kz.witme.project.common_ui.theme.LocalWitMeTheme
import kz.witme.project.timer.component.PlayButton
import kz.witme.project.timer.component.TimerButton
import kz.witme.project.timer.component.TimerView
import kz.witme.project.timer.model.TimerHelperModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.end_session
import witmekmp.core.common_ui.generated.resources.ic_back

class TimerScreen : Screen {

    @Composable
    override fun Content() {
        TimerScreenContent()
    }
}

//todo imlement logic with timer in a viewmodel, bottom sheet with books list, alert
@Composable
internal fun TimerScreenContent() {
    val bottomSheetNavigator = LocalBottomSheetNavigator.current
    var isRunning by remember {
        mutableStateOf(false)
    }
    val timerHelper = remember {
        TimerHelperModel(
            hours = "00",
            minutes = "00",
            seconds = "00"
        )
    }
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
                isRunning = isRunning,
                onTimerClick = {
                    isRunning = !isRunning
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            TimerView(
                hours = timerHelper.hours,
                minutes = timerHelper.minutes,
                seconds = timerHelper.seconds
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
                    onClick = {

                    }
                )
                Spacer(modifier = Modifier.width(12.dp))
                TimerButton(
                    modifier = Modifier.weight(1f),
                    buttonText = stringResource(Res.string.end_session),
                    onClick = {

                    }
                )
            }
        }
    }
}