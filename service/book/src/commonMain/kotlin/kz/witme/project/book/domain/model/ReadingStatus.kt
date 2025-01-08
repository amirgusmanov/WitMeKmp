package kz.witme.project.book.domain.model

import kz.witme.project.book.data.model.ReadingStatusDto
import kz.witme.project.common.serializable.Serializable

enum class ReadingStatus(val displayName: String) : Serializable {
    GoingToRead("К прочтению"),
    ReadingNow("Читаю"),
    FinishedReading("Уже прочитал")
}

internal fun ReadingStatus.toDto(): ReadingStatusDto = when (this) {
    ReadingStatus.GoingToRead -> ReadingStatusDto.GoingToRead
    ReadingStatus.ReadingNow -> ReadingStatusDto.ReadingNow
    ReadingStatus.FinishedReading -> ReadingStatusDto.FinishedReading
}