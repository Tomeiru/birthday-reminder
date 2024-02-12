import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

val LAST_LAUNCHED_YEAR = intPreferencesKey("last_launched_year")

val DISMISSED_POPUP_THIS_YEAR = booleanPreferencesKey("dismissed_popup_this_year")