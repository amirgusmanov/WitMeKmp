package kz.witme.project.timer.model

import androidx.compose.runtime.Stable

@Stable
class TimerHelperModel(
    val hours: String,
    val minutes: String,
    val seconds: String
) {

    override fun toString(): String {
        return "$hours:$minutes:$seconds"
    }

    companion object {
        val EMPTY = TimerHelperModel(
            hours = "00",
            minutes = "00",
            seconds = "00"
        )

        fun getLeftTimerHelperModel(seconds: Long?): TimerHelperModel {
            if (seconds == null) return EMPTY

            val hours = (seconds % (24 * 3600)) / 3600
            val minutes = (seconds % 3600) / 60
            val secs = seconds % 60

            return TimerHelperModel(
                hours.toString().padStart(2, '0'),
                minutes.toString().padStart(2, '0'),
                secs.toString().padStart(2, '0')
            )
        }
    }
}