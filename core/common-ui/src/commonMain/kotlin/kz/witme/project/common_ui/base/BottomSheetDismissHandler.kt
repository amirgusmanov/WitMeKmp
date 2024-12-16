package kz.witme.project.common_ui.base

import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.collectIndexed

@Composable
inline fun BottomSheetDismissHandler(
    sheetState: ModalBottomSheetState,
    crossinline onDismiss: () -> Unit
) {
    LaunchedEffect(sheetState) {
        snapshotFlow { sheetState.currentValue }
            .collectIndexed { index, value ->
                // skip first value
                if (index == 0) {
                    return@collectIndexed
                }
                if (value == ModalBottomSheetValue.Hidden) {
                    onDismiss()
                }
            }
    }
}
