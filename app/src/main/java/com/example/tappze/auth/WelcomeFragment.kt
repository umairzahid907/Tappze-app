package com.example.tappze.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.tappze.R
import com.example.tappze.databinding.FragmentWelcomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : Fragment(R.layout.fragment_welcome) {

    private lateinit var binding: FragmentWelcomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentWelcomeBinding.bind(view)
        binding.btnCreateAccount.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }
        binding.tvSignin.setOnClickListener{
            findNavController().navigate(R.id.loginFragment)
        }
    }

}