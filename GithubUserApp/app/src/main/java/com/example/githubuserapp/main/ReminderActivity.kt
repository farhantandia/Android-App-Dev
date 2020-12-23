package com.example.githubuserapp.main

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.example.githubuserapp.R
import com.example.githubuserapp.alarm.AlarmReceiver

class ReminderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reminder_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.remind)


    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    class SettingsFragment : PreferenceFragmentCompat(),
        SharedPreferences.OnSharedPreferenceChangeListener {
        private lateinit var REMINDED: String
        private lateinit var isReminded: SwitchPreference
        private lateinit var alarmReceiver: AlarmReceiver

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            init()
            setSummaries()


        }

        private fun init() {
            /* Ini digunakan untuk mendapatkan key dari strings.xml*/
            REMINDED = resources.getString(R.string.reminded)
            isReminded = findPreference<SwitchPreference>(REMINDED) as SwitchPreference
            alarmReceiver = AlarmReceiver()

        }

        override fun onResume() {
            super.onResume()
            preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        }

        override fun onPause() {
            super.onPause()
            preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
        }

        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {

            if (key == REMINDED) {
                isReminded.isChecked = sharedPreferences.getBoolean(REMINDED, false)
                val repeatMessage = getString(R.string.reminder)
                val repeatTime = "09:00"

                if ( isReminded.isChecked ) {

                    context?.let {
                        alarmReceiver.setRepeatingAlarm(
                            it,
                            repeatTime,
                            repeatMessage
                        )
                    }
                } else {
                    context?.let { alarmReceiver.cancelAlarm(it) }
                }
            }
        }

        private fun setSummaries() {
            val sh = preferenceManager.sharedPreferences
            isReminded.isChecked = sh.getBoolean(REMINDED, false)
        }


    }
}