package br.senai.sp.jandira.doetempo.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class DataStoreAppData(private val context: Context) {

    // Uma unica instancia
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("appData")
        val TOKEN_JWT = stringPreferencesKey("token")
        val ID_USER = stringPreferencesKey("id_user")
    }

    // Pegar o token
    val getToken: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[TOKEN_JWT] ?: ""
    }

    val getIdUser: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[ID_USER] ?: ""
    }

    suspend fun saveIdUser(idUser: String) {
        Log.i("aaa", "aaa")
        context.dataStore.edit {preferences ->
            preferences[ID_USER] = idUser
        }
    }

    // Salvar o token no dataStore
    suspend fun saveToken(token: String) {
        Log.i("aaa", "aaa")
        context.dataStore.edit { preferences ->
            preferences[TOKEN_JWT] = token
        }
    }
}