package com.example.lunettes

import android.content.Intent
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
import com.example.lunettes.model.Lunettes

class categories :  RecyclerView.Adapter<categories.NameshopViewHolder>() {

    private var nameshopList: List<String> = ArrayList()

    fun setData(list: List<String>) {
        nameshopList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameshopViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_categories, parent, false)
        return NameshopViewHolder(view)
    }

    override fun onBindViewHolder(holder: NameshopViewHolder, position: Int) {
        holder.bind(nameshopList[position])
    }

    override fun getItemCount(): Int {
        return nameshopList.size
    }

    class NameshopViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameshopTextView: TextView = itemView.findViewById(R.id.nameshopTextView)

        fun bind(categorie: String) {
            nameshopTextView.text = categorie
            nameshopTextView.setOnClickListener {
                // Rediriger vers l'activité des fleurs avec le nom de la boutique
                val intent = Intent(itemView.context, LunettesActivityB::class.java).apply {
                    putExtra("categorie", categorie)
                }
                itemView.context.startActivity(intent)  // Utilisez itemView.context ici
            }
        }
    }
}