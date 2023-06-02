package com.example.superfitcompose.ui.shared

import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

internal class DateMapperTest{
    private lateinit var mapper: DateMapper

    @Before
    fun prepare(){
        mapper = DateMapper()
    }

    @Test
    fun `june test`(){
        assertEquals("05.06.2023", mapper("2023-06-05"))
    }

    @Test
    fun `july test`(){
        assertEquals("05.07.2000", mapper("2000-07-05"))
    }

    @Test
    fun `wake me up when september test`(){
        assertEquals("01.09.2000", mapper("2000-09-01"))
    }
}