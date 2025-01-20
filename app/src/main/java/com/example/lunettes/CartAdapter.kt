package com.example.lunettes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.lunettes.model.CartItem

class CartAdapter  (private val items: List<CartItem>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_cart_adapter, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val flowerName: TextView = itemView.findViewById(R.id.cartFlowerNameTextView)
        private val flowerPrice: TextView = itemView.findViewById(R.id.cartFlowerPriceTextView)
        private val flowerQuantity: TextView = itemView.findViewById(R.id.cartFlowerQuantityTextView)

        fun bind(cartItem: CartItem) {
            flowerName.text = cartItem.lunette.nom
            flowerPrice.text = "${cartItem.lunette.prix} TND"
            flowerQuantity.text = "Quantité : ${cartItem.quantity}"
        }
    }
}