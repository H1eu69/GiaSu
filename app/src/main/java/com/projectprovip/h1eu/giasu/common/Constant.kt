package com.projectprovip.h1eu.giasu.common

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

object Constant {
    const val API_BASE_URL = "http://subedusmart.somee.com/api/"
    const val SECRET_KEY = "CED-super-secret-key"
    const val TOKEN_STRING = "token"
    const val EXPIRE_STRING = "expired"
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "share data")
