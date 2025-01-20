package com.example.lunettes.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class CartItem(
    val lunette: Lunettes,
    var quantity: Int //quantity ajouter au panier
) : Parcelable