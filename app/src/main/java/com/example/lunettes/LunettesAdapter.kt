package com.example.lunettes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lunettes.model.Lunettes

class LunettesAdapter : RecyclerView.Adapter<LunettesAdapter.FlowerViewHolder>()  {
    private var lunettesList: List<Lunettes> = ArrayList()

    fun setData(list: List<Lunettes>) {
        lunettesList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlowerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_lunettes_b, parent, false)
        return FlowerViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlowerViewHolder, position: Int) {
        holder.bind(lunettesList[position])
    }

    override fun getItemCount(): Int {
        return lunettesList.size
    }

    inner class FlowerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val flowerNameTextView: TextView = itemView.findViewById(R.id.flowerNameTextView)
        private val flowerPriceTextView: TextView = itemView.findViewById(R.id.flowerPriceTextView)
        private val flowerdescriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        private val addToCartIcon: ImageView = itemView.findViewById(R.id.addToCartIcon)

        fun bind(lunette: Lunettes) {
            flowerNameTextView.text = "Catégorie : ${lunette.nom}"
            flowerPriceTextView.text = "Prix : ${lunette.prix} TND"
            flowerdescriptionTextView.text = "Description : ${lunette.description}"
            // Ajouter un clic sur l'élément pour afficher l'icône
            itemView.setOnClickListener {
                addToCartIcon.visibility = View.VISIBLE

                // Cacher l'icône après 2 secondes
                addToCartIcon.setOnClickListener {
                    CartManager.addFlowerToCart(lunette) // Ajouter la fleur au panier

                    // Afficher un message pour confirmer l'ajout (Toast)
                    itemView.context.toast("Lunettes ajoutée au panier !")
                }

            }
        }
    }
    fun android.content.Context.toast(message: String) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show()
    }
}