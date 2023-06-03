package com.example.superfitcompose.ui.shared

import kotlinx.datetime.Instant

class TimeStampToDateMapper {

    operator fun invoke(time: Int): String {
        return Instant.fromEpochMilliseconds(time * 1000L)
            .toString().subSequence(0, 10) as String
    }
}