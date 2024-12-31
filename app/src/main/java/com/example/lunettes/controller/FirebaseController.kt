package com.example.lunettes.controller

import android.net.Uri
import com.example.lunettes.model.Lunettes
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class FirebaseController {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Ajouter une lunettes à Firestore
    fun addLunettes(lunette: Lunettes, onComplete: (Boolean) -> Unit) {
        val lunetteId = firestore.collection("lunettes").document().id // Crée une nouvelle référence de document unique

        val lunettesMap = hashMapOf(
            "nom" to lunette.nom,
            "categorie" to lunette.categorie,
            "prix" to lunette.prix,
            "description" to lunette.description,
            "imageUrl" to lunette.imageUrl,
            "userId" to lunette.userId
        )

        firestore.collection("fleurs")
            .document(lunetteId)  // Assurez-vous de définir un document unique
            .set(lunettesMap)
            .addOnSuccessListener {
                onComplete(true)
            }
            .addOnFailureListener { e ->
                onComplete(false)
            }
    }

    // Uploader une image et obtenir son URL
    fun uploadImage(imageUri: Uri, onComplete: (String?) -> Unit) {
        val storageRef = FirebaseStorage.getInstance().reference
        val fileName = "images/${System.currentTimeMillis()}.jpg"  // Nom unique pour chaque image
        val imageRef = storageRef.child(fileName)

        imageRef.putFile(imageUri)
            .addOnSuccessListener {
                imageRef.downloadUrl
                    .addOnSuccessListener { uri ->
                        onComplete(uri.toString())  // Renvoie l'URL de l'image
                    }
                    .addOnFailureListener { exception ->
                        onComplete(null)  // En cas d'échec
                    }
            }
            .addOnFailureListener { exception ->
                onComplete(null)  // En cas d'échec
            }
    }
}