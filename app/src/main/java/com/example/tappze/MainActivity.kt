package com.example.tappze

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.example.tappze.data.model.User
import com.example.tappze.databinding.ActivityMainBinding
import com.example.tappze.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel.user()
        viewModel.user.observe(this){
            when(it){
                is UiState.Loading -> {
                }
                is UiState.Failure -> {

                }
                is UiState.Success -> {
                    if(binding.navHost.findNavController().currentDestination?.id != R.id.profileFragment) {
                        binding.navHost.findNavController()
                            .navigate(R.id.action_welcomeFragment_to_homeFragment)
                    }
                }
            }
        }
    }
}