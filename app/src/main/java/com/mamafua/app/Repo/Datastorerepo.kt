package com.mamafua.app.Repo

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.createDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

const val PREFERENCE_NAME = "myprefrence"
data class  Filterpreferences(val  em: String,val id:String)
@Singleton
class DataStoreRepository @Inject constructor(@ApplicationContext context: Context)  {

    private object PreferenceKeys {
        val email = preferencesKey<String>("email")
        val cid = preferencesKey<String>("id")
    }

    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = PREFERENCE_NAME
    )

    suspend fun saveToDataStore(email: String,cid:String){
        dataStore.edit { preference ->
            preference[PreferenceKeys.email] = email
            preference[PreferenceKeys.cid]=cid

        }
    }

    val readFromDataStore= dataStore.data
        .catch { exception ->
            if(exception is IOException){
                Log.d("DataStore", exception.message.toString())
                emit(emptyPreferences())
            }else {
                throw exception
            }
        }
        .map { preference ->
            val email = preference[PreferenceKeys.email] ?: "none"
            val id = preference[PreferenceKeys.cid] ?: "none"

            Filterpreferences(email,id)
        }


    suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}