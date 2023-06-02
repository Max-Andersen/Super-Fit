package com.example.superfitcompose.ui.shared

class DateMapper(private val date: String) {

    operator fun invoke(): String {
        return date.split("-").let {
            "${it[2]}.${it[1]}.${it[0]}"
        }
    }

}