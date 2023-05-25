package com.example.superfitcompose.domain.usecases

import com.example.superfitcompose.domain.models.AuthCredential
import com.example.superfitcompose.domain.models.fromDomain
import com.example.superfitcompose.domain.repositoryinterfaces.AuthRepository
import com.example.superfitcompose.repositories.AuthRepositoryImpl

class RegisterUseCase(private val authRepository: AuthRepository) {

    operator fun invoke(registerForm: AuthCredential) =
        authRepository.register(registerForm.fromDomain())

}