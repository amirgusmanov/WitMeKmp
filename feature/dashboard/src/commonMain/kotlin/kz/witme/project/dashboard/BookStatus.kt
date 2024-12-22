package kz.witme.project.dashboard

enum class BookStatus(val id: Int) {
    TO_READ(1),
    READING(2),
    ALREADY_READ(3);

    companion object {
        private val map = entries.associateBy(BookStatus::id)

        operator fun get(value: Int?): BookStatus = map.get(key = value) ?: TO_READ
    }
}