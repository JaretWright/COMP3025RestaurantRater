package com.jdub.comp3025restaurantrater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.jdub.comp3025restaurantrater.databinding.ActivityRestaurantListBinding

class RestaurantListActivity : AppCompatActivity() {
    private lateinit var binding : ActivityRestaurantListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = FirebaseFirestore.getInstance().collection("restaurants")

        //this will log each of the restaurant's
        db.get().addOnSuccessListener { documents ->
            if (documents != null) {
                Log.i("DB_RESPONSE", "DocumentSnapshot data: ${documents.size()} ")
                for (document in documents) {
                    Log.i("DB_RESPONSE", "DocumentSnapshot data: ${document.data} ")
                }
            } else {
                Log.i("DB_RESPONSE", "No such document")
            }
        }
            .addOnFailureListener { exception ->
                Log.i("DB_RESPONSE", "get failed with ", exception)
            }
    }
}