package kz.witme.project.common_ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import witmekmp.core.common_ui.generated.resources.Res
import witmekmp.core.common_ui.generated.resources.golos_black
import witmekmp.core.common_ui.generated.resources.golos_bold
import witmekmp.core.common_ui.generated.resources.golos_extrabold
import witmekmp.core.common_ui.generated.resources.golos_medium
import witmekmp.core.common_ui.generated.resources.golos_regular
import witmekmp.core.common_ui.generated.resources.golos_semibold

@Composable
private fun GolosFontFamily(): FontFamily = FontFamily(
    Font(Res.font.golos_black, weight = FontWeight.Black),
    Font(Res.font.golos_bold, weight = FontWeight.Bold),
    Font(Res.font.golos_extrabold, weight = FontWeight.ExtraBold),
    Font(Res.font.golos_medium, weight = FontWeight.Medium),
    Font(Res.font.golos_regular, weight = FontWeight.Normal),
    Font(Res.font.golos_semibold, weight = FontWeight.SemiBold),
)

data class Typographies(
    val regular12: TextStyle,
    val regular14: TextStyle,
    val regular16: TextStyle,
    val regular18: TextStyle,
    val regular20: TextStyle,
    val regular24: TextStyle,
    val medium16: TextStyle,
    val medium20: TextStyle,
    val medium24: TextStyle,
    val semiBold16: TextStyle,
    val semiBold20: TextStyle,
    val semiBold24: TextStyle,
    val semiBold32: TextStyle,
    val semiBold48: TextStyle,
    val bold14: TextStyle,
    val bold16: TextStyle,
    val bold20: TextStyle,
    val bold24: TextStyle,
)

@Composable
internal fun Typography() = Typographies(
    regular12 = TextStyle(
        fontFamily = GolosFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 15.sp,
    ),
    regular14 = TextStyle(
        fontFamily = GolosFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 15.sp,
    ),
    regular16 = TextStyle(
        fontFamily = GolosFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 20.sp,
    ),
    regular18 = TextStyle(
        fontFamily = GolosFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 20.sp,
    ),
    regular20 = TextStyle(
        fontFamily = GolosFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 20.sp,
    ),
    regular24 = TextStyle(
        fontFamily = GolosFontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 24.sp,
    ),
    medium16 = TextStyle(
        fontFamily = GolosFontFamily(),
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 15.sp,
    ),
    medium20 = TextStyle(
        fontFamily = GolosFontFamily(),
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 20.sp,
    ),
    medium24 = TextStyle(
        fontFamily = GolosFontFamily(),
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 24.sp,
    ),
    semiBold16 = TextStyle(
        fontFamily = GolosFontFamily(),
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 20.sp,
    ),
    semiBold20 = TextStyle(
        fontFamily = GolosFontFamily(),
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 20.sp,
    ),
    semiBold24 = TextStyle(
        fontFamily = GolosFontFamily(),
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 24.sp,
    ),
    semiBold48 = TextStyle(
        fontFamily = GolosFontFamily(),
        fontWeight = FontWeight.SemiBold,
        fontSize = 48.sp,
        lineHeight = 48.sp,
    ),
    bold14 = TextStyle(
        fontFamily = GolosFontFamily(),
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 15.sp,
    ),
    bold16 = TextStyle(
        fontFamily = GolosFontFamily(),
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 20.sp,
    ),
    bold20 = TextStyle(
        fontFamily = GolosFontFamily(),
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 20.sp,
    ),
    bold24 = TextStyle(
        fontFamily = GolosFontFamily(),
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 29.sp,
    ),
    semiBold32 = TextStyle(
        fontFamily = GolosFontFamily(),
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 32.sp,
    ),
)