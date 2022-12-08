package com.example.tappze.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.tappze.R
import com.example.tappze.databinding.FragmentForgotPasswordBinding
import com.example.tappze.util.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : BottomSheetDialogFragment(R.layout.fragment_forgot_password) {


    lateinit var binding: FragmentForgotPasswordBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentForgotPasswordBinding.bind(view)
        binding.tvHide.setOnClickListener{
            dismiss()
        }
        binding.btnSend.setOnClickListener {
            if(validation()){
                viewModel.forgotPassword(email = binding.textInputEmail.text.toString())
            }
        }
        observer()
    }

    private fun observer() {
        viewModel.forgot.observe(viewLifecycleOwner){state ->
            when(state){
                is UiState.Loading -> { }
                is UiState.Failure -> {
                    state.error?.let { alertDialog(false, it) }
                    dismiss()
                }
                is UiState.Success -> {
                    alertDialog(true, state.data)
                    dismiss()
                }
            }
        }
    }

    private fun validation(): Boolean{
        binding.run {
            if(!inputLayoutEmail.validate(textInputEmail)){
                return false
            }else {
                if (!textInputEmail.isValidEmail()) {
                    inputLayoutEmail.error = "Please enter correct Email"
                    return false
                } else {
                    inputLayoutEmail.error = null
                }
            }
        }
        return true
    }
}