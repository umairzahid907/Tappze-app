package com.example.tappze.util

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tappze.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Matcher
import java.util.regex.Pattern

fun View.hide(){
    visibility = View.GONE
}

fun View.show(){
    visibility = View.VISIBLE
}

fun Fragment.toast(msg: String?){
    Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
}

fun Fragment.alertDialog(status: Boolean, text: String){
    val dial = Dialog(this.requireContext())
    dial.setContentView(R.layout.alert_dialog)
    dial.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    dial.window?.setBackgroundDrawableResource(android.R.color.transparent);
    val ok = dial.findViewById<Button>(R.id.btnOk)
    val tvText = dial.findViewById<TextView>(R.id.tvText)
    tvText?.text = text
    val img = dial.findViewById<ImageView>(R.id.topImg)
    if(status){
        img.setImageResource(R.drawable.ic_success)
    }else{
        img.setImageResource(R.drawable.ic_error)
    }
    ok?.setOnClickListener {
        dial.dismiss()
    }
    dial.show()
}

fun TextInputLayout.validate(child: TextInputEditText?): Boolean{
    var isValid = true
    if(child?.text.toString().isEmpty()){
        isValid = false
        this.error = "Empty Field"
        this.requestFocus()
    }else{
        this.error = null
    }
    return isValid
}
fun TextInputEditText.isValidEmail(): Boolean {
    val pattern: Pattern
    val emailPattern =
        "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    pattern = Pattern.compile(emailPattern)
    val matcher: Matcher = pattern.matcher(this.text.toString())
    return matcher.matches()
}

fun selectImage(name: String): Int {
    return when(name){
        "Whatsapp" -> R.drawable.ic_whatsapp
        "Calendly"-> R.drawable.ic_calendly
        "Custom" -> R.drawable.ic_custom
        "Email" -> R.drawable.ic_email
        "Facebook" -> R.drawable.ic_facebook
        "Instagram" -> R.drawable.ic_insta
        "Linkedin" -> R.drawable.ic_linkedin
        "Paypal" -> R.drawable.ic_paypal
        "Phone" -> R.drawable.ic_phone
        "Pinterest" -> R.drawable.ic_pinterest
        "Snapchat" -> R.drawable.ic_snapchat
        "Spotify" -> R.drawable.ic_spotify
        "Telegram" -> R.drawable.ic_telegram
        "Tiktok" -> R.drawable.ic_tiktok
        "Twitter" -> R.drawable.ic_twitter
        "Vimeo" -> R.drawable.ic_vimeo
        "Website" -> R.drawable.ic_website
        "Youtube" -> R.drawable.ic_youtube

        else -> R.drawable.ic_launcher_background
    }
}