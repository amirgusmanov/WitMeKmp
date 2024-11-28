package kz.witme.project.common_ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
fun WitMeTheme(content: @Composable () -> Unit) {
    val color = LocalColorProvider provides lightPalette
    val typography = LocalTypographyProvider provides Typography()

    CompositionLocalProvider(
        color,
        typography,
        content = content,
    )
}

object LocalWitMeTheme {
    val colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalColorProvider.current

    val typography: Typographies
        @Composable
        @ReadOnlyComposable
        get() = LocalTypographyProvider.current
}

internal val LocalColorProvider = staticCompositionLocalOf<Colors> {
    error("No default colors provided")
}

internal val LocalTypographyProvider = staticCompositionLocalOf<Typographies> {
    error("No default typography provided")
}