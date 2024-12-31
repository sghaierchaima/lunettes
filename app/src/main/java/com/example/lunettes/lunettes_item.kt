package com.example.lunettes

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lunettes.databinding.ActivityLunettesItemBinding
import com.example.lunettes.model.Lunettes

class lunettes_item (private val flowers: List<Lunettes>) : RecyclerView.Adapter<lunettes_item.LunettesViewHolder>() {

    // ViewHolder utilisant le ViewBinding
    class LunettesViewHolder(val binding: ActivityLunettesItemBinding) : RecyclerView.ViewHolder(binding.root) {
        // Liaison des vues avec le ViewBinding
        fun bind(lunette: Lunettes) {
            binding.lunettesName.text = lunette.nom
            binding.lunettesCategory.text = "Catégorie : ${lunette.categorie}"
            binding.lunettesDescription.text = "Description : ${lunette.description}"
            binding.lunettesPrice.text = "Prix : ${lunette.prix} TND"

            val imageUrl = lunette.imageUrl
            if (imageUrl.isNullOrEmpty()) {
                // Charger une image par défaut si l'URL est vide ou nulle
                Glide.with(binding.root.context)
                    .load(R.drawable.lunettes) // Remplacez par votre image par défaut
                    .into(binding.lunettes)
            } else {
                // Charger l'image depuis l'URL
                Glide.with(binding.root.context)
                    .load(imageUrl)
                    .into(binding.lunettes)
            }
            // Clic sur l'icône pour rediriger vers l'activité de modification
            binding.iconeEdit.setOnClickListener {
                val intent = Intent(binding.root.context, editLunette::class.java).apply {
                    // Passer les informations nécessaires pour la modification
                    putExtra("nom", lunette.nom)
                    putExtra("categorie", lunette.categorie)
                    putExtra("description", lunette.description)
                    putExtra("prix", lunette.prix)
                    putExtra("imageUrl", lunette.imageUrl)
                }
                binding.root.context.startActivity(intent)
            }
        }
    }

    // Créer et lier le ViewBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LunettesViewHolder {
        val binding = ActivityLunettesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LunettesViewHolder(binding)
    }

    // Associer les données aux vues
    override fun onBindViewHolder(holder: LunettesViewHolder, position: Int) {
        val flower = flowers[position]
        holder.bind(flower)
    }

    // Retourner le nombre d'éléments
    override fun getItemCount() = flowers.size
}
