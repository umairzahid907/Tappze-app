package com.example.tappze.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import com.example.tappze.R
import com.example.tappze.databinding.FragmentNfcBinding
import com.example.tappze.util.UiState
import com.example.tappze.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NfcFragment : Fragment(R.layout.fragment_nfc) {

    lateinit var binding: FragmentNfcBinding
    private val viewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentNfcBinding.bind(view)
        viewModel.user()
        observer()
    }

    private fun observer(){
        viewModel.user.observe(viewLifecycleOwner){
            when(it){
                is UiState.Loading -> {
                }
                is UiState.Failure -> {
                    toast(it.error)
                }
                is UiState.Success -> {
                    binding.tvUsername.text = it.data?.userName
                }
            }
        }
    }
}