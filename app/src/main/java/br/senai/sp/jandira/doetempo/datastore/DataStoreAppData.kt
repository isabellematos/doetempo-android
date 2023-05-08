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
        val ID_CAMPANHA = stringPreferencesKey("id_campanha")
        val NAME_USER = stringPreferencesKey("name_user")
        val TYPE_USER = stringPreferencesKey("type_user")
        val EMAIL = stringPreferencesKey("email")
    }

    // Pegar o token
    val getToken: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[TOKEN_JWT] ?: ""
    }

    val getTypeUser: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[TYPE_USER] ?: ""
    }

    val getIdCampanha: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[ID_CAMPANHA] ?: ""
    }

    val getIdUser: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[ID_USER] ?: ""
    }

    val getNameUser: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[NAME_USER] ?: ""
    }

    val getEmail: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[EMAIL] ?: ""
    }

    suspend fun saveIdUser(idUser: String) {
        context.dataStore.edit {preferences ->
            preferences[ID_USER] = idUser
        }
    }

    suspend fun saveTypeUser(typeUser: String) {
        context.dataStore.edit {preferences ->
            preferences[TYPE_USER] = typeUser
        }
    }

    suspend fun saveIdCampanha(idCampanha: String) {
        context.dataStore.edit {preferences ->
            preferences[ID_CAMPANHA] = idCampanha
        }
    }

    suspend fun saveNameUser(nameUser: String) {
        context.dataStore.edit {preferences ->
            preferences[NAME_USER] = nameUser
        }
    }

    suspend fun saveEmail(emailUser: String) {
        context.dataStore.edit {preferences ->
            preferences[EMAIL] = emailUser
        }
    }

    // Salvar o token no dataStore
    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_JWT] = token
        }
    }
}