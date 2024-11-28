package kz.witme.project.common_ui.theme

import androidx.compose.ui.graphics.Color

data class Colors(
    val black: Color,
    val white: Color,
    val background: Color,
    val primary200: Color,
    val primary300: Color,
    val primary400: Color,
    val primary500: Color,
    val secondary200: Color,
    val secondary300: Color,
    val secondary400: Color,
    val secondary500: Color,
    val secondary600: Color,
    val link100: Color,
    val link200: Color,
    val searchPlaceholder: Color,
    val search: Color,
    val textBrush100: Color,
    val textBrush200: Color,
    val error100: Color,
)

internal val lightPalette = Colors(
    black = Color.Black,
    white = Color.White,
    background = Color(0xFFEBEDF6),
    primary200 = Color(0xFFBCD9FF),
    primary300 = Color(0xFF729FD5),
    primary400 = Color(0xFF336DB4),
    primary500 = Color(0xFF1757A4),
    secondary200 = Color(0xFFEBF0F6),
    secondary300 = Color(0xFFB3BAC7),
    secondary400 = Color(0xFF7F87A0),
    secondary500 = Color(0xFF4D566E),
    secondary600 = Color(0xFF4D566E),
    link100 = Color(0xFF2DAAFC),
    link200 = Color(0xFF0778C1),
    searchPlaceholder = Color(0xFF787B86),
    search = Color(0xFFF2F2F2),
    textBrush100 = Color(0xFF2662AB),
    textBrush200 = Color(0xFF4D84C7),
    error100 = Color(0xFFE60045)
)