package com.example.superfitcompose.ui.shared

import org.junit.Test
import org.junit.jupiter.api.Assertions.*

internal class TimeStampToDateMapperTest{

    private val mapper = TimeStampToDateMapper()

    @Test
    fun `test first value`(){
        assertEquals("1970-01-01", mapper(0))
    }

    @Test
    fun `test middle value`(){
        assertEquals("2003-06-24", mapper(1056412800))
    }

    @Test
    fun `test big value`(){
        assertEquals("2038-01-19", mapper(Int.MAX_VALUE))
    }
}