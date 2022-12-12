package com.example.tappze.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tappze.R
import com.example.tappze.auth.ForgotPasswordFragment
import com.example.tappze.data.model.User
import com.example.tappze.databinding.FragmentSettingsBinding
import com.example.tappze.ui.viewmodel.UserViewModel
import com.example.tappze.util.UiState
import com.example.tappze.util.hide
import com.example.tappze.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    lateinit var binding: FragmentSettingsBinding
    private val viewModel: UserViewModel by viewModels()
    var user: User? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSettingsBinding.bind(view)
        binding.tvSignOut.setOnClickListener {
            requireActivity().findViewById<View>(R.id.bottomNavigation).hide()
            viewModel.deleteUser()
            findNavController().navigate(R.id.action_settingsFragment_to_my_navigation)
        }
        binding.tvChangePassword.setOnClickListener {
            ForgotPasswordFragment().show(childFragmentManager, "forgot_password")
        }
        viewModel.user()
        observer()
        binding.toggleButton.addOnButtonCheckedListener{ _, checkedId, isChecked ->
            if(isChecked){
                when(checkedId){
                    R.id.btnOn -> {
                        user?.status = true
                        user?.let { viewModel.updateUser(it) }
                        observer()
                    }
                    R.id.btnOff -> {
                        user?.status = false
                        user?.let { viewModel.updateUser(it) }
                        observer()
                    }
                }
            }
        }
        binding.tvPurchase.setOnClickListener {
            val urlIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://tappze.com")
            )
            startActivity(urlIntent)
        }
        binding.tvSubscriptions.setOnClickListener {
            val urlIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://tappze.com")
            )
            startActivity(urlIntent)
        }
        binding.tvContact.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_EMAIL, "support@tappze.com")
            intent.type = "message/rfc822"
            startActivity(intent)
        }
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
                    user = it.data!!
                    if(it.data.status){
                        binding.toggleButton.check(R.id.btnOn)
                    }else{
                        binding.toggleButton.check(R.id.btnOff)
                    }
                }
            }
        }

        viewModel.updateUser.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {
                }
                is UiState.Failure -> {
                    toast(it.error)
                }
                is UiState.Success -> {
                    toast("Status updated")
                }
            }
        }
    }
}