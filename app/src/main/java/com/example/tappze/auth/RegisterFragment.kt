package com.example.tappze.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tappze.R
import com.example.tappze.data.model.User
import com.example.tappze.databinding.FragmentRegisterBinding
import com.example.tappze.util.*
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.DoubleBounce
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    lateinit var binding: FragmentRegisterBinding
    private val viewModel: AuthViewModel by viewModels()
    lateinit var progress: ProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterBinding.bind(view)
        binding.btnSignUp.setOnClickListener {
            if(validation()){
                val user = getUserObject()
                viewModel.register(
                    email = binding.textInputEmail.text.toString(),
                    password = binding.textInputPassword.text.toString(),
                    user = user
                )
            }
        }
        progress = binding.progressBar
        observer()
    }

    private fun observer(){
        viewModel.register.observe(viewLifecycleOwner){
            when(it){
                is UiState.Loading -> {
                    progress.show()
                }
                is UiState.Failure -> {
                    progress.hide()
                    it.error?.let { it1 -> alertDialog(false, it1) }
                }
                is UiState.Success -> {
                    progress.hide()
                    alertDialog(true, it.data)
                    findNavController().navigate(R.id.action_registerFragment_to_homeFragment,
                    Bundle().apply
                     {
                         putParcelable("user", getUserObject())
                     })
                }
            }
        }
    }

    private fun validation(): Boolean{
        binding.run {
            if(!inputLayoutName.validate(textInputName)){
                return false
            }
            if(!inputLayoutUsername.validate(textInputUsername)){
               return false
            }
            if(!inputLayoutEmail.validate(textInputEmail)){
                return false
            } else{
                if(!textInputEmail.isValidEmail()){
                    inputLayoutEmail.error = "Please enter correct Email"
                    return false
                }else{
                    inputLayoutEmail.error = null
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

    private fun getUserObject() = User(
        id = "",
        name = binding.textInputName.text.toString(),
        userName = binding.textInputUsername.text.toString(),
        email = binding.textInputEmail.text.toString(),
        image = ""
    )

}
