package com.example.tappze.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tappze.R
import com.example.tappze.databinding.FragmentLoginBinding
import com.example.tappze.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    lateinit var binding: FragmentLoginBinding
    private val viewModel: AuthViewModel by viewModels()
    lateinit var progress: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)
//        binding.tvTopText.text = topText

        signIn()
        forgotPassword()

        progress = binding.progressBar
        observer()
    }

    private fun forgotPassword() {
        binding.tvForgotPassword.setOnClickListener{
            ForgotPasswordFragment().show(childFragmentManager, "forgot_password")
        }
    }

    private fun signIn() {
        binding.btnSignIn.setOnClickListener {
            if(validation()) {
                viewModel.login(
                    email = binding.textInputEmail.text.toString(),
                    password = binding.textInputPassword.text.toString()
                )
            }
        }
    }

    private fun observer(){
        viewModel.login.observe(viewLifecycleOwner){
            when(it){
                is UiState.Loading -> {
                    progress.show()
                }
                is UiState.Failure -> {
                    progress.hide()
                    toast(it.error)
                }
                is UiState.Success -> {
                    progress.hide()
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                }
            }
        }
    }

    private fun validation(): Boolean{
        binding.run {
            if(!inputLayout.validate(textInputEmail)){
                return false
            }else {
                if (!textInputEmail.isValidEmail()) {
                    inputLayout.error = "Please enter correct Email"
                    return false
                } else {
                    inputLayout.error = null
                }
            }
            if(!inputLayoutPassword.validate(textInputPassword)){
                return false
            }else{
                if(textInputPassword.text.toString().length < 6){
                    inputLayoutPassword.error = "Your password must be 6 digits or long"
                    return false
                }else{
                    inputLayoutPassword.error = null
                }
            }
        }
        return true
    }

}