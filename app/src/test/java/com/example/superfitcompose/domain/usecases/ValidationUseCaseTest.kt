package com.example.superfitcompose.domain.usecases

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class ValidationUseCaseTest {

    private lateinit var useCase: ValidationUseCase

    private val userNameIsEmpty = "Username is Empty!\n"
    private val codesNotEquals = "Code and confirmation not equal!\n"
    private val extraneousCharsInCodes = "Code can not contains any symbols except numbers\n"
    private val wrongLengthOfCodes = "Possible length of code is 4 digits\n"
    private val emailIncorrect = "Email is incorrect!\n"

    @Before
    fun prepare() {
        useCase = ValidationUseCase()
    }

    @Test
    fun `test with normal inputs`() {
        val email = "test@test.test"
        val code = "1234"
        val userName = "qwe"
        assertEquals("", useCase.invoke(email, code, code))
    }

    @Test
    fun `test with all bad inputs`() {
        val email = "testtest.test"
        val code = "1234!"
        val userName = ""
        assertEquals(
            userNameIsEmpty + codesNotEquals + extraneousCharsInCodes + wrongLengthOfCodes + emailIncorrect,
            useCase.invoke(email, code, code + "6", userName),
        )
    }

    @Test
    fun `test with bad email`() {
        val email = "testtest.test"
        val code = "1234"
        assertEquals(emailIncorrect, useCase.invoke(email, code, code))
    }

    @Test
    fun `test with long code`() {
        val email = "test@test.test"
        val code = "12345"
        assertEquals(wrongLengthOfCodes, useCase.invoke(email, code, code))
    }

    @Test
    fun `test with not digit in code`() {
        val email = "test@test.test"
        val code = "1234!"
        assertEquals(extraneousCharsInCodes + wrongLengthOfCodes, useCase.invoke(email, code, code))
    }

    @Test
    fun `test with not equals codes`() {
        val email = "test@test.test"
        val code = "123"
        assertEquals(codesNotEquals, useCase.invoke(email, code + "4", code + "5"))
    }

    @Test
    fun `test with empty username`() {
        val email = "test@test.test"
        val code = "1234"
        val userName = ""
        assertEquals(userNameIsEmpty, useCase.invoke(email, code, code, userName))
    }

}