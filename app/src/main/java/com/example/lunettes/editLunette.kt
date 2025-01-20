package com.example.lunettes

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lunettes.databinding.ActivityEditLunetteBinding
import com.google.firebase.firestore.FirebaseFirestore

class editLunette : AppCompatActivity() {
    private lateinit var binding: ActivityEditLunetteBinding
    private val firestore = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditLunetteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Récupérer les données de l'Intent
        val nom = intent.getStringExtra("nom")
        val categorie = intent.getStringExtra("categorie")
        val description = intent.getStringExtra("description")
        val prix = intent.getDoubleExtra("prix", 0.0)
        val imageUrl = intent.getStringExtra("imageUrl")
        // Afficher les données dans les EditText
        binding.editNom.setText(nom)
        binding.editCategorie.setText(categorie)
        binding.editDescription.setText(description)
        binding.editP.setText(prix.toString())

        binding.saveB.setOnClickListener {
            // Récupérer les nouvelles valeurs des champs
            val newNom = binding.editNom.text.toString()
            val newCategorie = binding.editCategorie.text.toString()
            val newDescription = binding.editDescription.text.toString()
            val newPrix = binding.editP.text.toString().toDoubleOrNull() ?: prix

            // Chercher le document basé sur le nom et la catégorie (ou autres critères)
            firestore.collection("lunettes")
                .whereEqualTo("nom", nom)
                .whereEqualTo("categorie", categorie)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        // Mettre à jour le document correspondant
                        val document = querySnapshot.documents[0] // On suppose un résultat unique
                        document.reference.update(
                            mapOf(
                                "nom" to newNom,
                                "categorie" to newCategorie,
                                "description" to newDescription,
                                "prix" to newPrix,
                                "imageUrl" to imageUrl // Si inchangé
                            )
                        ).addOnSuccessListener {
                            Toast.makeText(this, "lunette mise à jour avec succès", Toast.LENGTH_SHORT).show()
                            // Rediriger vers HomeActivity
                            val intent = Intent(this, HomeActivitty::class.java)
                            startActivity(intent) // Charger HomeActivity
                            finish()
                        }.addOnFailureListener {
                            Toast.makeText(this, "Erreur lors de la mise à jour", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "lunettes introuvable pour mise à jour", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Erreur de connexion à la base de données", Toast.LENGTH_SHORT).show()
                }
        }

        binding.cancelButton.setOnClickListener {
            finish() // Fermer l'activité sans enregistrer
        }
    }
}