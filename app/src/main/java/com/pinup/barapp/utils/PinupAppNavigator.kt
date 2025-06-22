package com.pinup.barapp.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.pinup.barapp.R

object PinupAppNavigator {
    const val DEFAULT_PRIVACY_URL = "https://sites.google.com/view/examplesampleprivacypolicy"
    const val DEFAULT_TERMS_URL = "https://sites.google.com/view/examplesampletermsofuse"
    const val REMOTE_CONFIG_URL = "https://pastebin.com/raw/q1CQaY4k"
    const val PERSISTED_OFFER_KEY = "main_offer_link"
    const val USER_ACTIVE_FLAG = "user_status"
    const val ONBOARDING_DISPLAYED_KEY = "welcome"
    private const val PINUP_PREFS_KEY = "example_sample_shared_preferences"

    fun Context.getPinupPrefs(): SharedPreferences {
        return this.getSharedPreferences(PINUP_PREFS_KEY, Context.MODE_PRIVATE)
    }

    fun FragmentManager.openPinupFragment(fragment: Fragment) {
        this.beginTransaction().apply {
            replace(R.id.navHostFragment, fragment)
            addToBackStack(null)
            commit()
        }
    }

    fun FragmentManager.openPinupFragmentWithoutBackstack(fragment: Fragment) {
        this.beginTransaction().apply {
            replace(R.id.navHostFragment, fragment)
            commit()
        }
    }
}
