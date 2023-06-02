package com.example.superfitcompose.ui.shared

class DateMapper {

    operator fun invoke(date: String): String {
        return date.split("-").let {
            "${it[2]}.${it[1]}.${it[0]}"
        }
    }
}