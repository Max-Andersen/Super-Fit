package com.example.superfitcompose.ui.shared

import com.example.superfitcompose.R
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

internal class StringMapTest{
    private val mapper = StringMap


    @Test
    fun test(){
        assertEquals(R.string.january, mapper.namesOfMonth[1])
    }

    @Test
    fun test1(){
        assertEquals(R.string.june, mapper.namesOfMonth[6])
    }

    @Test
    fun test2(){
        assertEquals(R.string.september, mapper.namesOfMonth[9])
    }

}