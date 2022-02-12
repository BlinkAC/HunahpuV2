package com.example.hunahpuv2.data.repository

import android.content.Context
import android.util.Log
import androidx.datastore.createDataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


const val PREFERENCE_NAME = "location_pref"
class DataStoreRepo(context: Context) {

    private object PreferenceKeys{
        val location = stringPreferencesKey("my_location")
    }
    val dataStore = context.createDataStore(
        name = PREFERENCE_NAME
    )

    suspend fun saveToDataStore(location: String){
        dataStore.edit { preference ->
            preference[PreferenceKeys.location] = location
        }
    }

    val readFromDataStore: Flow<String> = dataStore.data
        .catch { exception ->
            if(exception is IOException){
                Log.d("DataStore", exception.message.toString())
                emit(emptyPreferences())
            }else{
                throw exception
            }
        }.map { preference->
            val myLocation = preference[PreferenceKeys.location] ?: "25.6573%2C-100.402"
            myLocation
        }
}