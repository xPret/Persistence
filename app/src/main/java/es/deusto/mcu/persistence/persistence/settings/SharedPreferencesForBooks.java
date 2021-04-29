package es.deusto.mcu.persistence.persistence.settings;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesForBooks {

    private final SharedPreferences mPreferences;

    private static final String SHARED_PREF_NAME = "sha-perf-books";
    private static final String KEY_LAST_ACCESS = "KEY_LAST_ACCESS";

    public SharedPreferencesForBooks (Context context) {
        mPreferences = context.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
    }

    public void reset() {
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.clear();
        preferencesEditor.apply();
    }

    public void saveLastAccessDate(long last) {
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putLong(KEY_LAST_ACCESS, last);
        preferencesEditor.apply();
    }

    public long getLastAccessDate() {
        return mPreferences.getLong(KEY_LAST_ACCESS, -1);
    }
}