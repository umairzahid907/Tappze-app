package com.example.tappze.ui.fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.net.Uri
import android.widget.Toast
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tappze.R
import com.example.tappze.data.model.User
import com.example.tappze.databinding.FragmentEditProfileBinding
import com.example.tappze.di.Constants
import com.example.tappze.ui.viewmodel.UserViewModel
import com.example.tappze.util.*
import com.github.dhaval2404.imagepicker.ImagePicker
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : Fragment(R.layout.fragment_edit_profile), OnItemClickListener {

    lateinit var binding: FragmentEditProfileBinding
    private val viewModel: UserViewModel by viewModels()
    private val titles = Constants.providesSocialAppNames()
    private lateinit var adapter: RVAdapter
    private var links: MutableMap<String, String>? = null
    private var mProfileUri: Uri? = null
    var user: User? = null

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data!!

                    mProfileUri = fileUri
                    binding.profilePhoto.setImageURI(fileUri)
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(this.context, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this.context, "Task Cancelled", Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gender: Array<String> = resources.getStringArray(R.array.Gender)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown, gender)

        binding = FragmentEditProfileBinding.bind(view)
        adapter = RVAdapter(titles, this)
        binding.rvGrid.adapter = adapter
        binding.ddGender.setAdapter(arrayAdapter)

        btnSave()
        binding.btnCancel.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }
        binding.selectPhoto.setOnClickListener {
            ImagePicker.with(this)
//                .galleryOnly()
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }
        binding.btnDob.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(), { _, year, month, dayOfMonth ->
                    val date = "$dayOfMonth/${month + 1}/$year"
                    binding.btnDob.text = date
                }, 1990, 0, 1
            )
            datePickerDialog.show()
        }
        observer()

    }

    private fun btnSave() {
        viewModel.user()
        binding.btnSave.setOnClickListener {
            if(binding.edtName.text.toString().isEmpty()){
                binding.edtName.error = "Empty Field"
                binding.edtName.requestFocus()
            }else{
                user?.name = binding.edtName.text.toString()
                user?.email = user?.email!!
                user?.userName = user?.userName!!
                user?.details = binding.edtAbout.text.toString()
                user?.number = binding.edtPhone.text.toString()
                user?.company = binding.edtCompany.text.toString()
                user?.links = links!!
                user?.gender = binding.ddGender.editableText.toString()
                user?.dob = binding.btnDob.text as String
                binding.edtName.error = null
                if(mProfileUri != null){
                    mProfileUri?.let { it1 ->
                        viewModel.uploadImage(it1){ state ->
                            when(state){
                                is UiState.Loading -> {
                //                                progress.show()
                                }
                                is UiState.Failure -> {
                //                                progress.hide()
                                    toast(state.error)
                                }
                                is UiState.Success -> {
                                    user?.image = state.data.toString()
                                    viewModel.updateUser(user!!)
                                }
                            }
                        }
                    }
                }else {
                    viewModel.updateUser(user!!)
                }
            }
        }
    }

    private fun observer(){
        viewModel.user.observe(viewLifecycleOwner){
            when(it){
                is UiState.Loading -> {
                    binding.progressBar.show()
                }
                is UiState.Failure -> {
                    binding.progressBar.hide()
                    toast(it.error)
                }
                is UiState.Success -> {
                    binding.progressBar.hide()
                   binding.let { view ->
                       links = it.data?.links?.toMutableMap()
                       user = it.data
                       view.edtName.setText(it.data?.name)
                       view.edtAbout.setText(it.data?.details)
                       view.edtPhone.setText(it.data?.number)
                       view.edtCompany.setText(it.data?.company)
                       view.ddGender.setText(it.data?.gender, false)
                       if(it.data?.dob?.isNotEmpty() == true){
                           view.btnDob.text = it.data.dob
                       }
                       Picasso.get()
                           .load(it.data?.image)
                           .placeholder(R.drawable.placeholder)
                           .into(binding.profilePhoto)
                   }
                }
            }
        }

        viewModel.updateUser.observe(viewLifecycleOwner){
            when(it){
                is UiState.Loading -> {
                    binding.progressBar.show()
                }
                is UiState.Failure -> {
                    binding.progressBar.hide()
                    toast(it.error)
                }
                is UiState.Success -> {
                    binding.progressBar.hide()
                    toast(it.data)
                    findNavController().navigate(R.id.profileFragment)
                }
            }
        }
    }

    override fun onItemClicked(link: Pair<String, String>) {
        BottomPopupFragment(link).show(childFragmentManager, "link")
    }

}