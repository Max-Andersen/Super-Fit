package com.example.superfitcompose.data.local


import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.superfitcompose.MyApplication

object SharedPreferences {

    private var masterKey = MasterKey.Builder(MyApplication.applicationContext())
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private var sharedPreferences = EncryptedSharedPreferences.create(
        MyApplication.applicationContext(),
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