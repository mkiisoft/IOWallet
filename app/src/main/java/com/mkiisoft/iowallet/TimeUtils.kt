package com.mkiisoft.iowallet

import org.threeten.bp.ZonedDateTime

object TimeUtils {

    val ConferenceDays = listOf(
        ConferenceDay(
            ZonedDateTime.parse(BuildConfig.CONFERENCE_DAY1_START),
            ZonedDateTime.parse(BuildConfig.CONFERENCE_DAY1_END)
        ),
        ConferenceDay(
            ZonedDateTime.parse(BuildConfig.CONFERENCE_DAY2_START),
            ZonedDateTime.parse(BuildConfig.CONFERENCE_DAY2_END)
        ),
        ConferenceDay(
            ZonedDateTime.parse(BuildConfig.CONFERENCE_DAY3_START),
            ZonedDateTime.parse(BuildConfig.CONFERENCE_DAY3_END)
        )
    )
}