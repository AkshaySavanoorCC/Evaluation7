package com.akshay.evaluation7
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


private const val VISIT_COUNT_PREFERENCE_NAME = "visit_count_preferences"

// Create a DataStore instance using the preferencesDataStore delegate, with the Context as
// receiver.
private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(
    name = VISIT_COUNT_PREFERENCE_NAME
)

class VisitCountDataStore(context: Context) {
    private val VISITORS_COUNT = intPreferencesKey("visitors_count")

    val preferenceFlow: Flow<Int> = context.dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->

            preferences[VISITORS_COUNT] ?: 0

        }

    suspend fun setVisitCount(context: Context){
        context.dataStore.edit {

            val currentValue = it[VISITORS_COUNT] ?: 0
            it[VISITORS_COUNT] = currentValue + 1
        }
    }

}