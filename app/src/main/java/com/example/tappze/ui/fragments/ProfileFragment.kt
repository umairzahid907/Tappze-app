package com.example.tappze.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tappze.R
import com.example.tappze.data.model.UserDao
import com.example.tappze.data.model.User
import com.example.tappze.databinding.FragmentProfileBinding
import com.example.tappze.ui.viewmodel.UserViewModel
import com.example.tappze.util.*
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment: Fragment(R.layout.fragment_profile), OnItemClickListener {

    lateinit var binding: FragmentProfileBinding
    private var links: Map<String, String>? = null
    private lateinit var adapter: RVAdapter
    private val viewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentProfileBinding.bind(view)
        binding.tvEditProfile.setOnClickListener {
            findNavController().navigate(R.id.editProfileFragment)
        }
        viewModel.user()
        observer()
    }

    override fun onItemClicked(link: Pair<String, String>) {
        BottomPopupFragment(link).show(childFragmentManager, "link")
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
                    it.data?.let { it1 -> updateUI(it1) }
                }
            }
        }
    }

    private fun updateUI(user: User){
        links = user.links
        val array: Array<Pair<String, String>>? = links?.toList()?.toTypedArray()
        adapter = links?.let { RVAdapter(array, this) }!!
        binding.rvGrid.adapter = adapter
        binding.tvName.text = user.name
        if (user.image.isNotEmpty()){
            Picasso.get()
                .load(user.image)
                .placeholder(R.drawable.placeholder)
                .into(binding.profilePhoto)
        }
    }

}