package com.example.lunettes

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lunettes.databinding.ActivityHomeActivittyBinding
import com.example.lunettes.databinding.ActivitySinscrireBinding
import com.example.lunettes.model.Lunettes
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeActivitty : AppCompatActivity() {
    private lateinit var binding: ActivityHomeActivittyBinding
    private lateinit var lunettesAdapter: lunettes_item
    private val lunettes = mutableListOf<Lunettes>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Liaison avec le fichier XML via View Binding
        binding = ActivityHomeActivittyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = binding.flowersRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        lunettesAdapter = lunettes_item(lunettes)
        recyclerView.adapter = lunettesAdapter


        // Gestion des boutons
        binding.addFlowerButton.setOnClickListener {
            // Rediriger vers l'activité `ajouterFleurs`
            val intent = Intent(this, ajouterLunettes::class.java)
            startActivity(intent)
        }

        binding.logoutBtn.setOnClickListener {
            Firebase.auth.signOut()
            Toast.makeText(this, "Déconnexion réussie", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, login::class.java)
            startActivity(intent)
            finish()
        }

        // Charger les fleurs filtrées
        fetchFlowers()
    }

    override fun onResume() {
        super.onResume()
        fetchFlowers() // Recharger les données à chaque retour sur cette activité
    }

    private fun fetchFlowers() {
        val db = Firebase.firestore
        val currentUserId = Firebase.auth.currentUser?.uid

        if (currentUserId == null) {
            Toast.makeText(this, "Utilisateur non connecté", Toast.LENGTH_SHORT).show()
            return
        }

        db.collection("lunettes")
            .whereEqualTo("userId", currentUserId) // Filtrer par userId
            .get()
            .addOnSuccessListener { documents ->
                lunettes.clear()
                for (document in documents) {
                    val lunette = document.toObject(Lunettes::class.java)
                    lunettes.add(lunette)
                }
                lunettesAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erreur : ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}