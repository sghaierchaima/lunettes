package com.example.lunettes.model

import com.google.firebase.firestore.PropertyName

data class Lunettes(
    val nom: String = "",
    val categorie:String="",
    var prix: Double = 0.0,
    val description: String = "",
    @PropertyName("imageUrl")
    var imageUrl: String? = null,
    var userId: String = ""
)
