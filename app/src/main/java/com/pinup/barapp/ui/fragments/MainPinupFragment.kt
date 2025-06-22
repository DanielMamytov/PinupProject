package com.pinup.barapp.ui.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.pinup.barapp.R
import com.pinup.barapp.databinding.FragmentPinupMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainPinupFragment : Fragment() {

    private lateinit var mainBinding: FragmentPinupMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mainBinding = FragmentPinupMainBinding.inflate(inflater, container, false)
        return mainBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val navHost = childFragmentManager
            .findFragmentById(R.id.fragment_container_view) as NavHostFragment
        val navController = navHost.navController
        mainBinding.bottomNavigation.setupWithNavController(navController)
        mainBinding.bottomNavigation.selectedItemId = R.id.menuFragment

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.blankFragment,
                R.id.basketFragment,
                R.id.QRFragment,
                R.id.reservationQrFragment -> mainBinding.bottomNavigation.visibility = View.GONE
                else -> mainBinding.bottomNavigation.visibility = View.VISIBLE
            }
        }


    }
}