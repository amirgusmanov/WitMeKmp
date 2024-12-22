package kz.witme.project.book.data.model

import kz.witme.project.book.domain.model.ReadingStatus

internal enum class ReadingStatusDto(private val backendValue: String) {
    GoingToRead("will_read"),
    ReadingNow("now_reading"),
    FinishedReading("finished_reading");

    companion object {
        private val map = ReadingStatusDto.entries.associateBy(ReadingStatusDto::backendValue)

        operator fun get(value: String): ReadingStatusDto = map[value]
            ?: throw IllegalArgumentException("Unknown value: $value")
    }
}

internal fun ReadingStatusDto.toDomain(): ReadingStatus = when (this) {
    ReadingStatusDto.GoingToRead -> ReadingStatus.GoingToRead
    ReadingStatusDto.ReadingNow -> ReadingStatus.ReadingNow
    ReadingStatusDto.FinishedReading -> ReadingStatus.FinishedReading
}
