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
import com.example.lunettes.model.Lunettes

class categories : RecyclerView.Adapter<categories.LunettesViewHolder>() {

    private var categoriesList = ArrayList<String>()

    fun setData(newData: ArrayList<String>) {
        categoriesList = newData
        notifyDataSetChanged() // Met à jour l'UI lorsque les données changent
    }

    class LunettesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categorieTextView: TextView = view.findViewById(R.id.nameshopTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LunettesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_categories, parent, false)
        return LunettesViewHolder(view)
    }

    override fun onBindViewHolder(holder: LunettesViewHolder, position: Int) {
        val categorie = categoriesList[position]
        holder.categorieTextView.text = categorie // Affiche la catégorie des lunettes
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }
}