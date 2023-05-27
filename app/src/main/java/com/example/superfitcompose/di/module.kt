package com.example.superfitcompose.di

import com.example.superfitcompose.MyApplication
import com.example.superfitcompose.data.local.SharedPreferences
import com.example.superfitcompose.data.network.Network
import com.example.superfitcompose.data.network.retrofit.MyAuthenticator
import com.example.superfitcompose.data.network.retrofit.MyInterceptor
import com.example.superfitcompose.domain.repositoryinterfaces.*
import com.example.superfitcompose.domain.usecases.*
import com.example.superfitcompose.repositories.*
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
    viewModel { MyBodyViewModel(get(), get(), get(), get(), get(), androidContext() as MyApplication) }
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
    factory { UpdateBodyParamsUseCase(get()) }
    factory { AddNewImageUseCase(get()) }
    factory { GetUserPhotosUseCase(get()) }
    factory { DownloadPhotoUseCase(get()) }
}

val repositories = module {
    factory<AuthRepository> { AuthRepositoryImpl(get()) }
    factory<ProfileRepository> { ProfileRepositoryImpl(get()) }
    factory<TrainingRepository> { TrainingRepositoryImpl(get()) }
}