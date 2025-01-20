package com.example.lunettes.model

import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Lunettes(
    val nom: String = "",
    val categorie:String="",
    var prix: Double = 0.0,
    val description: String = "",
    @PropertyName("imageUrl")
    var imageUrl: String? = null,
    var userId: String = ""
): Parcelable
