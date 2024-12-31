package com.example.lunettes

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.lunettes.controller.FirebaseController
import com.example.lunettes.databinding.ActivityAjouterLunettesBinding
import com.example.lunettes.databinding.ActivityLunettesItemBinding
import com.example.lunettes.model.Lunettes
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class ajouterLunettes : AppCompatActivity() {
    private lateinit var binding: ActivityAjouterLunettesBinding
    private val firebaseController = FirebaseController()
    private var imageUri: Uri? = null
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAjouterLunettesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()

        val currentUser = Firebase.auth.currentUser
        if (currentUser == null) {
            Toast.makeText(this, "Veuillez vous connecter pour ajouter une fleur", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, login::class.java)  // Diriger vers la page de connexion
            startActivity(intent)
            finish()
            return
        }

        val userId = currentUser.uid

        binding.uploadImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            resultLauncher.launch(intent)
        }

        binding.addLunettesButton.setOnClickListener {
            val nom = binding.lunetteNameInput.text.toString()
            val categorie = binding.lunetteCategoryInput.text.toString()
            val prix = binding.flowerPriceInput.text.toString().toDoubleOrNull() ?: 0.0
            val description = binding.flowerDescriptionInput.text.toString()

            if (nom.isEmpty() || categorie.isEmpty() || prix <= 0.0 || description.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val lunette = Lunettes(nom, categorie, prix, description, "", userId)

            if (imageUri != null) {
                firebaseController.uploadImage(imageUri!!) { imageUrl ->
                    lunette.imageUrl = imageUrl ?: ""
                    addFleurToDatabase(lunette)
                }
            } else {
                addFleurToDatabase(lunette)
            }
        }
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                imageUri = data?.data
                // Affichage de l'image sélectionnée avec Coil
                binding.imageView.load(imageUri) {
                    crossfade(true)
                    transformations(RoundedCornersTransformation(16f))  // Optionnel : ajout d'une bordure arrondie
                }
            }
        }

    private fun addFleurToDatabase(lunette: Lunettes) {
        val lunetteMap = hashMapOf(
            "nom" to lunette.nom,
            "categorie" to lunette.categorie,
            "prix" to lunette.prix,
            "description" to lunette.description,
            "imageUrl" to lunette.imageUrl,
            "userId" to lunette.userId
        )

        firestore.collection("lunettes")
            .add(lunetteMap)
            .addOnSuccessListener {
                Toast.makeText(this, "lunettes ajoutée avec succès", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erreur lors de l'ajout de lunettes", Toast.LENGTH_SHORT).show()
                e.printStackTrace()  // Pour afficher l'erreur dans les logs
            }
    }
}