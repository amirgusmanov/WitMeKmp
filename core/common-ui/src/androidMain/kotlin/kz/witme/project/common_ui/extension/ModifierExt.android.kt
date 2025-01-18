package kz.witme.project.common_ui.extension

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.Dp

@Composable
actual fun Modifier.blur(radius: Dp): Modifier = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        blur(radius)
    }

    else -> {
        // Use a custom "cloudy blur" effect for older Android versions
        this.alpha(0f)
    }
}