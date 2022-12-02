package com.example.tappze.fragments

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.tappze.R
import com.example.tappze.data.model.User
import com.example.tappze.databinding.FragmentBottomPopupBinding
import com.example.tappze.di.Constants
import com.example.tappze.util.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BottomPopupFragment(private val link: Pair<String, String>) : BottomSheetDialogFragment(R.layout.fragment_bottom_popup) {

    lateinit var binding: FragmentBottomPopupBinding
    private val viewModel: UserViewModel by viewModels()
    lateinit var user: User
    lateinit var links: MutableMap<String, String>
    private val helpMessages = Constants.providesSocialAppHelp()
    var dial: Dialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentBottomPopupBinding.bind(view)
        binding.img.setImageResource(selectImage(link.first))
        binding.tvTitle.text = link.first
        binding.textInput.setText(link.second)
        binding.inputLayout.hint = link.first
        binding.tvHide.setOnClickListener {
            dismiss()
        }
        viewModel.user()
        observe()
        binding.btnSave.setOnClickListener {
            if(binding.inputLayout.validate(binding.textInput)){
                updateLink()
            }
        }
        binding.btnDelete.setOnClickListener {
            deleteLink()
        }
        binding.ivHelp.setOnClickListener {
            dial = Dialog(this.requireContext())
            dial?.setContentView(R.layout.dialog)
            dial?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            val ok = dial?.findViewById<Button>(R.id.btnOk)
            val tvHelp = dial?.findViewById<TextView>(R.id.tvMessage)
            tvHelp?.text = helpMessages[link.first]
            ok?.setOnClickListener {
                dial?.dismiss()
            }
            dial?.show()
        }

        binding.btnOpen.setOnClickListener {
            val launchIntent: Intent? =
                this.context?.packageManager?.getLaunchIntentForPackage("com.google.android.${link.first.lowercase()}")
            if (launchIntent != null) {
                launchIntent.putExtra(Intent.ACTION_VIEW, Uri.parse("https://www.${link.first.lowercase()}.com/${link.second}"))
                startActivity(launchIntent)
            } else {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.${link.first.lowercase()}.com/${link.second}"))
                context?.startActivity(intent)
            }
        }
    }


    private fun observe(){
        viewModel.user.observe(viewLifecycleOwner){
            when(it){
                is UiState.Loading -> {
                }
                is UiState.Failure -> {
                }
                is UiState.Success -> {
                    user = it.data!!
                    links = user.links.toMutableMap()
                }
            }
        }
    }

    private fun updateLink(){
        if(links.containsKey(link.first)) {
            links.replace(link.first, binding.textInput.text.toString())

        }else{
            links[link.first] = binding.textInput.text.toString()
        }
        user.links = links
        viewModel.updateUser(user)
        dismiss()
    }

    private fun deleteLink(){
        if(links.containsKey(link.first)) {
            links.remove(link.first)
        }
        user.links = links
        viewModel.updateUser(user)
        dismiss()
    }

    private fun openApp(packageName: String, playStoreLink: String, inputFromUser: String?) {
        try {
            if (isAppInstalled(packageName, context) == true)
            {
                context?.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(playStoreLink)))
            } else {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(playStoreLink))
                context?.startActivity(intent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun isAppInstalled(packageName: String, context: Context?): Boolean? {
        return try {
            val packageManager = context?.packageManager
            packageManager?.getApplicationInfo(packageName, 0)?.enabled
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}