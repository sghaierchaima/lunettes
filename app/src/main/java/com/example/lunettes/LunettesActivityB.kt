package com.example.lunettes

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lunettes.databinding.ActivityLunettesBBinding
import com.example.lunettes.model.Lunettes
import com.google.firebase.firestore.FirebaseFirestore

class LunettesActivityB : AppCompatActivity() {
    private lateinit var binding: ActivityLunettesBBinding
    private val firestore = FirebaseFirestore.getInstance()
    private lateinit var lunetteAdapter: LunettesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLunettesBBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Récupérer le nom de la catégorie depuis l'intent
        val categorie = intent.getStringExtra("categorie")

        // Vérifier si la catégorie est nulle
        if (categorie == null) {
            Toast.makeText(this, "Catégorie introuvable", Toast.LENGTH_SHORT).show()
            return
        }

        // Afficher le nom de la catégorie dans le titre
        binding.shopNameTextView.text = "Lunettes de la catégorie : $categorie"

        // Initialiser l'adaptateur et le RecyclerView
        lunetteAdapter = LunettesAdapter()
        binding.recyclerViewFlowers.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewFlowers.adapter = lunetteAdapter

        // Charger les lunettes par catégorie
        loadLunettesByCategory(categorie)

        // Ajouter un clic sur le nom de la catégorie pour afficher une nouvelle page (si nécessaire)
        binding.shopNameTextView.setOnClickListener {
            val intent = Intent(this, LunettesAdapter::class.java).apply {
                putExtra("categorie", categorie)
            }
            startActivity(intent)
        }
    }

    // Charger les lunettes en fonction de la catégorie
    private fun loadLunettesByCategory(categorie: String) {
        // Rechercher les lunettes dans Firestore où le champ "categorie" correspond à la catégorie sélectionnée
        firestore.collection("lunettes")
            .whereEqualTo("categorie", categorie)  // Chercher les lunettes par catégorie
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    Toast.makeText(this, "Aucune lunette trouvée pour cette catégorie", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

                val lunettesList = ArrayList<Lunettes>()
                for (document in documents) {
                    // Récupérer chaque lunette en tant qu'objet Lunettes
                    val lunette = document.toObject(Lunettes::class.java)
                    lunettesList.add(lunette)
                }

                // Passer les lunettes à l'adaptateur
                lunetteAdapter.setData(lunettesList)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Erreur lors de la recherche des lunettes : ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}