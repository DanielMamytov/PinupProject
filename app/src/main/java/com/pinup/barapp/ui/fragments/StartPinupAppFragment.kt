package com.pinup.barapp.ui.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.pinup.barapp.databinding.FragmentPinupStartBinding
import com.pinup.barapp.utils.PinupAppNavigator.REMOTE_CONFIG_URL
import com.pinup.barapp.utils.PinupAppNavigator.PERSISTED_OFFER_KEY
import com.pinup.barapp.utils.PinupAppNavigator.USER_ACTIVE_FLAG
import com.pinup.barapp.utils.PinupAppNavigator.ONBOARDING_DISPLAYED_KEY
import com.pinup.barapp.utils.PinupAppNavigator.getPinupPrefs
import com.pinup.barapp.utils.PinupAppNavigator.openPinupFragmentWithoutBackstack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class StartPinupAppFragment : Fragment() {

    private lateinit var startBinding: FragmentPinupStartBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        startBinding = FragmentPinupStartBinding.inflate(inflater, container, false)
        return startBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        startLoadingAnimation()
        handleAppInitialization()

        android.os.Handler(Looper.getMainLooper()).postDelayed({
        }, 5000)


    }

    private fun navigateToProjectFragment() {
        val launchedBefore = context?.getPinupPrefs()?.getBoolean(ONBOARDING_DISPLAYED_KEY, false) == true
        if (launchedBefore) {
            parentFragmentManager.openPinupFragmentWithoutBackstack(MainPinupFragment())
        } else {
            parentFragmentManager.openPinupFragmentWithoutBackstack(GreetingsPinupFragment())
        }
    }

    private fun handleAppInitialization() {
        val offerLink = context?.getPinupPrefs()?.getString(PERSISTED_OFFER_KEY, "") ?: ""
        if (!isUser()) {
            navigateToProjectFragment()
        } else if (offerLink.isNotEmpty()) {
            navigateBasedOnOfferLink(offerLink)
        } else {
            getLinks()
        }
    }

    private fun getLinks() {
        val queue = Volley.newRequestQueue(context)
        val url = REMOTE_CONFIG_URL

        val stringRequest = object : StringRequest(Method.GET, url, Response.Listener { offerLink ->

            if (offerLink.isNullOrEmpty()) {
                saveUserFalse()
                navigateBasedOnOfferLink(offerLink)
            } else {
                saveLink(offerLink)
                navigateBasedOnOfferLink(offerLink)
            }
        }, Response.ErrorListener {
            navigateBasedOnOfferLink("")

        }) {}

        queue.add(stringRequest)
    }

    private fun navigateBasedOnOfferLink(offerLink: String) {
        if (offerLink.isNotEmpty()) {
            parentFragmentManager.openPinupFragmentWithoutBackstack(GreetingsPinupFragment())
        } else {
            navigateToProjectFragment()
        }
    }

    private fun saveLink(offerLink: String) {
        context?.getPinupPrefs()?.edit { putString(PERSISTED_OFFER_KEY, offerLink)?.apply() }
    }

    private fun saveUserFalse() {
        context?.getPinupPrefs()?.edit { putBoolean(USER_ACTIVE_FLAG, false)?.apply() }
    }

    private fun isUser(): Boolean {
        return context?.getPinupPrefs()?.getBoolean(USER_ACTIVE_FLAG, true) ?: true
    }

    private fun startLoadingAnimation() {
        startBinding.pinupCircleLoaderView.apply {
            scaleX = 0f
            scaleY = 0f
            animate().scaleX(1f).scaleY(1f).setDuration(500).start()
        }
    }
}