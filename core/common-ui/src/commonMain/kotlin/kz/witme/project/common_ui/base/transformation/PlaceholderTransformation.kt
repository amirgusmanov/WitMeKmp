package kz.witme.project.common_ui.base.transformation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle

class PlaceholderTransformation(
    private val placeholder: String,
    private val placeholderColor: Color,
) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return placeholderFilter()
    }

    private fun placeholderFilter(): TransformedText {

        val builder = AnnotatedString.Builder()

        val numberOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return 0
            }

            override fun transformedToOriginal(offset: Int): Int {
                return 0
            }
        }

        builder.withStyle(style = SpanStyle(color = placeholderColor)) {
            append(placeholder)
        }

        return TransformedText(builder.toAnnotatedString(), numberOffsetTranslator)
    }
}