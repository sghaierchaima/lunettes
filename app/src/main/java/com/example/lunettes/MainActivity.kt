package com.example.lunettes

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lunettes.databinding.ActivityMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var categories: categories
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        fetchLunettesData() // Utilisation de la méthode pour récupérer les données des lunettes

        binding.logoutBtn.setOnClickListener {
            Firebase.auth.signOut()
            Toast.makeText(this, "Déconnexion réussie", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, login::class.java)
            startActivity(intent)
            finish()
        }
        val cartIcon: ImageView = findViewById(R.id.cartIcon)

        // Ajouter un clic sur l'icône pour ouvrir CartActivity

        cartIcon.setOnClickListener {
            val cartItems = CartManager.getAllCartItems()
            if (cartItems.isEmpty()) {
                Toast.makeText(this, "Le panier est vide !", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, CartActivity::class.java)
                intent.putParcelableArrayListExtra("cart_items", ArrayList(CartManager.getAllCartItems()))
                startActivity(intent)
            }
        }
    }

    private fun setupRecyclerView() {
        categories = categories() // Initialisation de l'adaptateur
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = categories
        }
    }

    private fun fetchLunettesData() {
        firestore.collection("lunettes")  // Accès à la collection 'lunettes'
            .get()
            .addOnSuccessListener { documents ->
                val categoriesList = ArrayList<String>()
                for (document in documents) {
                    val categorie = document.getString("categorie")  // Récupération de la catégorie
                    if (categorie != null) {
                        categoriesList.add(categorie)
                    }
                }
                categories.setData(categoriesList) // Mise à jour de l'adaptateur avec les données récupérées
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Erreur : ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}