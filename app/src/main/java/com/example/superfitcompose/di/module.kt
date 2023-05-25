package com.example.superfitcompose.di

import com.example.superfitcompose.data.local.SharedPreferences
import com.example.superfitcompose.data.network.Network
import com.example.superfitcompose.data.network.retrofit.MyAuthenticator
import com.example.superfitcompose.data.network.retrofit.MyInterceptor
import com.example.superfitcompose.domain.repositoryinterfaces.AuthRepository
import com.example.superfitcompose.domain.repositoryinterfaces.ProfileRepository
import com.example.superfitcompose.domain.repositoryinterfaces.TrainingRepository
import com.example.superfitcompose.domain.usecases.GetAccessTokenUseCase
import com.example.superfitcompose.domain.usecases.GetBodyParamsUseCase
import com.example.superfitcompose.domain.usecases.GetRefreshTokenUseCase
import com.example.superfitcompose.domain.usecases.GetTokensUseCase
import com.example.superfitcompose.domain.usecases.GetTrainingHistoryUseCase
import com.example.superfitcompose.domain.usecases.RegisterUseCase
import com.example.superfitcompose.domain.usecases.SaveExerciseProgressUseCase
import com.example.superfitcompose.domain.usecases.SharedPreferencesInteractor
import com.example.superfitcompose.domain.usecases.ValidationUseCase
import com.example.superfitcompose.repositories.AuthRepositoryImpl
import com.example.superfitcompose.repositories.ProfileRepositoryImpl
import com.example.superfitcompose.repositories.TrainingRepositoryImpl
import com.example.superfitcompose.ui.auth.code.CodeInputViewModel
import com.example.superfitcompose.ui.auth.login.LoginViewModel
import com.example.superfitcompose.ui.auth.register.RegisterViewModel
import com.example.superfitcompose.ui.exercise.ExerciseViewModel
import com.example.superfitcompose.ui.main.exercises.AllExercisesViewModel
import com.example.superfitcompose.ui.main.mainscreen.MainScreenViewModel
import com.example.superfitcompose.ui.mybody.MyBodyViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

const val baseUrl = "http://fitness.wsmob.xyz:22169/api/"

val data = module {
    single { SharedPreferences(androidContext()) }
    single { Network(get(), get()) }
}

val retrofit = module {
    single { MyInterceptor(get()) }
    single { MyAuthenticator(get(), baseUrl) }
}

val viewModels = module {
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get(), get(), get(), get()) }
    viewModel { CodeInputViewModel(get(), get()) }
    viewModel { ExerciseViewModel(get(), get()) }
    viewModel { AllExercisesViewModel() }
    viewModel { MainScreenViewModel(get(), get(), get()) }
    viewModel { MyBodyViewModel(get()) }
}

val usecases = module {
    factory { ValidationUseCase() }
    factory { GetTokensUseCase(get(), get()) }
    factory { GetRefreshTokenUseCase(get()) }
    factory { GetAccessTokenUseCase(get()) }
    factory { SharedPreferencesInteractor(get()) }
    factory { RegisterUseCase(get()) }
    factory { GetBodyParamsUseCase(get()) }
    factory { GetTrainingHistoryUseCase(get()) }
    factory { SaveExerciseProgressUseCase(get()) }
}

val repositories = module {
    factory<AuthRepository> { AuthRepositoryImpl(get()) }
    factory<ProfileRepository> { ProfileRepositoryImpl(get()) }
    factory<TrainingRepository> { TrainingRepositoryImpl(get()) }
}