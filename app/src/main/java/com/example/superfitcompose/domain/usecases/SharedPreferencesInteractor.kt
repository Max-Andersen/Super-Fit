package com.example.superfitcompose.domain.usecases

import com.example.superfitcompose.SharedPreferencesTypes
import com.example.superfitcompose.data.local.SharedPreferences

class SharedPreferencesInteractor {
    private val sharedPreferences = SharedPreferences

    private val types = SharedPreferencesTypes

    fun getAccessToken() = sharedPreferences.getSharedPrefs(types.AccessToken)

    fun updateAccessToken(newToken: String) = sharedPreferences.updateSharedPrefs(types.AccessToken, newToken)

    fun getRefreshToken() = sharedPreferences.getSharedPrefs(types.RefreshToken)

    fun updateRefreshToken(newToken: String) = sharedPreferences.updateSharedPrefs(types.RefreshToken, newToken)

    fun getUserLogin() = sharedPreferences.getSharedPrefs(types.Login)

    fun updateUserLogin(newUserLogin: String) = sharedPreferences.updateSharedPrefs(types.Login, newUserLogin)

    fun getUserPassword() = sharedPreferences.getSharedPrefs(types.Password)

    fun updateUserPassword(newUserPassword: String) = sharedPreferences.updateSharedPrefs(types.Password, newUserPassword)

    fun clearUserData(){
        types.allCleanableTypes.forEach {
            sharedPreferences.updateSharedPrefs(it, "")
        }
    }

}