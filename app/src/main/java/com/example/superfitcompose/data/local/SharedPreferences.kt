package com.example.superfitcompose.data.local


import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class SharedPreferences(context: Context) {

    private var masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private var sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secret_shared_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun updateSharedPrefs(typeOfData: String, newToken: String) {
        sharedPreferences.edit().putString(typeOfData, newToken).apply()
    }

    fun getSharedPrefs(typeOfData: String): String {
        return sharedPreferences.getString(typeOfData, "") ?: ""
    }
}