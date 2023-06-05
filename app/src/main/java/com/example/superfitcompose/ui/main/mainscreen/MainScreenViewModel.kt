package com.example.superfitcompose.ui.main.mainscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.superfitcompose.ui.IntentHandler
import com.example.superfitcompose.data.network.ApiResponse
import com.example.superfitcompose.domain.usecases.GetBodyParamsUseCase
import com.example.superfitcompose.domain.usecases.GetTrainingHistoryUseCase
import com.example.superfitcompose.domain.usecases.SharedPreferencesInteractor
import com.example.superfitcompose.ui.main.mainscreen.MainScreenIntent.ClickedOnExercise
import com.example.superfitcompose.ui.main.mainscreen.MainScreenIntent.ClickedOnMyBodyCard
import com.example.superfitcompose.ui.main.mainscreen.MainScreenIntent.ClickedOnSeeAllExercises
import com.example.superfitcompose.ui.main.mainscreen.MainScreenIntent.ClickedOnSignOut
import com.example.superfitcompose.ui.main.mainscreen.MainScreenIntent.LoadData
import com.example.superfitcompose.ui.main.mainscreen.MainScreenIntent.NavigationProcessed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainScreenViewModel(
    private val getBodyParamsUseCase: GetBodyParamsUseCase,
    private val getTrainingHistoryUseCase: GetTrainingHistoryUseCase,
    private val sharedPreferencesInteractor: SharedPreferencesInteractor
) : ViewModel(), IntentHandler<MainScreenIntent> {
    private val _screenState = MutableLiveData(MainScreenViewState())

    fun getViewState(): LiveData<MainScreenViewState> = _screenState


    override fun processIntent(intent: MainScreenIntent) {
        var state = _screenState.value ?: return

        when (intent) {
            is LoadData -> {
                viewModelScope.launch {
                    getBodyParamsUseCase().collect {
                        when (it) {
                            is ApiResponse.Success -> {
                                withContext(Dispatchers.Main) {
                                    _screenState.value =
                                        state.copy(bodyParams = it.data.maxByOrNull { item -> item.date })
                                    state = _screenState.value ?: return@withContext
                                }
                            }

                            is ApiResponse.Failure -> {}
                            is ApiResponse.Loading -> {}
                        }
                    }

                    getTrainingHistoryUseCase().collect {
                        when (it) {
                            is ApiResponse.Success -> {
                                withContext(Dispatchers.Main) {
                                    if (it.data.isEmpty()) {
                                        return@withContext
                                    }

                                    val list = it.data.sortedBy { exercise -> exercise.date }

                                    if (list.size == 1) {
                                        _screenState.value =
                                            state.copy(
                                                lastTrainings = Pair(
                                                    list.last().exercise,
                                                    null
                                                )
                                            )
                                    } else { // 2
                                        val first = list.lastOrNull()?.exercise
                                        val second =
                                            list.findLast { exercise -> exercise.exercise != first }?.exercise
                                        _screenState.value =
                                            state.copy(
                                                lastTrainings = Pair(
                                                    first,
                                                    second
                                                )
                                            )
                                    }
                                }
                            }

                            is ApiResponse.Failure -> {}
                            is ApiResponse.Loading -> {}
                        }
                    }
                }
            }

            is ClickedOnMyBodyCard -> {
                _screenState.value = state.copy(navigateToMyBody = true)
            }

            is ClickedOnSeeAllExercises -> {
                _screenState.value = state.copy(seeAllExercises = true)
            }

            is ClickedOnExercise -> {
                _screenState.value = state.copy(navigateToTrainingType = intent.trainingType)
            }

            is ClickedOnSignOut -> {
                sharedPreferencesInteractor.clearUserData()
                _screenState.value = state.copy(signOut = true)
            }

            is NavigationProcessed -> {
                _screenState.value = state.copy(
                    navigateToTrainingType = null,
                    navigateToMyBody = false,
                    signOut = false,
                    seeAllExercises = false
                )
            }
        }
    }
}