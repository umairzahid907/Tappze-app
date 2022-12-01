package com.example.tappze.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import com.example.tappze.R
import com.example.tappze.databinding.FragmentShareBinding
import com.example.tappze.util.UiState
import com.example.tappze.util.toast
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShareFragment : Fragment(R.layout.fragment_share) {

    lateinit var binding: FragmentShareBinding
    private val viewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentShareBinding.bind(view)
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
                    Picasso.get()
                        .load(it.data?.image)
                        .placeholder(R.drawable.placeholder)
                        .into(binding.profilePhoto)
                }
            }
        }
    }

}