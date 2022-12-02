package com.example.tappze.data.model

import android.net.Uri
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class User(
    @PrimaryKey
    var id: String = "",
    var name: String = "",
    var userName: String = "",
    var email: String = "",
    var number: String = "",
    var details: String = "",
    var company: String = "",
    var links: Map<String, String> = mapOf("key" to "value"),
    var image: String = "",
    var gender: String = "",
    var status: Boolean = true,
    var dob: String = ""
) : Parcelable