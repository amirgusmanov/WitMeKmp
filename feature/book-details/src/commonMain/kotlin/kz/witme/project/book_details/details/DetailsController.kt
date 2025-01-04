package kz.witme.project.book_details.details

import androidx.compose.runtime.Stable
import kz.witme.project.book.domain.model.GetBookSessionDetails

@Stable
internal interface DetailsController {

    fun onSessionClick(session: GetBookSessionDetails)

    fun onErrorDismiss()
}