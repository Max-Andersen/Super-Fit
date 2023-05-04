package com.example.superfitcompose.domain.usecases

import com.example.superfitcompose.domain.models.AuthCredential
import com.example.superfitcompose.domain.models.fromDomain
import com.example.superfitcompose.domain.repositoryinterfaces.AuthRepository
import com.example.superfitcompose.repositories.AuthRepositoryImpl

class RegisterUseCase(private val registerForm: AuthCredential) {

    private val authRepository: AuthRepository = AuthRepositoryImpl()

    operator fun invoke() = authRepository.register(registerForm.fromDomain())

}