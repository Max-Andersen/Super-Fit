package com.example.superfitcompose.data.local

class SharedPreferencesTypes {
    companion object{
        const val AccessToken: String = "access_token"
        const val RefreshToken: String = "refresh_token"
        const val Login: String = "user_login"
        const val Password: String = "user_password"

        val allCleanableTypes = listOf(AccessToken, RefreshToken, Login, Password)
    }
}