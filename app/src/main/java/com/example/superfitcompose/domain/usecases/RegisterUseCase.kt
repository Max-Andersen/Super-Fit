package com.example.superfitcompose.domain.usecases

import com.example.superfitcompose.domain.models.AuthCredential
import com.example.superfitcompose.domain.models.fromDomain
import com.example.superfitcompose.repositories.AuthRepositoryImpl

class RegisterUseCase(private val authRepositoryImpl: AuthRepositoryImpl) {

    operator fun invoke(registerForm: AuthCredential) =
        authRepositoryImpl.register(registerForm.fromDomain())

}