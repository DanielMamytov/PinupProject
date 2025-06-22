package com.pinup.barapp.ui.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.pinup.barapp.databinding.FragmentGreetingsPinupBinding
import com.pinup.barapp.utils.PinupAppNavigator.ONBOARDING_DISPLAYED_KEY
import com.pinup.barapp.utils.PinupAppNavigator.getPinupPrefs
import com.pinup.barapp.utils.PinupAppNavigator.openPinupFragmentWithoutBackstack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GreetingsPinupFragment : Fragment() {

    private lateinit var greetingsBinding: FragmentGreetingsPinupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        greetingsBinding = FragmentGreetingsPinupBinding.inflate(inflater, container, false)
        return greetingsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        //todo welcome fragment logic
        greetingsBinding.nextButton.setOnClickListener {
            context?.getPinupPrefs()?.edit { putBoolean(ONBOARDING_DISPLAYED_KEY, true).apply() }
            parentFragmentManager.openPinupFragmentWithoutBackstack(MainPinupFragment())
        }

    }
}
