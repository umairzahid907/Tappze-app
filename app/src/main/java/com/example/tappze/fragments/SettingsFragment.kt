package com.example.tappze.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tappze.R
import com.example.tappze.databinding.FragmentSettingsBinding
import com.example.tappze.util.hide
import com.google.firebase.firestore.auth.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    lateinit var binding: FragmentSettingsBinding
    private val viewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSettingsBinding.bind(view)
        binding.tvSignOut.setOnClickListener {
            requireActivity().findViewById<View>(R.id.bottomNavigation).hide()
            viewModel.deleteUser()
            findNavController().navigate(R.id.action_settingsFragment_to_my_navigation)
        }
    }
}